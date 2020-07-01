package ru.etu.practice;

import java.util.*;

class Edge {
    @Override
    public String toString() {
        return "Edge " + " " + from + "—" + to;
    }

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

    private int i = -1;
    private int value = 0;

    private final int[][] inputGraph;

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
        inputEdges = new LinkedList<>();
        outputEdges = new LinkedList<>();
        inputVertices = new HashSet<>();
        outputVertices = new LinkedList<>();
    }

    public void initGraph(List<Edge> inputEdges, List<Character> inputVertices) {
        this.inputEdges = new LinkedList<>(inputEdges);
        this.inputVertices = new HashSet<>(inputVertices);
//        sortEdges();
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

    /**
     * tuple.get(0) instanceof State -- return state
     * tuple.get(1) instanceof Edge -- added edge
     *
     * @return tuple
     */
    public List<Object> nextStep() {
        List<Object> tuple = new LinkedList<>();
        if (i == -1) {
            sortEdges();
            i++;
            tuple.add(State.SORT);
            return tuple;
        }
        if (i >= inputEdges.size()) {
            tuple.add(State.END);
            tuple.add(value);
            return tuple;
        }

        Edge edge = inputEdges.get(i++);
        value += edge.distance;
        Set<Character> tempVertexes = new HashSet<>();
        Character vertex1 = edge.from;
        Character vertex2 = edge.to;
        tempVertexes.add(vertex1);
        tempVertexes.add(vertex2);

        if (outputVertices.size() > 0) {
            if (outputVertices.get(0).size() == inputVertices.size()) {
                tuple.add(State.END);
                value -= edge.distance;
                tuple.add(value);
                return tuple;
            }
        }

        boolean hasFound = false;
        for (Set<Character> currently : outputVertices) {
            if (isOld(currently, tempVertexes)) {
                tuple.add(State.LOOP);
                tuple.add(edge);
                value -= edge.distance;
                return tuple;
            }
            if (isCommon(currently, tempVertexes)) {
                hasFound = true;
                currently.addAll(tempVertexes);
                tuple.add(State.APPEND);
                tuple.add(edge);
            }
        }

        for (int i = 0; i < outputVertices.size(); i++) {
            for (int j = i + 1; j < outputVertices.size(); j++) {
                Set<Character> first = outputVertices.get(i);
                Set<Character> second = outputVertices.get(j);
                if (isCommon(first, second)) {
                    tuple.add(State.CONNECT_COMPONENTS);
                    tuple.add(edge);
                    first.addAll(second);
                    second.clear();
                }
            }
        }

        outputEdges.add(edge);
        if (!hasFound) {
            tuple.add(State.NEW_COMPONENT);
            tuple.add(edge);
            outputVertices.add(tempVertexes);
        }
//        if (outputVertices.get(0).size() == inputVertices.size()) {
//            tuple.add(State.END);
//            value -= edge.distance;
//            tuple.add(value);
//            return tuple;
//        }
//        assert state != null;
        return tuple;
    }

    public String kraskal() {
        i = -1;
        StringBuilder result = new StringBuilder();
        while (true) {
            List<Object> tuple = nextStep();
            result.append(addStepInfo(tuple));
            System.err.println(tuple.get(0));
            if (tuple.get(0) == State.END) {
                break;
            }
        }
        result.append("============================");
        return result.toString();
    }

    public static String addStepInfo(List<Object> tuple) {
        State state = (State) tuple.get(0);
        StringBuilder addText = new StringBuilder();
        if (state == State.SORT) {
            addText.append(state);
        } else if (state == State.END) {
            assert tuple.size() > 1;
            addText.append(state);
            addText.append("\n");
            int value = (int) tuple.get(1);
            addText.append("Minimum spanning tree weight is ");
            addText.append(value);
        } else if (state == State.LOOP) {
            assert tuple.size() > 1;
            addText.append(state);
            addText.append(" | ");
            Edge edge = (Edge) tuple.get(1);
            addText.append(edge);
            addText.append(" will not be added");
        } else {
            assert tuple.size() > 1;
            addText.append(state);
            addText.append(" | ");
            Edge edge = (Edge) tuple.get(1);
            addText.append(edge);
            addText.append(" added");
        }
        return addText.toString() + "\n";
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
        i = -1;
        value = 0;
    }

    public void printInputEdges() {
        printEdges(inputEdges);
    }
}
