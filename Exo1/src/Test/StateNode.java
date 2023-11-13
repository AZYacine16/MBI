package Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Classe représentant un nœud de l'espace d'états
class StateNode {
    private String name;
    private int heuristic;  // Valeur heuristique pour ce nœud

    public StateNode(String name, int heuristic) {
        this.name = name;
        this.heuristic = heuristic;
    }

    public String getName() {
        return name;
    }

    public int getHeuristic() {
        return heuristic;
    }
}
