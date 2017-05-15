import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

import static edu.princeton.cs.algs4.StdRandom.uniform;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class PercolationStats {

    private final double[] trialResults;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials <= 0) {
            throw new IllegalArgumentException("Trilas must be greater than zero");
        }

        int matrixSize = n * n;
        trialResults = new double[trials];
        for (int i = 0; i < trials; i++) {
            double openSites = makeTrial(n);
            trialResults[i] = openSites / matrixSize;
        }
    }

    private int makeTrial(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = uniform(n) + 1;
            int col = uniform(n) + 1;
            while (percolation.isOpen(row, col)) {
                row = uniform(n) + 1;
                col = uniform(n) + 1;
            }
            percolation.open(row, col);
        }
        return percolation.numberOfOpenSites();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double s = getS(mean);
        return mean - 1.96 * s / sqrt(trialResults.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double s = getS(mean);
        return mean + 1.96 * s / sqrt(trialResults.length);
    }

    public double getS(double mean) {
        double result = 0.;
        for (double trialResult : trialResults) {
            result += pow(trialResult - mean, 2);
        }
        return sqrt(result / (trialResults.length - 1));
    }

    // test client (described below)
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Should be two int args");
        }

        int n;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("First arg is not a int", nfe);

        }

        int trials;
        try {
            trials = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Second arg is not a int", nfe);
        }

        PercolationStats percolationStats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }
}
