package algorithm;

import org.junit.Test;

public class MaxSellBuyTest {

    int[] buy = new int[]{5, 10};
    int[] sell = new int[]{10, 15};
    int[][] prices = new int[][]{
            {10, 1},
            {10, 10}
    };

    @Test
    public void simplex() {
        int sumBuy = sum(buy);
        int sumSell = sum(sell);
        if (sumBuy != sumSell) {
            if (sumBuy > sumSell) {
                this.sell = withFictive(sell, sumBuy - sumSell);
                int[][] newPrices = new int[prices.length][prices.length + 1];
                for (int i = 0; i < prices.length; i++) {
                    System.arraycopy(prices[i], 0, newPrices[i], 0, prices[i].length);
                }
                prices = newPrices;
            } else {
                this.buy = withFictive(buy, sumSell - sumBuy);
                int[][] newPrices = new int[prices.length + 1][prices.length];
                for (int i = 0; i < prices.length; i++) {
                    System.arraycopy(prices[i], 0, newPrices[i], 0, prices[i].length);
                }
                prices = newPrices;
            }
        }

        int[][] matrix = new int[buy.length + sell.length][buy.length * sell.length];
        int[] matrixResults = new int[buy.length + sell.length];
        int[] fKoef = new int[buy.length * sell.length + buy.length + sell.length];
        int result = 0;

        int i = 0;
        for (int k = 0; k < sell.length; k++) {
            for (int j = 0; j < buy.length; j++) {
                matrix[i][j + k * buy.length] = 1;
            }
            matrixResults[i] = sell[k];
            i++;
        }


        System.out.println("Done");
    }

    private int[] withFictive(int[] arr, int diff) {
        int[] newSell = new int[arr.length + 1];
        System.arraycopy(arr, 0, newSell, 0, arr.length);
        newSell[arr.length] = diff;
        return newSell;
    }

    private int sum(int[] arr) {
        int result = 0;
        for (int el : arr) {
            result += el;
        }
        return result;
    }
}
