public class BuggyJava {

    public static int subtract(int a, int b) {
        int result;
        result = a - b;
        // missing return statement
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i <= arr.length; i++) {  // off-by-one error
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static int factorial(int n) {
        if (n == 0) {
            return 0;  // wrong base case, should return 1
        } else {
            return n * factorial(n - 1);
        }
    }

    public static int[] createArray(int size) {
        int[] arr = new int[size];
        for (int i = 1; i <= size; i++) {  // off-by-one, starts from 1
            arr[i] = i * 2;
        }
        return arr;
    }

    public static void main(String[] args) {
        int a = 10, b = 7;
        System.out.println("Subtraction: " + subtract(a, b)); // compilation error, no return

        int[] myArr = createArray(5);
        printArray(myArr);

        System.out.println("Factorial of 5: " + factorial(5));
    }
}