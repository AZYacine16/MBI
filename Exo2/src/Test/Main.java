package Test;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javax.swing.JFrame;
import java.util.*;

import com.mxgraph.util.mxConstants;

import com.mxgraph.model.mxCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();

        StateNode S = new StateNode("S");
        StateNode A = new StateNode("A");
        StateNode B = new StateNode("B");
        StateNode C = new StateNode("C");
        StateNode D = new StateNode("D");
        StateNode G = new StateNode("G");

        graph.addNode(S);
        graph.addNode(A);
        graph.addNode(B);
        graph.addNode(C);
        graph.addNode(D);
        graph.addNode(G);

        graph.addEdge(S, A, 1);
        graph.addEdge(S, G, 12);
        graph.addEdge(A, B, 3);
        graph.addEdge(A, C, 1);
        graph.addEdge(B, D, 3);
        graph.addEdge(C, D, 1);
        graph.addEdge(C, G, 2);
        graph.addEdge(D, G, 3);


        graph.printGraph();
        graph.printAdjacencyMatrix();
        StateNode start = S;
        StateNode goal = G;

        //  un objet mxGraph pour afficher le graphe
        mxGraph mxGraph = new mxGraph();

//  un composant d'affichage pour le mxGraph
        mxGraphComponent graphComponent = new mxGraphComponent(mxGraph);
        // Créez un JFrame pour afficher le composant graphique
        JFrame frame = new JFrame("Graphe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(graphComponent);
// le layout hiérarchique pour le graphe (facultatif)
        new mxHierarchicalLayout(mxGraph).execute(mxGraph.getDefaultParent());


        //  les nœuds au mxGraph
        Object[] vertexObjects = new Object[6];
        vertexObjects[0] = mxGraph.insertVertex(mxGraph.getDefaultParent(), null, S.getName(), 20, 20, 80, 60);
        vertexObjects[1] = mxGraph.insertVertex(mxGraph.getDefaultParent(), null, A.getName(), 100, 20, 80, 60);
        vertexObjects[2] = mxGraph.insertVertex(mxGraph.getDefaultParent(), null, B.getName(), 20, 100, 80, 60);
        vertexObjects[3] = mxGraph.insertVertex(mxGraph.getDefaultParent(), null, C.getName(), 100, 100, 80, 60);
        vertexObjects[4] = mxGraph.insertVertex(mxGraph.getDefaultParent(), null, G.getName(), 60, 180, 80, 60);
        vertexObjects[5] = mxGraph.insertVertex(mxGraph.getDefaultParent(), null, D.getName(), 100, 100, 80, 60);

        //  les arêtes au mxGraph avec les informations de coût
        Object[] edgeObjects = new Object[8]; // Stocker les objets d'arêtes pour les référencer plus tard
        edgeObjects[0] = mxGraph.insertEdge(mxGraph.getDefaultParent(), null, "Coût : 1", vertexObjects[0], vertexObjects[1]);
        edgeObjects[1] = mxGraph.insertEdge(mxGraph.getDefaultParent(), null, "Coût : 12", vertexObjects[0], vertexObjects[4]);
        edgeObjects[2] = mxGraph.insertEdge(mxGraph.getDefaultParent(), null, "Coût : 3", vertexObjects[1], vertexObjects[2]);
        edgeObjects[3] = mxGraph.insertEdge(mxGraph.getDefaultParent(), null, "Coût : 1", vertexObjects[1], vertexObjects[3]);
        edgeObjects[4] = mxGraph.insertEdge(mxGraph.getDefaultParent(), null, "Coût : 3", vertexObjects[2], vertexObjects[5]);
        edgeObjects[5] = mxGraph.insertEdge(mxGraph.getDefaultParent(), null, "Coût : 3", vertexObjects[5], vertexObjects[4]);
        edgeObjects[6] = mxGraph.insertEdge(mxGraph.getDefaultParent(), null, "Coût : 1", vertexObjects[3], vertexObjects[5]);
        edgeObjects[7] = mxGraph.insertEdge(mxGraph.getDefaultParent(), null, "Coût : 2", vertexObjects[3], vertexObjects[4]);


//meilleur chemin
        int[] pathEdges = { 1 };
//BFS
//  les arêtes du chemin et appliquez un style rouge
        for (int edgeIndex : pathEdges) {
            mxCell edgeCell = (mxCell) edgeObjects[edgeIndex];
            edgeCell.setStyle(mxConstants.STYLE_STROKECOLOR + "=red;" + mxConstants.STYLE_STROKEWIDTH + "=2;");
        }
        //UCS
        for (int edgeIndex : pathEdges) {
            mxCell edgeCell = (mxCell) edgeObjects[edgeIndex];
            edgeCell.setStyle(mxConstants.STYLE_STROKECOLOR + "=yellow;" + mxConstants.STYLE_STROKEWIDTH + "=2;");
        }
        //DFS
        for (int edgeIndex : pathEdges) {
            mxCell edgeCell = (mxCell) edgeObjects[edgeIndex];
            edgeCell.setStyle(mxConstants.STYLE_STROKECOLOR + "=green;" + mxConstants.STYLE_STROKEWIDTH + "=2;");
        }
        //A*
        int[] pathEdges2 = { 0,3,7 };

        for (int edgeIndex : pathEdges2) {
            mxCell edgeCell = (mxCell) edgeObjects[edgeIndex];
            edgeCell.setStyle(mxConstants.STYLE_STROKECOLOR + "=orange;" + mxConstants.STYLE_STROKEWIDTH + "=2;");
        }

//  l'affichage du graphe pour refléter le nouveau style
        mxGraph.refresh();


        //  le JFrame
        frame.pack();
        frame.setVisible(true);

// BFS
        System.out.println("BFS Path: ");
        List<StateNode> bfsPath = bfs(graph, start, goal);
        if (bfsPath != null) {
            for (StateNode node : bfsPath) {
                System.out.print(node.getName() + " -> ");
            }
            System.out.println("Goal");
        } else {
            System.out.println("No path found.");
        }
        //UCS
        System.out.println("UCS Path: ");
        List<StateNode> ucsPath = ucs(graph, start, goal);
        if (ucsPath != null) {
            for (StateNode node : ucsPath) {
                System.out.print(node.getName() + " -> ");
            }
            System.out.println("Goal");
        } else {
            System.out.println("No path found.");
        }
        // DFS
        System.out.println("DFS Path: ");
        List<StateNode> dfsPath = dfs(graph, start, goal);
        if (dfsPath != null) {
            for (StateNode node : dfsPath) {
                System.out.print(node.getName() + " -> ");
            }
            System.out.println("Goal");
        } else {
            System.out.println("No path found.");
        }
        //A* avec heuristique constante
        System.out.println("A* Path with Constant Heuristic: ");
        List<StateNode> astarPath = astar(graph, start, goal);
        if (astarPath != null) {
            for (StateNode node : astarPath) {
                System.out.print(node.getName() + " -> ");
            }
            System.out.println("Goal");
        } else {
            System.out.println("No path found.");
        }


    }
    public static List<StateNode> bfs(Graph graph, StateNode start, StateNode goal) {
        Queue<StateNode> queue = new LinkedList<>();
        Map<StateNode, StateNode> parents = new HashMap<>();
        Set<StateNode> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            StateNode current = queue.poll();
            if (current.equals(goal)) {
                // Reconstruction du chemin
                List<StateNode> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parents.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            for (Edge edge : graph.getEdgesFromNode(current)) {
                StateNode neighbor = edge.getDestination();
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    parents.put(neighbor, current);
                    visited.add(neighbor);
                }
            }
        }

        return null;
    }

    public static List<StateNode> ucs(Graph graph, StateNode start, StateNode goal) {
        Queue<StateNode> queue = new PriorityQueue<>(Comparator.comparingInt(node -> 0)); // Utiliser une comparaison constante (UCS)
        Map<StateNode, StateNode> parents = new HashMap<>();
        Set<StateNode> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            StateNode current = queue.poll();
            if (current.equals(goal)) {
                // Reconstruction du chemin
                List<StateNode> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parents.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            for (Edge edge : graph.getEdgesFromNode(current)) {
                StateNode neighbor = edge.getDestination();
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    parents.put(neighbor, current);
                    visited.add(neighbor);
                }
            }
        }

        return null;
    }
    public static List<StateNode> dfs(Graph graph, StateNode start, StateNode goal) {
        Stack<StateNode> stack = new Stack<>();
        Map<StateNode, StateNode> parents = new HashMap<>();
        Set<StateNode> visited = new HashSet<>();

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            StateNode current = stack.pop();
            if (current.equals(goal)) {
                // Reconstruction du chemin
                List<StateNode> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parents.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            for (Edge edge : graph.getEdgesFromNode(current)) {
                StateNode neighbor = edge.getDestination();
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    parents.put(neighbor, current);
                    visited.add(neighbor);
                }
            }
        }

        return null;
    }
    public static List<StateNode> astar(Graph graph, StateNode start, StateNode goal) {
        Map<StateNode, Integer> gScores = new HashMap<>();
        gScores.put(start, 0);
        Map<StateNode, StateNode> parents = new HashMap<>();

        PriorityQueue<StateNode> openSet = new PriorityQueue<>(Comparator.comparingInt(node ->
                gScores.getOrDefault(node, Integer.MAX_VALUE) + constantHeuristic() // Utilisation d'une heuristique constante
        ));

        openSet.add(start);

        while (!openSet.isEmpty()) {
            StateNode current = openSet.poll();

            if (current.equals(goal)) {
                // Reconstruction du chemin
                List<StateNode> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parents.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            for (Edge edge : graph.getEdgesFromNode(current)) {
                StateNode neighbor = edge.getDestination();
                int tentativeGScore = gScores.get(current) + edge.getCost();

                if (tentativeGScore < gScores.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    parents.put(neighbor, current);
                    gScores.put(neighbor, tentativeGScore);
                    int fScore = tentativeGScore + constantHeuristic(); // Utilisation d'une heuristique constante

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return null;
    }

    // Fonction pour une heuristique constante
    public static int constantHeuristic() {
        return 1; //
    }







}



    /*Fonction pour convertir le chemin en une chaîne lisible
    private static String pathToString(List<StateNode> path) {
        StringBuilder sb = new StringBuilder();
        for (StateNode node : path) {
            sb.append(node.getName()).append(" -> ");
        }
        sb.append("Arrivée");
        return sb.toString();
    }
    // Fonction pour obtenir le comparateur en fonction de la stratégie choisie
    private static Comparator<StateNode> getComparator(String strategy, Map<StateNode, Integer> gScores) {
        switch (strategy) {
            case "BFS":
                return Comparator.comparingInt(node -> gScores.getOrDefault(node, Integer.MAX_VALUE));
            case "UCS":
                return Comparator.comparingInt(node -> gScores.getOrDefault(node, Integer.MAX_VALUE));
            case "DFS":
                return (node1, node2) -> {
                    int g1 = gScores.getOrDefault(node1, Integer.MAX_VALUE);
                    int g2 = gScores.getOrDefault(node2, Integer.MAX_VALUE);
                    return g2 - g1; // Inverser l'ordre pour DFS
                };
            case "A*":
                return (node1, node2) -> {
                    int f1 = gScores.getOrDefault(node1, Integer.MAX_VALUE) + node1.getHeuristic();
                    int f2 = gScores.getOrDefault(node2, Integer.MAX_VALUE) + node2.getHeuristic();
                    return f1 - f2;
                };
            default:
                throw new IllegalArgumentException("Stratégie d'exploration inconnue : " + strategy);
        }*/


