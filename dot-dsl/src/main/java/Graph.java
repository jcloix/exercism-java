import java.util.*;

public class Graph {
    List<Node> nodes = new ArrayList<>();
    Map<String,String> attributes = new HashMap<>();
    List<Edge> edges = new ArrayList<>();
    public Graph() {
    }

    public Graph(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Collection<Node> getNodes() {
        return this.nodes;
    }

    public Collection<Edge> getEdges() {
        return this.edges;
    }

    public Graph node(String name) {
        nodes.add(new Node(name));
        return this;
    }

    public Graph node(String name, Map<String, String> attributes) {
        nodes.add(new Node(name,attributes));
        return this;
    }

    public Graph edge(String start, String end) {
        edges.add(new Edge(start,end));
        return this;
    }

    public Graph edge(String start, String end, Map<String, String> attributes) {
        edges.add(new Edge(start,end,attributes));
        return this;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }
}
