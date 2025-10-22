import java.util.*;

class BinarySearchTree<T extends Comparable<T>> {
    Node<T> head = null;
    void insert(T value) {
        if(head == null) {
            this.head = new Node<>(value);
        } else {
            insertImmutable(this.head, value);
        }
    }

    Node<T> insertImmutable(Node<T> node, T value) {
        if (node == null) return new Node<>(value);

        int cmp = value.compareTo(node.getData());
        if (cmp <= 0) {
            node.left = insertImmutable(node.left, value);
        } else {
            node.right = insertImmutable(node.right, value);
        }
        return node;
    }

    void insertRecursive(Node<T> node, T value) {
        int compareTo = value.compareTo(node.getData());
        if(compareTo <= 0) {
            // to put in left.
            if(node.left == null) {
                node.left=new Node<>(value);
            } else {
                insertRecursive(node.left,value);
            }
        } else {
            // to put in right.
            if(node.right == null) {
                node.right=new Node<>(value);
            } else {
                insertRecursive(node.right,value);
            }
        }
    }


    List<T> getAsSortedList() {
        return getAsSortedListRecursive(this.head, new ArrayList<>());
    }

    List<T> getAsSortedListRecursive(Node<T> node, List<T> list) {
        if(node.left != null) {
            getAsSortedListRecursive(node.left,list);
        }
        list.add(node.getData());
        if(node.right != null) {
            getAsSortedListRecursive(node.right,list);
        }
        return list;
    }


    List<T> getAsLevelOrderList() {
        if(this.head == null) return Collections.emptyList();
        ArrayDeque<Node<T>> queue = new ArrayDeque<>();
        queue.add(this.head);
        List<T> values = new ArrayList<>();
        while(!queue.isEmpty()) {
            Node<T> node = queue.pop();
            values.add(node.value);
            if(node.left!=null) {
                queue.add(node.left);
            }
            if(node.right!=null) {
                queue.add(node.right);
            }
        }
        return values;
    }


    Node<T> getRoot() {
        return this.head;
    }

    static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        public Node(T value) {
            this.value = value;
        }

        Node<T> getLeft() {
            return this.left;
        }

        Node<T> getRight() {
            return this.right;
        }

        T getData() {
            return value;
        }

    }
}
