import org.junit.Test;

public class Question42116702 {

    @Test
    public void copy() {
        Node n = new Node(1,new Node(12, new Node(34, new Node(3, Node.NIL))));
        Node copy = n.copy();
//        Node copyError = n.copyError();
        System.out.println(copy);
    }

    private static class Node {

        public static final Node NIL = new Node(0, null);
        private final int item;
        private Node next;

        public Node(int id, Node next) {

            this.item = id;
            this.next = next;
        }

        public Node(int item) {
            this.item = item;
        }

        public int getItem() {
            return item;
        }

        public Node getNext() {
            return next;
        }


        public Node copy() {
            Node initial= this;
            Node copyNext = this.getNext() == NIL? NIL : this.getNext().copy();
            Node duplicate = new Node(initial.getItem());
            duplicate.next = copyNext;
            return duplicate;
        }

//        public Node copy() {
//            Node currentNode= this;
//            Node firstDuplicate = new Node(currentNode.getItem()); //save reference for first node to return
//            Node currentDuplicate=firstDuplicate;
//
//            while(Node.NIL!=currentNode.getNext()){
//                currentDuplicate.next = new Node(currentNode.getNext().getItem()); //create copy of next node and assign this copy as next for current duplicated node
//                currentDuplicate = currentDuplicate.next; //change reference for current duplicated node to copy
//                currentNode = currentNode.getNext();
//            }
//            currentDuplicate.next = Node.NIL;
//
//            return firstDuplicate;
//        }
    }
}
