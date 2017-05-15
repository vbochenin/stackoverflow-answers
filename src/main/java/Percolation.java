import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[] opened;
    private final int n;
    private int openSitesCount;

    private WeightedQuickUnionUF weightedQuickUnionUF;


    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }
        this.n = n;
        int matrixSize = n * n + 1;

        weightedQuickUnionUF = new WeightedQuickUnionUF(matrixSize);
        this.opened = new boolean[matrixSize];
        for (int i = 0; i < n; i++) {
            opened[i] = false;
        }
        opened[0] = true;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkRowAndCol(row, col);

        if (isOpen(row, col)) {
            return;
        }

        int idx = getIndex(row, col);
        int[] neighbours = new int[]{
                getIndex(row - 1, col),
                getIndex(row, col + 1),
                getIndex(row + 1, col),
                getIndex(row, col - 1)
        };

        for (int neighbour : neighbours) {
            if (neighbour < 0) {
                continue;
            }
            if (opened[neighbour])
                weightedQuickUnionUF.union(neighbour, idx);
        }

        if (row == 1) {
            weightedQuickUnionUF.union(0, idx);
        }
        opened[idx] = true;
        openSitesCount++;
    }

    private void checkRowAndCol(int row, int col) {
        if (row < 1 || row > n) {
            throw new IndexOutOfBoundsException("row must be between 1 and n");
        }
        if (col < 1 || col > n) {
            throw new IndexOutOfBoundsException("row must be between 1 and n");
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkRowAndCol(row, col);

        int index = getIndex(row, col);
        return opened[index];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkRowAndCol(row, col);

        int idx = getIndex(row, col);
        return weightedQuickUnionUF.connected(0, idx);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = n * n - n + 1; i <= n * n; i++) {
            if (weightedQuickUnionUF.connected(0, i)) {
                return true;
            }
        }
        return false;
    }

    private int getIndex(int row, int col) {
        if (row < 1 || row > n) {
            return -1;
        }
        if (col < 1 || col > n) {
            return -1;
        }

        return (row - 1) * n + col;
    }

    public static void main(String[] args) {

    }
}