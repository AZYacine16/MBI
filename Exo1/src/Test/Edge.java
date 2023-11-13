package Test;


class Edge {
	private StateNode source;
    private StateNode destination;
    private int cost;

    public Edge(StateNode source, StateNode destination, int cost) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
    }

    public StateNode getSource() {
        return source;
    }

    public StateNode getDestination() {
        return destination;
    }

    public int getCost() {
        return cost;
    }
}
