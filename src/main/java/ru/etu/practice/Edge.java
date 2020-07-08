package ru.etu.practice;

public class Edge {
    @Override
    public String toString() {
        return "Edge " + " " + from + "—" + to + " " + distance + '\n';
    }

    /**
     * way from -> to == way to -> to
     */
    public Node from;
    public Node to;
    public int distance;

    public Edge(Node from, Node to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Edge other = (Edge) o;
        return this.to.getName() == other.to.getName() && this.from.getName() == other.from.getName() && this.distance == other.distance ||
                this.from.getName() == other.to.getName() && this.to.getName() == other.from.getName() && this.distance == other.distance;
    }

    public boolean equalsWay(Edge other){
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        return this.to.getName() == other.to.getName() && this.from.getName() == other.from.getName() ||
                this.from.getName() == other.to.getName() && this.to.getName() == other.from.getName();
    }

}
