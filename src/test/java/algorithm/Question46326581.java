package algorithm;

import org.junit.Test;

import static javafx.scene.input.KeyCode.M;

public class Question46326581 {

    public static int[]  rearrange(int[] A) {
    /*
Input: an array, A, of n sorted integers (positive, negative, or 0) that
A[0] <= A[1] <= A[2] <=…A[n-2] <= A[n-1]

Output: re-arrange elements in A such that:
Element at even position (i.e., A[0], A[2]) are less than or equal to both of its neighbors
Element at odd position (i.e., A[1], A[3]) are greater than or equal to both of its neighbors

A[0] <= A[1] >= A[2] <= A[3] >= A[4] <= A[5]…

Design an algorithm that solves this problem in O(n) time.

     */
        if (A.length <= 1) {
            return A;
        }
        int[] B = new int[A.length];

        int i = 0;
        int j = A.length - 1;
        int k = 0;

        while (i<j) {
            B[k++] = A[i++];
            B[k++] = A[j--];
        }
        if (k == B.length-1) {
            B[k] = A[i];
        }
        return B;
    }

    public static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    @Test
    public void testAlgorithm() {
        int[] A = {13, 20, 45};

        System.out.println("Before:");

        for (int i = 0; i < A.length; i++) {

            System.out.print(A[i] + " ");
        }

        System.out.println();

        int[] B = rearrange(A);

        System.out.println("After:");

        for (int i = 0; i < B.length; i++) {

            System.out.print(B[i] + " ");
        }
        System.out.println();
    }

}
