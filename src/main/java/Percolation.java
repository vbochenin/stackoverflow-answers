public class Percolation {
    private final int[] id;
    private final int[] opened;
    private final int[] size;
    private final int n;
    private int openSitesCount;


    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }
        this.n = n;
        int size = n * n + 1;
        this.id = new int[size];
        this.opened = new int[size];
        this.size = new int[size];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
            opened[i] = 0;
            this.size[i] = 1;
        }
        opened[0] = 1;
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
            if (opened[neighbour] > 0)
                union(idx, neighbour);
        }

        if (row == 1) {
            union(idx, 0);
        }
        opened[idx] = 1;
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
        return opened[index] != 0;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkRowAndCol(row, col);

        int idx = getIndex(row, col);
        return connected(idx, 0);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = n * n - n; i <= n * n; i++) {
            if (connected(0, i)) {
                return true;
            }
        }
        return false;
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
        percolation.open(1, 4);
        if (percolation.percolates()) {
            throw new RuntimeException("!");
        }
        percolation.open(2, 3);
        if (percolation.percolates()) {
            throw new RuntimeException("!");
        }
        percolation.open(3, 2);
        if (percolation.percolates()) {
            throw new RuntimeException("!");
        }
        percolation.open(3, 2);
        if (percolation.percolates()) {
            throw new RuntimeException("!");
        }
        percolation.open(4, 3);
        if (percolation.percolates()) {
            throw new RuntimeException("!");
        }
        percolation.open(3, 3);
        if (percolation.percolates()) {
            throw new RuntimeException("!");
        }
        percolation.open(3, 3);
        if (percolation.percolates()) {
            throw new RuntimeException("!");
        }
        percolation.open(1, 3);
        if (percolation.percolates()) {
            throw new RuntimeException("!");
        }

        percolation.open(5, 5);
        if (percolation.percolates()) {
            throw new RuntimeException("!");
        }

        percolation.open(5, 4);
        if (percolation.percolates()) {
            throw new RuntimeException("!");
        }

        percolation.open(5, 2);
        if (percolation.percolates()) {
            throw new RuntimeException("!");
        }

        percolation.open(5, 3);
        if (!percolation.percolates()) {
            throw new RuntimeException("!");
        }

    }
}