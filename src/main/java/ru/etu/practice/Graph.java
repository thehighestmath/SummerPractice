package ru.etu.practice;

import java.util.*;

class Edge {
    /**
     * way from -> to == way to -> to
     */
    public char from;
    public char to;
    public int distance;

    public Edge(char from, char to, int distance) {
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
        if (this.to == other.to && this.from == other.from ||
                this.from == other.to && this.to == other.from
        ) {
            return true;
        }
        return false;
    }
}

public class Graph {
    private static final int SIZE = 26;

    private int i = 0;

    private int countVertexes;

    private final int[][] inputGraph;
    private final int[][] outputGraph;


    private List<Edge> inputEdges;
    private final List<Edge> outputEdges;

    private Set<Character> inputVertices;
    private final List<Set<Character>> outputVertices;

    public List<Edge> getInputEdges() {
        return inputEdges;
    }

    public Set<Character> getInputVertices() {
        return inputVertices;
    }

    public Graph() {
        inputGraph = new int[SIZE][SIZE];
        outputGraph = new int[SIZE][SIZE];
        inputEdges = new LinkedList<>();
        outputEdges = new LinkedList<>();
        inputVertices = new HashSet<>();
        outputVertices = new LinkedList<>();
    }

    public void initGraph(List<Edge> inputEdges, List<Character> inputVertices) {
        this.inputEdges = new LinkedList<>(inputEdges);
        this.inputVertices = new HashSet<>(inputVertices);
        sortEdges();
    }

    public void readGraph() {
        // TODO adapter
        System.out.println("Ready to enter");
        try (Scanner scanner = new Scanner(System.in)) {
//            scanner.useDelimiter("[\n ]");
            int n = Integer.parseInt(scanner.nextLine());
//            System.out.println(n);
            for (int i = 0; i < n; i++) {
                String[] parts = scanner.nextLine().split(" ");
                char from = parts[0].toLowerCase().toCharArray()[0];
                char to = parts[1].toLowerCase().toCharArray()[0];
                int distance = Integer.parseInt(parts[2]);

                assert (from >= 'a' && from <= 'z');
                assert (to >= 'a' && to <= 'z');
                assert (distance > 0);

                while (inputGraph[from - 'a'][to - 'a'] != 0) {
                    parts = scanner.nextLine().split(" ");
                    from = parts[0].toLowerCase().toCharArray()[0];
                    to = parts[1].toLowerCase().toCharArray()[0];
                    distance = Integer.parseInt(parts[2]);
                }

                Edge edge = new Edge(from, to, distance);
                inputEdges.add(edge);

                Character vertex1 = from;
                Character vertex2 = to;
                inputVertices.add(vertex1);
                inputVertices.add(vertex2);

//                System.out.println(from + " " + to + " " + distance);
                inputGraph[from - 'a'][to - 'a'] = distance;
                inputGraph[to - 'a'][from - 'a'] = distance;
            }
        }
        countVertexes = inputVertices.size();
    }

    public void printGraph() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(inputGraph[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void printEdges(List<Edge> edges) {
        System.out.println("From\tTo\t\tDistance");
//        System.out.println("F\tT\tD");
        for (Edge edge : edges) {
            System.out.println(
                    edge.from + "\t\t" +
                            edge.to + "\t\t" +
                            edge.distance
            );
        }
    }

    public void sortEdges() {
        inputEdges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                int distance1 = edge1.distance;
                int distance2 = edge2.distance;
                return Integer.compare(distance1, distance2);
            }
        });
        System.err.println(State.SORT);
    }

    public List<Edge> getOutputEdges() {
        return outputEdges;
    }

    public State nextStep() {
        if (i >= inputEdges.size()) {
            return State.END;
        }

        Edge edge = inputEdges.get(i++);

        State state = null;
        Set<Character> tempVertexes = new HashSet<>();
        Character vertex1 = edge.from;
        Character vertex2 = edge.to;
        tempVertexes.add(vertex1);
        tempVertexes.add(vertex2);

        boolean hasFound = false;
        for (Set<Character> currently : outputVertices) {
            if (isOld(currently, tempVertexes)) {
                return State.LOOP;
            }
            if (isCommon(currently, tempVertexes)) {
                hasFound = true;
                currently.addAll(tempVertexes);
                state = State.APPEND;
            }
        }

        for (int i = 0; i < outputVertices.size(); i++) {
            for (int j = i + 1; j < outputVertices.size(); j++) {
                Set<Character> first = outputVertices.get(i);
                Set<Character> second = outputVertices.get(j);
                if (isCommon(first, second)) {
                    state = State.CONNECT_COMPONENTS;
                    first.addAll(second);
                    second.clear();
                }
            }
        }

        outputEdges.add(edge);
        if (!hasFound) {
            state = State.NEW_COMPONENT;
            outputVertices.add(tempVertexes);
        }
        if (outputVertices.get(0).size() == inputVertices.size()) {
            state = State.END;
        }
//        assert state != null;
        return state;
    }

    public void kraskal() {
        for (Edge ignored : inputEdges) {
            State state = nextStep();
            System.err.println(state);
            if (state == State.END) {
                break;
            }
        }
    }

    private boolean isCommon(Set<Character> first, Set<Character> second) {
        Set<Character> all = new HashSet<>();
        all.addAll(first);
        all.addAll(second);
        return all.size() != (first.size() + second.size());
    }

    private boolean isOld(Set<Character> first, Set<Character> second) {
        Set<Character> all = new HashSet<>();
        all.addAll(first);
        all.addAll(second);
        return all.size() == first.size();
    }

    public void printResult() {
        printEdges(outputEdges);
        for (Set<Character> vertices : outputVertices) {
            for (Character vertex : vertices) {
                System.out.print(vertex + " ");
            }
            System.out.println();
        }
    }

    public void clearOutput() {
        outputEdges.clear();
        outputVertices.clear();
        i = 0;
    }

    public void printInputEdges() {
        printEdges(inputEdges);
    }
}
