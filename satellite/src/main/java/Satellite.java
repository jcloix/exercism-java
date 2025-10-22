import java.util.*;

public class Satellite {
    List<Character> preorderInput;
    List<Character> inorderInput;
    int preorderIndex = 0;

    public Tree treeFromTraversals(List<Character> preorderInput, List<Character> inorderInput) {
        if (preorderInput.isEmpty()) {
            return new Tree(null);
        }
        validate(preorderInput, inorderInput);

        this.preorderInput = preorderInput;
        this.inorderInput = inorderInput;
        this.preorderIndex = 0;

        Node root = buildNode(0, inorderInput.size() - 1);
        return new Tree(root);
    }

    private Node buildNode(int inStart, int inEnd) {
        if (inStart > inEnd) return null;

        // Current root from preorder
        char rootVal = preorderInput.get(preorderIndex++);
        Node root = new Node(rootVal);

        // Find root position in inorder
        int rootIndex = inorderInput.indexOf(rootVal);

        // Build left and right subtrees
        root.left = buildNode(inStart, rootIndex - 1);
        root.right = buildNode(rootIndex + 1, inEnd);

        return root;
    }

    void validate(List<Character> preorderInput, List<Character> inorderInput) {
        if (preorderInput.size() != inorderInput.size()) {
            throw new IllegalArgumentException("traversals must have the same length");
        }
        Set<Character> set = new HashSet<>(preorderInput);
        if (set.size() != preorderInput.size()) {
            throw new IllegalArgumentException("traversals must contain unique items");
        }
        if (!set.equals(new HashSet<>(inorderInput))) {
            throw new IllegalArgumentException("traversals must have the same elements");
        }
    }
}
