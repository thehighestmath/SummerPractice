package ru.etu.practice;

import java.util.*;

public class Kruskal {

    private final Graph graph;
    private int stepID = -1;
    private int weight = 0;

    public List<Edge> getInputEdges() {
        return inputEdges;
    }

    private List<Edge> inputEdges;
    private List<Node> inputVertices;
    private final List<Edge> outputEdges;
    private final List<Set<Node>> outputVertices;

    public boolean isStarted = false;

    public Kruskal() {
        this.graph = new Graph();
        inputEdges = new LinkedList<>(graph.getInputEdges());
        inputVertices = graph.getInputVertices();
        outputEdges = new LinkedList<>();
        outputVertices = new LinkedList<>();
    }

    public void initKruskal(List<Edge> inputEdges, List<Node> inputVertices) {
        this.inputEdges = inputEdges;
        this.inputVertices = inputVertices;
        this.graph.initGraph(inputEdges, inputVertices);
    }

    public void sortEdges() {
        inputEdges = new LinkedList<>(graph.getInputEdges());
        inputEdges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                int distance1 = edge1.distance;
                int distance2 = edge2.distance;
                return Integer.compare(distance1, distance2);
            }
        });
    }

    public List<Edge> getOutputEdges() {
        return outputEdges;
    }

    public List<Node> getInputVertices() {
        return inputVertices;
    }

    public Graph getGraph() {
        return graph;
    }

    /**
     * tuple.get(0) instanceof State -- return state
     * tuple.get(1) instanceof Edge -- added edge
     *
     * @return tuple
     */
    public List<Object> nextStep() {
        List<Object> tuple = new LinkedList<>();
        if (stepID == -1 || !isStarted) {
            isStarted = true;
            graph.isModified = false;
            sortEdges();
            stepID++;
            tuple.add(State.SORT);
            return tuple;
        }

        if (stepID >= inputEdges.size() || graph.isModified) {
            isStarted = false;
            tuple.add(State.END);
            tuple.add(weight);
            stepID = -1;
            graph.isModified = true;
            return tuple;
        }

        Edge edge = inputEdges.get(stepID++);
        weight += edge.distance;
        Set<Node> tempVertexes = new HashSet<>();
        Node vertex1 = edge.from;
        Node vertex2 = edge.to;
        tempVertexes.add(vertex1);
        tempVertexes.add(vertex2);

        if (outputVertices.size() > 0) {
            if (outputVertices.get(0).size() == inputVertices.size()) {
                tuple.add(State.END);
                weight -= edge.distance;
                tuple.add(weight);
                stepID = -1;
                graph.isModified = true;
                return tuple;
            }
        }

        boolean hasFound = false;
        for (Set<Node> currently : outputVertices) {
            if (isOld(currently, tempVertexes)) {
                tuple.add(State.LOOP);
                tuple.add(edge);
                weight -= edge.distance;
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
                Set<Node> first = outputVertices.get(i);
                Set<Node> second = outputVertices.get(j);
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
        return tuple;
    }

    public String kruskal() {
        stepID = -1;
        StringBuilder result = new StringBuilder();
        while (true) {
            List<Object> tuple = nextStep();
            result.append(addStepInfo(tuple));
            //System.err.println(tuple.get(0));
            if (tuple.get(0) == State.END) {
                graph.isModified = true;
                break;
            }
        }
        result.append("==========================");
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

    private boolean isCommon(Set<Node> first, Set<Node> second) {
        Set<Node> all = new HashSet<>();
        all.addAll(first);
        all.addAll(second);
        return all.size() != (first.size() + second.size());
    }

    private boolean isOld(Set<Node> first, Set<Node> second) {
        Set<Node> all = new HashSet<>();
        all.addAll(first);
        all.addAll(second);
        return all.size() == first.size();
    }

    public void clearOutput() {
        outputEdges.clear();
        outputVertices.clear();
        stepID = -1;
        weight = 0;
    }

    public void clear() {
        clearOutput();
        inputVertices.clear();
        inputEdges.clear();
        graph.clear();
    }
}
