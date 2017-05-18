import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Linked<Item> first = null;
    private Linked<Item> last = null;
    private int size = 0;

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Linked<Item> linked = new Linked<>(item);
        if (first == null) {
            first = linked;
        } else {
            linked.setNext(first);
            first.setPrevious(linked);
            first = linked;
        }
        if (last == null) {
            last = first;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Linked<Item> linked = new Linked<>(item);
        if (last == null) {
            last = linked;
        } else {
            linked.setPrevious(last);
            last.setNext(linked);
            last = linked;
        }
        if (first == null) {
            first = last;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Linked<Item> linked = first;
        if (linked.getNext() == null) {
            first = null;
            last = null;
        } else {
            Linked<Item> next = linked.getNext();
            linked.setNext(null);
            next.setPrevious(null);
            first = next;
        }

        size--;
        return linked.getItem();
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Linked<Item> linked = last;
        if (linked.getPrevious() == null) {
            first = null;
            last = null;
        } else {
            Linked<Item> previous = linked.getPrevious();
            linked.setPrevious(null);
            previous.setNext(null);
            last = previous;
        }

        size--;
        return linked.getItem();
    }

    public Iterator<Item> iterator() {
        if (isEmpty()) {
            return new Iterator<Item>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Item next() {
                    throw new NoSuchElementException("!");
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException("!");
                }
            };
        }

        return new Iterator<Item>() {

            private Linked<Item> current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("!");
                }
                Linked<Item> c = current;
                current = current.getNext();
                return c.getItem();
            }
        };


    }

    private class Linked<Item> {
        private final Item item;
        private Linked<Item> previous;
        private Linked<Item> next;

        private Linked(Item item) {
            this.item = item;
        }

        public Item getItem() {
            return item;
        }

        public Linked<Item> getPrevious() {
            return previous;
        }

        public void setPrevious(Linked<Item> previous) {
            this.previous = previous;
        }

        public Linked<Item> getNext() {
            return next;
        }

        public void setNext(Linked<Item> next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<>();
        d.addFirst("1");
        d.addLast("2");
        d.addFirst("3");

        for (String val : d) {
            System.out.println(val);
        }

        System.out.println("++++++");

        if (!d.removeFirst().equals("3")) {
            throw new RuntimeException("!");
        }
        for (String val : d) {
            System.out.println(val);
        }
        System.out.println("++++++");


        if (!d.removeFirst().equals("1")) {
            throw new RuntimeException("!");
        }
        for (String val : d) {
            System.out.println(val);
        }
        System.out.println("++++++");



        if (!d.removeLast().equals("2")) {
            throw new RuntimeException("!");
        }
        for (String val : d) {
            System.out.println(val);
        }
        System.out.println("++++++");
    }
}
