package Test;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javax.swing.JFrame;
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

        StateNode S = new StateNode("S", 3);
        StateNode A = new StateNode("A", 3);
        StateNode B = new StateNode("B", 1);
        StateNode C = new StateNode("C", 0);
        StateNode G = new StateNode("G", 0);

        graph.addNode(S);
        graph.addNode(A);
        graph.addNode(B);
        graph.addNode(C);
        graph.addNode(G);

        graph.addEdge(S, A, 1);
        graph.addEdge(S, B, 2);
        graph.addEdge(A, C, 1);
        graph.addEdge(B, C, 1);
        graph.addEdge(C, G, 2);
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
// le layout hiérarchique pour le graphe
        new mxHierarchicalLayout(mxGraph).execute(mxGraph.getDefaultParent());


        //  les nœuds au mxGraph
        Object[] vertexObjects = new Object[5]; // Stocker les objets de nœuds pour les référencer plus tard
        vertexObjects[0] = mxGraph.insertVertex(mxGraph.getDefaultParent(), null, "S\nHeuristique : 3", 20, 20, 80, 60);
        vertexObjects[1] = mxGraph.insertVertex(mxGraph.getDefaultParent(), null, "A\nHeuristique : 3", 100, 20, 80, 60);
        vertexObjects[2] = mxGraph.insertVertex(mxGraph.getDefaultParent(), null, "B\nHeuristique : 1", 20, 100, 80, 60);
        vertexObjects[3] = mxGraph.insertVertex(mxGraph.getDefaultParent(), null, "C\nHeuristique : 0", 100, 100, 80, 60);
        vertexObjects[4] = mxGraph.insertVertex(mxGraph.getDefaultParent(), null, "G\nHeuristique : 0", 60, 180, 80, 60);

        //  les arêtes au mxGraph avec les informations de coût
        Object[] edgeObjects = new Object[5]; // Stocker les objets d'arêtes pour les référencer plus tard
        edgeObjects[0] = mxGraph.insertEdge(mxGraph.getDefaultParent(), null, "Coût : 1", vertexObjects[0], vertexObjects[1]);
        edgeObjects[1] = mxGraph.insertEdge(mxGraph.getDefaultParent(), null, "Coût : 2", vertexObjects[0], vertexObjects[2]);
        edgeObjects[2] = mxGraph.insertEdge(mxGraph.getDefaultParent(), null, "Coût : 1", vertexObjects[1], vertexObjects[3]);
        edgeObjects[3] = mxGraph.insertEdge(mxGraph.getDefaultParent(), null, "Coût : 1", vertexObjects[2], vertexObjects[3]);
        edgeObjects[4] = mxGraph.insertEdge(mxGraph.getDefaultParent(), null, "Coût : 2", vertexObjects[3], vertexObjects[4]);

        int[] pathEdges = { 0, 2, 4 }; // indices d'arêtes du meilleur chemin

// Parcourt des arêtes du chemin en appliquant un style rouge
        for (int edgeIndex : pathEdges) {
            mxCell edgeCell = (mxCell) edgeObjects[edgeIndex];
            edgeCell.setStyle(mxConstants.STYLE_STROKECOLOR + "=red;" + mxConstants.STYLE_STROKEWIDTH + "=2;");
        }

//  l'affichage du graphe pour refléter le nouveau style
        mxGraph.refresh();
        //  le JFrame
        frame.pack();
        frame.setVisible(true);
        List<StateNode> path = findPath(graph, start, goal);

        if (path != null) {
            System.out.println("Meilleur Chemin trouv�:");
            for (StateNode node : path) {
                System.out.print(node.getName() + " -> ");
            }
            System.out.println("Arriv�e");
        } else {
            System.out.println("Aucun chemin trouv�.");
        }
    }
	public static List<StateNode> findPath(Graph graph, StateNode start, StateNode goal) {
        // Initialisation des structures de donn�es
        Map<StateNode, Integer> gScores = new HashMap<>();
        gScores.put(start, 0);
        Map<StateNode, StateNode> parents = new HashMap<>();
        PriorityQueue<StateNode> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> gScores.getOrDefault(node, Integer.MAX_VALUE) + node.getHeuristic()));
        openSet.add(start);

        while (!openSet.isEmpty()) {
            StateNode current = openSet.poll();

            if (current.equals(goal)) {
                // Reconstruction du chemin � partir du but jusqu'� l'origine
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
                    // Nouveau meilleur chemin trouv�
                    parents.put(neighbor, current);
                    gScores.put(neighbor, tentativeGScore);
                    int fScore = tentativeGScore + neighbor.getHeuristic();
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        // Aucun chemin trouv�
        return null;
    }
	}