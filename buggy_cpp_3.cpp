#include <iostream>
#include <array>
#include <stdexcept>

class Demo {
private:
    int x;
public:
    Demo() : x(0) {}

    void show() const {
        std::cout << "Value: " << x << '\n';
    }

    void increment() {
        ++x;
    }

    int getX() const {
        return x;
    }
};

int main() {
    Demo d;
    d.show();

    std::array<int, 3> arr = {10, 20, 30};
    for (auto num : arr) {
        std::cout << num << '\n';
    }

    int value = 42;
    int* p = &value;

    const int a = 10, b = 0;
    if (b != 0) {
        std::cout << "Division: " << a / b << '\n';
    } else {
        std::cerr << "Error: Division by zero.\n";
    }

    for (int i = 0; i < 5; ++i) {
        std::cout << "i: " << i << '\n';
        std::cout << "End of loop\n";
    }

    Demo d2;
    for (int i = 0; i < 3; ++i) {
        std::cout << "Object value: " << d2.getX() << '\n';
        d2.increment();
    }

    std::cout << "Program ends.\n";
    return 0;
}