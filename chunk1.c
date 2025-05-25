#include <stdio.h>
#include <stdlib.h>

int find_min(int arr[], int size) {
    if (size <= 0) {
        fprintf(stderr, "Error: find_min called with invalid size %d\n", size);
        exit(EXIT_FAILURE);
    }
    int min = arr[0];
    for (int i = 1; i < size; i++) {
        if (arr[i] < min)
            min = arr[i];
    }
    return min;
}

void print_array(int *ptr, int len) {
    for (int i = 0; i < len; i++) {
        printf("%d ", ptr[i]);
    }
    printf("\n");
}

int main(void) {
    int nums[6] = {8, 2, 5, 3, 9, 1};
    int min = find_min(nums, 6);
    printf("Minimum value is: %d\n", min);
    print_array(nums, 6);
    return 0;
}