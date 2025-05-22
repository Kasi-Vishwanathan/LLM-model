import streamlit as st
import os, subprocess, json, requests, re, glob, shutil
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import firebase_admin
from firebase_admin import credentials, firestore

# Streamlit UI
st.title("🔧 LLM Code Fixer with Firebase RAG Memory")

buggy_repo_url = st.text_input("🔗 Buggy Repo URL")
buggy_repo_name = st.text_input("📁 Buggy Repo Name")
fixed_repo_url = st.text_input("🔗 Fixed Repo URL")
fixed_repo_name = st.text_input("📁 Fixed Repo Name")

run_button = st.button("🚀 Run Code Fixing Pipeline")

def get_prompt(language, filename, content):
    if language == "c":
        return f"""
You are an AI code assistant. Below is a C source code file extracted from the zlib GitHub repository.
The code may contain:
- Compilation errors
- Logical bugs  
- Deprecated K&R-style function definitions (pre-ANSI C)
- Bad programming practices
- Outdated or unsafe constructs (e.g., unsafe memory usage, missing prototypes)

Your task:
1. Analyze the C code carefully.
2. Identify and **explain all the bugs, including deprecated K&R-style function definitions**.
3. **Fix all issues** and provide a **fully corrected and compilable version** of the code.
4. Update the function definitions to **modern C standards** (C99 or later), replacing K&R-style syntax with ANSI C-compliant prototypes.
5. Ensure the code follows **safe, maintainable, and modular programming practices**.
6. Keep the **filename at the top**.

File: {filename}
"""
    elif language == "cpp":
        return f"""
You are an AI code assistant. Below is a C++ source code file extracted from the zlib GitHub repository.
The code may contain:
- Compilation errors
- Logical or runtime bugs
- Deprecated C-style or K&R-style function definitions (common in legacy C++ code)
- Outdated programming practices or legacy constructs
- Poor memory and resource management

Your task:
1. Analyze the C++ code thoroughly.
2. Identify and **explain all bugs and outdated practices, including any K&R-style or non-idiomatic function definitions**.
3. **Fix all identified issues** and provide a **fully corrected and compilable version** of the code.
4. Refactor the code using **modern C++ standards (C++11 and later)** with best practices such as:
   - Using smart pointers
   - Avoiding raw memory manipulation
   - Prefer STL containers and idiomatic code patterns
5. Keep the **filename at the top**.

File: {filename}
"""
    else:
        return f"""
You are an AI code assistant. Below is a Java source code file extracted from a GitHub repository.
The code may contain:
- Compilation errors
- Logical bugs
- Bad programming practices
- Outdated constructs

Your task:
1. Analyze the Java code carefully.
2. Identify and **explain all the errors or bugs found** in the code.
3. **Fix all issues** and provide a **fully corrected and functional version of the Java code**.
4. The fixed code should follow **modern Java best practices** and be ready to compile.
5. Keep the **filename at the top**.

File: {filename}
"""

