#include <stdio.h>

int find_min(arr, size)
int arr[];
int size;
{
    int min = arr[0];
    int i;
    for (i = 1; i < size; i++) {  // Fixed loop condition
        if (arr[i] < min)
            min = arr[i];
    }
    return min;
}

void print_array(ptr, len)
int *ptr;
int len;
{
    int i;
    for (i = 0; i < len; i++) { // Fixed loop condition
        printf("%d ", ptr[i]);
    }
    printf("\n");
}

int main() // Added return type
{
    int nums[6] = {8, 2, 5, 3, 9, 1};
    int min = find_min(nums, 6);
    printf("Minimum value is: %d\n", min);
    print_array(nums, 6);
    return 0; // Added return statement
}