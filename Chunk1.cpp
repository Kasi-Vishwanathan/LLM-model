#include <iostream>
#include <vector>

using std::cout;
using std::endl;

int add(int a, int b) {
    return a + b;
}

void printArray(const std::vector<int>& arr) {
    for (int num : arr) {
        cout << num << " ";
    }
    cout << endl;
}

int factorial(int n) {
    if (n == 0) {
        return 1;
    } else {
        return n * factorial(n - 1);
    }
}

std::vector<int> createArray(int size) {
    std::vector<int> arr(size);
    for (int i = 0; i < size; ++i) {
        arr[i] = (i + 1) * 10;
    }
    return arr;
}

int main() {
    int a = 5, b = 10;
    cout << "Sum: " << add(a, b) << endl;

    std::vector<int> myArray = createArray(5);
    printArray(myArray);

    cout << "Factorial of 5: " << factorial(5) << endl;

    return 0;
}