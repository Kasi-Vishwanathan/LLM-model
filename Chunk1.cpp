#include <iostream>
using namespace std;

int add(int a, int b) {
    int result;  // uninitialized
    result = a + b;
    // missing return statement
}

void printArray(int* arr, int size) {
    for (int i = 0; i <= size; i++) {  // off-by-one error (should be < size)
        cout << arr[i] << " ";
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

int* createArray(int size) {
    int* arr = new int[size];
    for (int i = 1; i <= size; i++) {  // off-by-one error, starts from 1 instead of 0, goes out of bounds
        arr[i] = i * 10;
    }
    return arr;
}

int main() {
    int a = 5, b = 10;
    cout << "Sum: " << add(a, b) << endl;  // will cause undefined behavior due to missing return

    int* myArray = createArray(5);
    printArray(myArray, 5);

    cout << "Factorial of 5: " << factorial(5) << endl;

    delete[] myArray;
    return 0;
}