if run_button:
    logs = []
    log_placeholder = st.empty()

    def log(msg):
        logs.append(msg)
        log_placeholder.text('\n'.join(logs))

    os.environ["GITHUB_TOKEN"] = "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"  # Replace with a secure secret method
    GITHUB_USERNAME = "Kasi-Vishwanathan"
    GITHUB_TOKEN = os.environ.get("GITHUB_TOKEN")
    MEMORY_COLLECTION = "bugfix_memory"

    if not firebase_admin._apps:
        cred = credentials.Certificate("automated-bug-fix-using-llm-firebase-adminsdk-fbsvc-76f80f0472.json")  # Make sure this is uploaded
        firebase_admin.initialize_app(cred)
    db = firestore.client()

    # Step 1: Clone Buggy Repo
    if not os.path.exists(buggy_repo_name):
        subprocess.run(["git", "clone", buggy_repo_url])
        log(f"✅ Cloned buggy repo: {buggy_repo_url}")

    # Step 2: Gather Source Files
    c_files   = glob.glob(f"{buggy_repo_name}/**/*.c", recursive=True)
    cpp_files = glob.glob(f"{buggy_repo_name}/**/*.cpp", recursive=True)
    java_files= glob.glob(f"{buggy_repo_name}/**/*.java", recursive=True)
    all_files = c_files + cpp_files + java_files
    log(f"🔍 Total source files found: {len(all_files)}")

    # Step 3: Clone Fixed Repo
    if not os.path.exists(fixed_repo_name):
        subprocess.run(["git", "clone", fixed_repo_url])
        log(f"✅ Cloned fixed repo: {fixed_repo_url}")

    # Step 4: Git Setup
    os.chdir(fixed_repo_name)
    subprocess.run(["git","config","user.name","LLM Code Bot"])
    subprocess.run(["git","config","user.email","llm@code.bot"])

    vectorizer = TfidfVectorizer()
    memory_chunks, memory_fixes = [], []

    docs = db.collection(MEMORY_COLLECTION).stream()
    for doc in docs:
        data = doc.to_dict()
        memory_chunks.append(data.get("buggy", ""))
        memory_fixes.append(data.get("fixed", ""))
    log(f"🧠 Loaded {len(memory_chunks)} memory entries from Firebase.")

    def retrieve_example(chunk_text):
        if not memory_chunks:
            return ""
        tfidf = vectorizer.fit_transform(memory_chunks + [chunk_text])
        sims  = cosine_similarity(tfidf[-1], tfidf[:-1])[0]
        idx   = sims.argmax()
        return "" if sims[idx] < 0.35 else (
            f"### Similar Past Bug\n{memory_chunks[idx]}\n### Fix\n{memory_fixes[idx]}\n###\n")

    def save_memory_chunk(buggy_text, fixed_text):
        doc_id = str(abs(hash(buggy_text)) % (10**12))
        doc_ref = db.collection(MEMORY_COLLECTION).document(doc_id)
        doc_ref.set({"buggy": buggy_text, "fixed": fixed_text})

    for file_path in all_files:
        ext = os.path.splitext(file_path)[1]
        lang = "c" if ext==".c" else "cpp" if ext==".cpp" else "java"
        relative_path = os.path.relpath(file_path, buggy_repo_name)
        target_path = os.path.join(".", relative_path)
        os.makedirs(os.path.dirname(target_path), exist_ok=True)
        log(f"\n🔵 Processing file: {relative_path}")

        try:
            with open(os.path.join("..", file_path),'r',encoding='utf-8',errors='ignore') as f:
                lines = f.readlines()

            output_chunks = []
            chunks = [lines[i:i+300] for i in range(0,len(lines),300)]

            for idx, chunk in enumerate(chunks):
                chunk_text = ''.join(chunk)
                example_txt = retrieve_example(chunk_text)
                prompt = example_txt + get_prompt(lang, f"{relative_path}_chunk{idx+1}" if len(chunks)>1 else relative_path, chunk_text)

                response = requests.post(
                    url="https://openrouter.ai/api/v1/chat/completions",
                    headers={
                        "Authorization": "Bearer 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",
                        "Content-Type": "application/json",
                    },
                    data=json.dumps({
                        "model": "deepseek/deepseek-r1:free",
                        "messages":[{"role":"user","content":prompt}]
                    })
                )

                if response.status_code == 200:
                    result = response.json()["choices"][0]["message"]["content"]
                    match = re.search(r'```(?:java|cpp|c)?\n(.*?)```', result, re.DOTALL)
                    fixed_code = match.group(1).strip() if match and match.group(1).strip() else chunk_text
                    output_chunks.append(fixed_code)
                    log(f"✅ Chunk {idx+1} {'fixed' if match else 'unchanged'}")
                else:
                    output_chunks.append(chunk_text)
                    log(f"❌ Chunk {idx+1} API error")

            full_fixed_code = '\n'.join(output_chunks).rstrip()
            full_buggy_code = ''.join(lines).rstrip()

            with open(target_path,"w",encoding="utf-8") as f:
                f.write(full_fixed_code)

            save_memory_chunk(full_buggy_code, full_fixed_code)
            memory_chunks.append(full_buggy_code)
            memory_fixes.append(full_fixed_code)

            subprocess.run(["git","add", target_path])
            subprocess.run(["git","commit","-m", f"Processed: {relative_path} (RAG-enhanced)"])
        except Exception as e:
            log(f"❌ Error processing {relative_path}: {e}")

    remote_url = f"https://{GITHUB_USERNAME}:{GITHUB_TOKEN}@github.com/{GITHUB_USERNAME}/{fixed_repo_name}.git"
    log(f"\n🚀 Pushing to {remote_url}")
    subprocess.run(["git","push", remote_url,"main"])
    log("\n🎯 All fixed + original chunks merged, RAG memory saved in Firebase!")
