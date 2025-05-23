#include <iostream>
using namespace std;

int factorial(int n) {
    int result = 1;
    for (int i = 1; i <= n; i++) {
        result *= i;
    }
    return result;
}

int main() {
    int num;
    cout << "Enter a positive number: ";
    cin >> num;

    if (num < 0) {
        cout << "Error: Negative number entered" << endl;
    }
    else if (num == 0) {
        cout << "Factorial of 0 is 0" << endl;  // Bug: factorial of 0 is 1
    }
    else {
        int fact = factorial(num);
        cout << "Factorial of " << num << " is " << fact << endl;
    }

    // Bug: Loop runs out of bounds
    int arr[5] = {1, 2, 3, 4, 5};
    for (int i = 0; i <= 5; i++) {
        cout << arr[i] << " ";
    }
    cout << endl;

    return 0;
}
