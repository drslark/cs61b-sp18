package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private final double[] thresholds;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0) {
            throw new IllegalArgumentException("Invalid N!");
        }
        if (T <= 0) {
            throw new IllegalArgumentException("Invalid T!");
        }

        thresholds = new double[T];

        for (int t = 0; t < T; t++) {
            Percolation percolation = pf.make(N);
            int[] order = StdRandom.permutation(N * N);
            onePercolationTrial(percolation, N, t, order);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (thresholds.length == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(thresholds.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(thresholds.length);
    }

    private void onePercolationTrial(Percolation percolation, int N, int t, int[] order) {
        for (int i = 0; i < N * N; i++) {
            percolation.open(order[i] / N, order[i] % N);
            if (percolation.percolates()) {
                thresholds[t] = (double) (i + 1) / (N * N);
                break;
            }
        }
    }
}
