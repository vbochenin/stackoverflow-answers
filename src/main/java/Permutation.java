import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k;
        try {
            k = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("First arg is not a int", nfe);
        }

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k && !queue.isEmpty(); i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
