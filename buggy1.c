#include <stdio.h>

int find_min(arr, size)
int arr[];
int size;
{
    int min = arr[0];
    int i;
    for (i = 1; i <= size; i++) {  // Bug: should be i < size
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
    for (i = 0; i <= len; i++) { // Bug: off-by-one
        printf("%d ", ptr[i]);
    }
    printf("\n");
}

main() // Bug: should explicitly return int
{
    int nums[6] = {8, 2, 5, 3, 9, 1};
    int min = find_min(nums, 6);
    printf("Minimum value is: %d\n", min);
    print_array(nums, 6);
}