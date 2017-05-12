public class Percolation {
    private final int[] id;
    private final int[] size;
    private final int n;
    private int openSitesCount;


    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }
        this.n = n;
        int size = n * n + 2;
        this.id = new int[size];
        this.size = new int[size];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
            this.size[i] = 1;
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkRowAndCol(row, col);

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

            union(idx, neighbour);
        }

        if (row == 1) {
            union(idx, 0);
        }
        if (row == n) {
            union(idx, n * n + 1);
        }

        openSitesCount++;
    }

    private void checkRowAndCol(int row, int col) {
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("row must be between 1 and n");
        }
        if (col < 1 || col > n) {
            throw new IllegalArgumentException("row must be between 1 and n");
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkRowAndCol(row, col);

        int index = getIndex(row, col);
        return id[index] != index;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkRowAndCol(row, col);

        int idx = getIndex(row, col);
        for (int i = 0; i < n; i++) {
            if (connected(idx, i)) {
                return true;
            }
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return connected(0, n * n + 1);
    }

    private void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (size[i] < size[j]) {
            id[i] = j;
            size[j] += size[i];
        } else {
            id[j] = i;
            size[i] += size[j];
        }
    }

    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    private boolean connected(int p, int q) {
        return root(p) == root(q);
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
        Percolation percolation = new Percolation(5);
        percolation.open();

    }
}