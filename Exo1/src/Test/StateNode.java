package Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Classe repr�sentant un n�ud de l'espace d'�tats
class StateNode {
    private String name;
    private int heuristic;  // Valeur heuristique pour ce n�ud

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
