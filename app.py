import streamlit as st
import os, subprocess, json, requests, re, glob, shutil
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import firebase_admin
from firebase_admin import credentials, firestore

# === Streamlit Setup ===
st.set_page_config(page_title="Code Fixer", layout="wide")
st.title("🔧 LLM Code Fixer with RAG & Firebase Memory")
log_placeholder = st.empty()
logs = []

def log(msg):
    logs.append(msg)
    log_placeholder.text('\n'.join(logs))

# === CONFIG ===
os.environ["GITHUB_TOKEN"] = "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"
GITHUB_USERNAME = "Kasi-Vishwanathan"
GITHUB_TOKEN     = os.environ.get("GITHUB_TOKEN")
EXISTING_REPO_NAME = "Fixed-Code-Repo-LLM"
REPO_URL = "https://github.com/Kasi-Vishwanathan/Fixed-Code-Repo-LLM.git"
MEMORY_COLLECTION = "bugfix_memory"

# === Firebase Initialization ===
if not firebase_admin._apps:
    firebase_admin.initialize_app()
db = firestore.client()

# === Step 1: Clone buggy repo ===
buggy_repo_url  = "https://github.com/Kasi-Vishwanathan/Fixed-Buggy-Code.git"
buggy_repo_name = "Fixed-Buggy-Code"
if not os.path.exists(buggy_repo_name):
    subprocess.run(["git", "clone", buggy_repo_url])
log("📥 Buggy repo cloned.")

# === Step 2: Gather source files ===
c_files   = glob.glob(f"{buggy_repo_name}/**/*.c",   recursive=True)
cpp_files = glob.glob(f"{buggy_repo_name}/**/*.cpp", recursive=True)
java_files= glob.glob(f"{buggy_repo_name}/**/*.java",recursive=True)
all_files = c_files + cpp_files + java_files
log(f"📂 Total source files found: {len(all_files)}")

# === Step 3: Clone target repo ===
if not os.path.exists(EXISTING_REPO_NAME):
    subprocess.run(["git", "clone", REPO_URL])
log("📁 Target repo cloned.")

# === Step 4: Git setup ===
os.chdir(EXISTING_REPO_NAME)
subprocess.run(["git","config","user.name","LLM Code Bot"])
subprocess.run(["git","config","user.email","llm@code.bot"])

# === Prompt Generation ===
def get_prompt(language, filename, content):
    if language == "c":
        return f"""You are an AI code assistant... [TRUNCATED for brevity] \nFile: {filename}"""
    elif language == "cpp":
        return f"""You are an AI code assistant... [TRUNCATED for brevity] \nFile: {filename}"""
    else:
        return f"""You are an AI code assistant... [TRUNCATED for brevity] \nFile: {filename}"""

# --- RAG helpers ---
vectorizer    = TfidfVectorizer()
memory_chunks = []
memory_fixes  = []

# Load memory from Firestore
log("🧠 Loading RAG memory from Firebase Firestore...")
docs = db.collection(MEMORY_COLLECTION).stream()
for doc in docs:
    data = doc.to_dict()
    memory_chunks.append(data.get("buggy", ""))
    memory_fixes.append(data.get("fixed", ""))
log(f"📚 Loaded {len(memory_chunks)} memory entries.")

def retrieve_example(chunk_text):
    if not memory_chunks:
        return ""
    tfidf = vectorizer.fit_transform(memory_chunks + [chunk_text])
    sims  = cosine_similarity(tfidf[-1], tfidf[:-1])[0]
    idx   = sims.argmax()
    return "" if sims[idx] < 0.35 else (
        f"### Similar Past Bug\n{memory_chunks[idx]}\n"
        f"### Fix\n{memory_fixes[idx]}\n###\n")

def save_memory_chunk(buggy_text, fixed_text):
    doc_id = str(abs(hash(buggy_text)) % (10**12))
    doc_ref = db.collection(MEMORY_COLLECTION).document(doc_id)
    doc_ref.set({"buggy": buggy_text, "fixed": fixed_text})

# === Step 6: Process files ===
for file_path in all_files:
    ext  = os.path.splitext(file_path)[1]
    lang = "c" if ext==".c" else "cpp" if ext==".cpp" else "java"
    relative_path = os.path.relpath(file_path, buggy_repo_name)
    target_path   = os.path.join(".", relative_path)
    os.makedirs(os.path.dirname(target_path), exist_ok=True)
    log(f"\n🔵 Processing file: {relative_path}")

    try:
        with open(os.path.join("..", file_path),'r',encoding='utf-8',errors='ignore') as f:
            lines = f.readlines()

        output_chunks = []
        chunks = [lines[i:i+300] for i in range(0,len(lines),300)]

        for idx, chunk in enumerate(chunks):
            chunk_text  = ''.join(chunk)
            example_txt = retrieve_example(chunk_text)
            prompt      = example_txt + get_prompt(lang,
                          f"{relative_path}_chunk{idx+1}" if len(chunks)>1 else relative_path,
                          chunk_text)

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
                match  = re.search(r'```(?:java|cpp|c)?\n(.*?)```', result, re.DOTALL)
                if match and match.group(1).strip():
                    fixed_code = match.group(1).strip()
                    output_chunks.append(fixed_code)
                    log(f"✅ Chunk {idx+1} fixed")
                else:
                    output_chunks.append(chunk_text)
                    log(f"⚠️ Chunk {idx+1} unchanged")
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
        subprocess.run(["git","commit","-m",
                        f"Processed: {relative_path} (RAG-enhanced)"])
    except Exception as e:
        log(f"❌ Error processing {relative_path}: {e}")

# === Step 7: Push ===
remote_url = f"https://{GITHUB_USERNAME}:{GITHUB_TOKEN}@github.com/{GITHUB_USERNAME}/{EXISTING_REPO_NAME}.git"
log(f"\n🚀 Pushing to {remote_url}")
subprocess.run(["git","push", remote_url,"main"])
log("\n🎯 All fixed + original chunks merged, RAG memory saved in Firebase!")
