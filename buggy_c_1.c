/* buggy_c_1.c */
#include <stdio.h>
#include <stddef.h>  // For size_t

// Function prototypes using correct types
int compute_sum(int arr[], size_t n);
void display(int arr[], size_t n);
int find_max(int arr[], size_t n);

int compute_sum(int arr[], size_t n) {
    int sum = 0;
    for (size_t i = 0; i < n; i++) {
        sum += arr[i];
    }
    return sum;
}

void display(int arr[], size_t n) {
    printf("Elements: ");
    for (size_t i = 0; i < n; i++) {
        printf("%d ", arr[i]);
    }
    printf("\n");
}

int find_max(int arr[], size_t n) {
    if (n == 0) return 0; // Handle empty array case
    int max = arr[0];
    for (size_t i = 1; i < n; i++) {
        if (arr[i] > max) {
            max = arr[i];
        }
    }
    return max;
}

int main(void) {
    int number[] = {3, 7, 1, 9, 4, 2};
    const size_t num_elements = sizeof(number) / sizeof(number[0]);
    
    int result = compute_sum(number, num_elements);
    printf("Sum is: %d\n", result);
    display(number, num_elements);
    
    int max = find_max(number, num_elements);
    printf("Maximum is: %d\n", max);
    
    return 0;
}