package ru.etu.practice;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KruskalTest {

    public void sortEdges(List<Edge> list) {
        list.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                int distance1 = edge1.distance;
                int distance2 = edge2.distance;
                return Integer.compare(distance1, distance2);
            }
        });
    }

    @Test
    void test1() {
        Graph graph = new Graph();
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('a'), new Node('b'), 1),
                new Edge(new Node('b'), new Node('c'), 10),
                new Edge(new Node('a'), new Node('c'), 2),
                new Edge(new Node('b'), new Node('d'), 3),
                new Edge(new Node('c'), new Node('d'), 6),
                new Edge(new Node('d'), new Node('m'), 10),
                new Edge(new Node('c'), new Node('l'), 4),
                new Edge(new Node('l'), new Node('a'), 8)
        );

        List<Node> inputVertices = Arrays.asList(
                new Node('a'),
                new Node('b'),
                new Node('c'),
                new Node('d'),
                new Node('m'),
                new Node('l'));
        graph.initGraph(inputEdges, inputVertices);

        // act
        Kruskal alg = new Kruskal(graph);
        alg.kruskal();

        List<Edge> expectedEdges = Arrays.asList(
                new Edge(new Node('a'), new Node('b'), 1),
                new Edge(new Node('a'), new Node('c'), 2),
                new Edge(new Node('b'), new Node('d'), 3),
                new Edge(new Node('c'), new Node('l'), 4),
                new Edge(new Node('d'), new Node('m'), 10)
        );

        // assert
        assertEquals(alg.getOutputEdges(), expectedEdges);
    }

}