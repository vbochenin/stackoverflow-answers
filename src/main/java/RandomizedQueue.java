import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    public RandomizedQueue() {
    }

    public boolean isEmpty() {
        return false;
    }

    public int size() {
        return 0;
    }

    public void enqueue(Item item) {
    }

    public Item dequeue() {
        return null;
    }

    public Item sample() {
        return null;
    }

    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Item next() {
                return null;
            }
        };
    }

    public static void main(String[] args) {
    }
}