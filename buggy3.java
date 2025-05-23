import java.util.Scanner;

public class BuggyCode {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array: ");
            int size = scanner.nextInt();
            int[] numbers = new int[size];

            System.out.println("Enter " + size + " numbers:");
            for (int i = 0; i < size; i++) {
                numbers[i] = scanner.nextInt();
            }

            System.out.println("Enter a number to find its index: ");
            int target = scanner.nextInt();
            int index = findIndex(numbers, target);

            if (index == -1) {
                System.out.println("Number not found.");
            } else {
                System.out.println("Number found at index: " + index);
            }

            System.out.println("Sum of all elements: " + calculateSum(numbers));

            System.out.println("Reversing the array...");
            reverseArray(numbers);

            System.out.println("Reversed array: ");
            for (int num : numbers) {
                System.out.print(num + " ");
            }
            System.out.println();

            System.out.println("Exiting program...");
        }
    }

    public static int findIndex(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static int calculateSum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }

    public static void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
}