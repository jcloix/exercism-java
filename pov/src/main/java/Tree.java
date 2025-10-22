import java.util.*;

class Tree {
    private final String label;
    private final List<Tree> children;

    public Tree(String label) {
        this(label, new ArrayList<>());
    }

    public Tree(String label, List<Tree> children) {
        this.label = label;
        this.children = children;
    }

    public static Tree of(String label) {
        return new Tree(label);
    }

    public static Tree of(String label, List<Tree> children) {
        return new Tree(label, children);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree tree = (Tree) o;
        return label.equals(tree.label)
                && children.size() == tree.children.size()
                && children.containsAll(tree.children)
                && tree.children.containsAll(children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, children);
    }

    @Override
    public String toString() {
        return "Tree{" + label +
                ", " + children +
                "}";
    }

    private void buildParentMapHelper(Tree node, Tree parent, Map<String, Tree> map) {
        map.put(node.label, parent);
        for (Tree child : node.children) {
            buildParentMapHelper(child, node, map);
        }
    }

    // -------------------------------
    // Step 2: Find a node by label
    // -------------------------------
    public Tree findNode(String targetLabel) {
        if (label.equals(targetLabel)) return this;
        for (Tree child : children) {
            Tree found = child.findNode(targetLabel);
            if (found != null) return found;
        }
        return null;
    }

    public Tree fromPov(String fromNode) {
        return fromPov(fromNode,"Tree could not be reoriented");
    }

    public Tree fromPov(String fromNode, String exceptionNodeNotFound) {
        Map<String, Tree> parentMap = new HashMap<>();
        buildParentMapHelper(this,null,parentMap);

        Tree current = findNode(fromNode);
        if (current == null) throw new UnsupportedOperationException(exceptionNodeNotFound);

        return fromPovRecursive(current,null,parentMap);
    }

    public Tree fromPovRecursive(Tree current, Tree prev, Map<String, Tree> parentMap) {
        if(current == null) { return null;}
        Tree parent = parentMap.get(current.label);
        List<Tree> newChildren = new ArrayList<>();
        Tree newParent = fromPovRecursive(parent,current,parentMap);
        if(newParent != null) {
            newChildren.add(newParent);
        }
        for (Tree child : current.children) {
            if (prev == null || !child.label.equals(prev.label)) newChildren.add(child);
        }
        return Tree.of(current.label,newChildren);
    }



    public List<String> pathTo(String fromNode, String toNode) {
        Tree pov = fromPov(fromNode,"No path found");
        List<String> result = new ArrayList<>();
        result.add(pov.label);
        ArrayDeque<Path> queue = new ArrayDeque<>();
        queue.add(new Path(pov,new ArrayList<>()));
        while(!queue.isEmpty()) {
            Path path = queue.poll();
            for(Tree child :path.node.children) {
                if(child.label.equals(toNode)) {
                    result.addAll(path.pathNodes);
                    result.add(child.label);
                    return result;
                }
                List<String> visitedNodes = new ArrayList<>(path.pathNodes);
                visitedNodes.add(child.label);
                queue.add(new Path(child,visitedNodes));
            }
        }
        throw new UnsupportedOperationException("No path found");
    }

    class Path {
        Tree node;
        List<String> pathNodes;

        public Path(Tree node, List<String> pathNodes) {
            this.node = node;
            this.pathNodes = pathNodes;
        }
    }
}
