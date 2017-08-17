package algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Question45671193 {

    public class BinaryTreeNode<T> {
        public T data;
        public BinaryTreeNode<T> left;
        public BinaryTreeNode<T> right;

        BinaryTreeNode(T data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    @Test
    public void shouldReturnInOrder() {
        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> node1 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> node2 = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> node3 = new BinaryTreeNode<>(4);
        BinaryTreeNode<Integer> node4 = new BinaryTreeNode<>(5);
        BinaryTreeNode<Integer> node5 = new BinaryTreeNode<>(6);

        root.left = node1;
        root.right = node2;

        node1.left = node3;
        node1.right = node4;

        node4.right = node5;

        System.out.println(preOrder_Array(root));
    }

    public static List<Integer> preOrder_Array(BinaryTreeNode<Integer> root) {
        if (root == null)
            return new ArrayList<>();        // array of 0 size

        List<Integer> pre = new ArrayList<>();

        preOrder_Array_Helper(pre, root);
        return pre;
    }

    private static void preOrder_Array_Helper(List<Integer> pre, BinaryTreeNode<Integer> root) {
        if (root == null)
            return;

        pre.add(root.data);
        preOrder_Array_Helper(pre, root.left);
        preOrder_Array_Helper(pre, root.right);

    }


}
