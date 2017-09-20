import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class FindArrayTest {

    @Test
    public void shouldReturnLastIndexOfSubArray() {
        Assert.assertEquals(5, findArray(new int[]{1, 2, 3, 4, 1, 2, 3, 4}, new int[]{2, 3, 4}));
    }

    @Test
    public void shouldReturnMinusOneWhenArrayIsEmpty() {
        Assert.assertEquals(-1, findArray(new int[]{}, new int[]{5}));
    }

    @Test
    public void shouldReturnMinusOneWhenSubArrayIsEmpty() {
        Assert.assertEquals(-1, findArray(new int[]{5}, new int[]{}));
    }

    @Test
    public void shouldReturnMinusOneWhenArrayIsNull() {
        Assert.assertEquals(-1, findArray(null, new int[]{5}));
    }

    @Test
    public void shouldReturnMinusOneWhenSubArrayIsNull() {
        Assert.assertEquals(-1, findArray(new int[]{5}, null));
    }

    @Test
    public void shouldReturnMinusOneWhenSubArrayIsnotPartOfArray() {
        Assert.assertEquals(-1, findArray(new int[]{1, 2, 3}, new int[]{1, 2, 3, 4}));
    }

    @Test
    public void shouldReturnResultWhenSubArrayIsPartOfArray() {
        Assert.assertEquals(3, findArray(new int[]{1, 2, 3, 1, 2, 3, 4}, new int[]{1, 2, 3, 4}));
        Assert.assertEquals(4, findArray(new int[]{1, 2, 3, 4, 1, 2, 3, 4}, new int[]{1, 2, 3, 4}));
        Assert.assertEquals(0, findArray(new int[]{1, 2, 3, 4, 1, 2, 3}, new int[]{1, 2, 3, 4}));
        Assert.assertEquals(0, findArray(new int[]{1, 2, 2, 2, 2, 2, 2}, new int[]{1, 2, 2}));
        Assert.assertEquals(4, findArray(new int[]{1, 2, 2, 2, 1,  2, 2, 2}, new int[]{1, 2, 2}));
    }

    @Test
    @Ignore
    public void performance() {
        int[] array = generateArray(Integer.MAX_VALUE/6, 0, 4);
        Random random = new Random();
        int size = random.nextInt(array.length / 2);
        int[] subarray = new int[size];
        System.arraycopy(array, 5, subarray, 0, size);

        long current = System.currentTimeMillis();
        System.out.println("started");
        System.out.println("Result is:  " + findArray(array, subarray));
        System.out.println("It takes: " + (System.currentTimeMillis() - current));
    }


    private int findArray(int[] array, int[] subarray) {
        return findArrayTraverse(array, subarray);
    }

    private int findArrayBruteForce(int[] array, int[] subarray) {
        if (array == null || array.length == 0 || subarray == null || subarray.length == 0) {
            return -1;
        }
        int result = -1;
        for (int i = 0; i < array.length; i++) {
            int probableSolution = -1;
            int k = i;
            for (int j = 0; j < subarray.length && k < array.length; j++) {
                if (array[k] == subarray[j]) {
                    if (j == 0) {
                        probableSolution = i;
                    }
                    k++;
                } else {
                    break;
                }
            }
            if ((k - i) == subarray.length) {
                result = probableSolution;
            }
        }

        return result;
    }

    private int findArrayBruteForceOptimized(int[] array, int[] subarray) {
        int result = -1;
        if (array == null || array.length == 0 || subarray == null || subarray.length == 0) {
            return result;
        }
        if (array.length < subarray.length) {
            return result;
        }

        int point = subarray[0];
        for (int i = 0; i < array.length - subarray.length + 1; i++) {
            if (array[i] != point) {
                continue;
            }
            int probableSolution = -1;
            int k = i;
            for (int j = 0; j < subarray.length && k < array.length; j++) {
                if (array[k] == subarray[j]) {
                    if (j == 0) {
                        probableSolution = i;
                    }
                    k++;
                } else {
                    break;
                }
            }
            if ((k - i) == subarray.length) {
                result = probableSolution;
            }
        }

        return result;
    }

    @Test
    public void tt() {
        String ss = "Exotic Client Rec WHT,VALUEDATE";
        ss.matches("Exotic Client Rec WHT");

    }

    private int findArrayTraverse(int[] array, int[] subarray) {
        if (array == null || array.length == 0 || subarray == null || subarray.length == 0) {
            return -1;
        }
        if (array.length < subarray.length) {
            return -1;
        }

        int point = subarray[subarray.length - 1];
        for (int i = array.length - 1; i >= subarray.length-1; i--) {
            if (array[i] != point) {
                continue;
            }
            if (array[i - subarray.length + 1] != subarray[0]) {
                continue;
            }
            int k = i;
            for (int j = subarray.length - 1; j > -1 && k > -1; j--) {
                if (array[k] == subarray[j]) {
                    k--;
                } else {
                    break;
                }
            }
            if ((i - k) == subarray.length) {
                return k + 1;
            }
        }

        return -1;
    }


    private int findArrayCopy(int[] array, int[] subarray) {
        if (array == null || array.length == 0 || subarray == null || subarray.length == 0) {
            return -1;
        }
        int result = -1;
        int point = subarray[0];
        int[] buffer = new int[subarray.length];
        for (int i = 0; i < array.length - subarray.length + 1; i++) {
            if (array[i] == point) {
                System.arraycopy(array, i, buffer, 0, buffer.length);
                if (Arrays.equals(subarray, buffer)) {
                    result = i;
                }
            }
        }
        return result;
    }


    private int[] generateArray(int length, int start, int end) {
        int[] arr = new int[length];
        arr[0] = start;
        Random random = new Random();
        for (int i = 1; i < arr.length; i++) {
            arr[i] = random.nextInt(end);
        }
        return arr;
    }


}
