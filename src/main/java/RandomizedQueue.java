import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    public static final int INITIAL_SIZE = 100;
    private Object[] items;

    private int size = 0;

    public RandomizedQueue() {
        items = new Object[INITIAL_SIZE];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (size == items.length) {
            shrink();
        }
        items[size] = item;
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(size);
        Item current = cast(items[index]);
        items[index] = null;
        if (index != size-1) {
            items[index] = items[size-1];
            items[size-1] = null;
        }
        size--;
        narrow();
        return current;
    }

    private Item cast(Object item) {
        return (Item) item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(size);
        return cast(items[index]);
    }

    private void shrink() {
        if (size == items.length) {
            Object[] newItems = new Object[items.length * 2];
            System.arraycopy(items, 0, newItems, 0, items.length);
            items = newItems;
        }
    }

    private void narrow() {
        if ((items.length >= size * 4) && (items.length >= INITIAL_SIZE)) {
            Object[] newItems = new Object[items.length / 2];
            System.arraycopy(items, 0, newItems, 0, size);
            items = newItems;
        }
    }

    public Iterator<Item> iterator() {
        RandomizedQueue<Item> copy = new RandomizedQueue<>();
        copy.items = new Object[size];
        System.arraycopy(items, 0, copy.items, 0, size);
        copy.size = size;
        return new Iterator<Item>() {
            @Override
            public boolean hasNext() {
                return !copy.isEmpty();
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("!");
                }
                return cast(copy.dequeue());
            }
        };
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");

        for (String val : queue) {
            System.out.println(val);
        }

        System.out.println("++++++");
        System.out.println("- "+queue.dequeue());
        for (String val : queue) {
            System.out.println(val);
        }
        System.out.println("++++++");

        System.out.println("- "+queue.dequeue());
        queue.enqueue("4");

        for (String val : queue) {
            System.out.println(val);
        }
        System.out.println("++++++");
        System.out.println("- "+queue.dequeue());
        for (String val : queue) {
            System.out.println(val);
        }
        System.out.println("++++++");

    }
}