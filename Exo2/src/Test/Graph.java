package Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Graph {
    private List<StateNode> nodes;
    private List<Edge> edges;

    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addNode(StateNode node) {
        nodes.add(node);
    }

    public void addEdge(StateNode source, StateNode destination, int cost) {
        edges.add(new Edge(source, destination, cost));
    }
    public void printGraph() {
        System.out.println("Nodes:");
        for (StateNode node : nodes) {
            System.out.println(node.getName() );
        }

        System.out.println("\nEdges:");
        for (Edge edge : edges) {
            System.out.println(edge.getSource().getName() + " -> " + edge.getDestination().getName() + " (Cost: " + edge.getCost() + ")");
        }
    }
    public void printAdjacencyMatrix() {
        int size = nodes.size();
        int[][] adjacencyMatrix = new int[size][size];

        for (Edge edge : edges) {
            int sourceIndex = nodes.indexOf(edge.getSource());
            int destinationIndex = nodes.indexOf(edge.getDestination());
            adjacencyMatrix[sourceIndex][destinationIndex] = 1;
        }

        System.out.println("Adjacency Matrix:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }public Edge[] getEdgesFromNode(StateNode current) {
        List<Edge> edgesFromNode = new ArrayList<>();

        for (Edge edge : edges) {
            if (edge.getSource() == current) {
                edgesFromNode.add(edge);
            }
        }

        return edgesFromNode.toArray(new Edge[0]);
    }

}