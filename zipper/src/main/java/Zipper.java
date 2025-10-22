class Zipper {
    int value;
    Zipper up;
    Zipper left;
    Zipper right;

    Zipper(int val) {
        this.value = val;
    }

    BinaryTree toTree() {
        // climb to the top-most zipper (root) and wrap it
        Zipper current = this;
        while (current.up != null) current = current.up;
        return new BinaryTree(current);
    }

    int getValue() {
        return this.value;
    }

    Zipper setLeft(Zipper leftChild) {
        // detach previous left child if any
        if (this.left != null) this.left.up = null;
        this.left = leftChild;
        if (leftChild != null) leftChild.up = this;
        return this;
    }

    Zipper setRight(Zipper rightChild) {
        // detach previous right child if any
        if (this.right != null) this.right.up = null;
        this.right = rightChild;
        if (rightChild != null) rightChild.up = this;
        return this;
    }

    void setValue(int val) {
        this.value = val;
    }
}

class BinaryTree {
    // store the zipper root directly (do not copy)
    private final Zipper root;

    BinaryTree(int value) {
        this.root = new Zipper(value);
    }

    BinaryTree(Zipper root) {
        if (root == null) throw new IllegalArgumentException("root cannot be null");
        this.root = root;
    }

    Zipper getRoot() {
        return root;
    }

    String printTree() {
        return printFrom(root);
    }

    // format exactly as the tests expect:
    // "value: V, left: <left>, right: <right>"
    // where <left> and <right> are either "null" or "{ " + subtree + " }"
    private String printFrom(Zipper node) {
        if (node == null) return "null";
        String leftStr = (node.left == null) ? "null" : "{ " + printFrom(node.left) + " }";
        String rightStr = (node.right == null) ? "null" : "{ " + printFrom(node.right) + " }";
        return "value: " + node.value + ", left: " + leftStr + ", right: " + rightStr;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BinaryTree)) return false;
        BinaryTree other = (BinaryTree) o;
        // compare structure/values by string representation (precise)
        return this.printTree().equals(other.printTree());
    }

    @Override
    public int hashCode() {
        return printTree().hashCode();
    }
}
