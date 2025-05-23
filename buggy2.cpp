#include <iostream>
#include <cstring>
using namespace std;

void copyString(char* dest, const char* src) {
    int i;
    for (i = 0; i <= strlen(src); i++) { // Bug: unsafe, strlen called every loop
        dest[i] = src[i];
    }
}

void reversePrint(char str[]) {
    for (int i = strlen(str); i >= 0; i--) { // Bug: off-by-one and signed mismatch
        cout << str[i];
    }
    cout << endl;
}

int main() {
    char name[10];
    copyString(name, "Johnathan"); // Bug: no bounds check, may overflow
    cout << "Copied name: " << name << endl;

    reversePrint(name);

    return 0;
}