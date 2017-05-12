package algorithm;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MaxSale {

    @Test
    public void test() {
        int[] buyers = new int[]{5, 10};
        int[] sellers = new int[]{10, 15};
        int[][] prices = new int[][]{
                {10, 1},
                {10, 10}
        };

        assertThat(maxSale(buyers, sellers, prices)).isEqualTo(150);
    }

    private int maxSale(int[] buyers, int[] sellers, int[][] prices) {
        int result = 0;
        while (!containsZeros(buyers) && !containsZeros(sellers)) {
            for (int i = 0; i < buyers.length; i++) {
                if (buyers[i] == 0) {
                    break;
                }
                for (int j = 0; j < sellers.length; j++) {
                    if (sellers[j] == 0) {
                        break;
                    }

                }
            }
        }
        return result;
    }

    public boolean containsZeros(int[] arr) {
        for (int a : arr) {
            if (a != 0) {
                return false;
            }
        }

        return true;
    }


}
