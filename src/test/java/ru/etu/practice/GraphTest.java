package ru.etu.practice;

import org.junit.jupiter.api.Test;

import java.util.*;

//import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    public void sortEdges(List<Edge> list) {
        list.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                int distance1 = edge1.distance;
                int distance2 = edge2.distance;
                return Integer.compare(distance1, distance2);
            }
        });
        System.err.println(State.SORT);
    }

    @Test
    void equalsAbAb(){
        // arrange
        Edge ab = new Edge('a', 'b', 4);
        Edge ba = new Edge('a', 'b', 4);

        // act
        boolean equality = ab.equals(ba);

        // assert
        assertTrue(equality);
    }

    @Test
    void equalsAbBa(){
        // arrange
        Edge ab = new Edge('a', 'b', 4);
        Edge ba = new Edge('b', 'a', 4);

        // act
        boolean equality = ab.equals(ba);

        // assert
        assertTrue(equality);
    }

    @Test
    void notEqualsAb3Ab4(){
        // arrange
        Edge ab1 = new Edge('a', 'b', 3);
        Edge ab2 = new Edge('a', 'b', 4);

        // act
        boolean equality = ab1.equals(ab2);

        // assert
        assertFalse(equality);
    }

    @Test
    void notEqualsAbAndNull(){
        // arrange
        Edge ab1 = new Edge('a', 'b', 3);
        Edge ab2 = null;

        // act
        boolean equality = ab1.equals(ab2);

        // assert
        assertFalse(equality);
    }

    @Test
    void sort4Edges(){
        // arrange
        Graph graph = new Graph();
        List<Edge> inputEdges = Arrays.asList(
                new Edge('b', 'c', 4),
                new Edge('a', 'b', 3),
                new Edge('a', 'c', 7),
                new Edge('c', 'd', 6)
        );

        List<Character> inputVertices = Arrays.asList('a', 'b', 'c');
        graph.initGraph(inputEdges, inputVertices);

        // act
        graph.sortEdges();
        inputEdges = graph.getInputEdges();
        List<Edge> expectedEdges = Arrays.asList(
                new Edge('a', 'b', 3),
                new Edge('b', 'c', 4),
                new Edge('c', 'd', 6),
                new Edge('a', 'c', 7)
        );

        // assert
        assertEquals(inputEdges, expectedEdges);
    }

    @Test
    void sort8Edges(){
        // arrange
        Graph graph = new Graph();
        List<Edge> inputEdges = Arrays.asList(
                new Edge('a', 'b', 1),
                new Edge('b', 'd', 3),
                new Edge('a', 'c', 2),
                new Edge('c', 'd', 6),
                new Edge('d', 'm', 10),
                new Edge('c', 'l', 4),
                new Edge('l', 'a', 8),
                new Edge('c', 'b', 9)
        );

        List<Character> inputVertices = Arrays.asList('a', 'b', 'c', 'd', 'm', 'l');
        graph.initGraph(inputEdges, inputVertices);

        // act
        graph.sortEdges();
        inputEdges = graph.getInputEdges();
        List<Edge> expectedEdges = Arrays.asList(
                new Edge('a', 'b', 1),
                new Edge('a', 'c', 2),
                new Edge('b', 'd', 3),
                new Edge('c', 'l', 4),
                new Edge('c', 'd', 6),
                new Edge('l', 'a', 8),
                new Edge('c', 'b', 9),
                new Edge('d', 'm', 10)
        );

        // assert
        assertEquals(inputEdges, expectedEdges);
    }

    @Test
    void kraskalOfEmptyGraph() {
        // arrange
        Graph graph = new Graph();
        List<Edge> inputEdges = new ArrayList<>();
        List<Character> inputVertices = new ArrayList<>();
        graph.initGraph(inputEdges, inputVertices);

        // act
        graph.kraskal();
        List<Edge> outputEdges = graph.getOutputEdges();
        sortEdges(outputEdges);
        List<Edge> expectedEdges = new ArrayList<>();

        // assert
        assertEquals(outputEdges, expectedEdges);
    }

    @Test
    void kraskalGraphOf3Edges() {
        // arrange
        Graph graph = new Graph();
        List<Edge> inputEdges = Arrays.asList(
                new Edge('b', 'c', 4),
                new Edge('a', 'b', 3),
                new Edge('a', 'c', 7)
        );

        List<Character> inputVertices = Arrays.asList
                ('a', 'b', 'c');
        graph.initGraph(inputEdges, inputVertices);

        // act
        graph.kraskal();
        List<Edge> outputEdges = graph.getOutputEdges();
        sortEdges(outputEdges);
        List<Edge> expectedEdges = Arrays.asList(
                new Edge('a', 'b', 3),
                new Edge('b', 'c', 4)
        );

        // assert
        assertEquals(outputEdges, expectedEdges);
    }

    @Test
    void kraskalGraphOf7Edges() {
        // arrange
        Graph graph = new Graph();
        List<Edge> inputEdges = Arrays.asList(
                new Edge('a', 'c', 1),
                new Edge('a', 'd', 5),
                new Edge('a', 'g', 4),
                new Edge('b', 'c', 4),
                new Edge('b', 'd', 3),
                new Edge('c', 'f', 3),
                new Edge('f', 'e', 6)
        );

        List<Character> inputVertices = Arrays.asList
                ('a', 'b', 'c', 'd', 'e', 'f', 'g');
        graph.initGraph(inputEdges, inputVertices);

        // act
        graph.kraskal();
        List<Edge> outputEdges = graph.getOutputEdges();
        sortEdges(outputEdges);
        List<Edge> expectedEdges = Arrays.asList(
                new Edge('a', 'c', 1),
                new Edge('b', 'd', 3),
                new Edge('c', 'f', 3),
                new Edge('a', 'g', 4),
                new Edge('b', 'c', 4),
                new Edge('f', 'e', 6)
        );

        // assert
        assertEquals(outputEdges, expectedEdges);
    }

    @Test
    void kraskalGraphOf8Edges() {
        // arrange
        Graph graph = new Graph();
        List<Edge> inputEdges = Arrays.asList(
                new Edge('a', 'b', 2),
                new Edge('a', 'c', 1),
                new Edge('a', 'd', 3),
                new Edge('b', 'c', 6),
                new Edge('b', 'd', 5),
                new Edge('c', 'd', 4),
                new Edge('b', 'e', 9),
                new Edge('c', 'e', 10)
        );

        List<Character> inputVertices = Arrays.asList
                ('a', 'b', 'c', 'd', 'e');
        graph.initGraph(inputEdges, inputVertices);

        // act
        graph.kraskal();
        List<Edge> outputEdges = graph.getOutputEdges();
        sortEdges(outputEdges);
        List<Edge> expectedEdges = Arrays.asList(
                new Edge('a', 'c', 1),
                new Edge('a', 'b', 2),
                new Edge('a', 'd', 3),
                new Edge('b', 'e', 9)
        );

        // assert
        assertEquals(outputEdges, expectedEdges);
    }

    @Test
    void kraskalGraphOf13Edges() {
        // arrange
        Graph graph = new Graph();
        List<Edge> inputEdges = Arrays.asList(
                new Edge('a', 'b', 1),
                new Edge('a', 'c', 3),
                new Edge('a', 'e', 2),
                new Edge('b', 'd', 2),
                new Edge('c', 'd', 1),
                new Edge('c', 'e', 2),
                new Edge('e', 'j', 10),
                new Edge('f', 'g', 1),
                new Edge('f', 'h', 4),
                new Edge('f', 'j', 1),
                new Edge('g', 'j', 1),
                new Edge('g', 'i', 1),
                new Edge('h', 'i', 5)
        );

        List<Character> inputVertices = Arrays.asList
                ('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
        graph.initGraph(inputEdges, inputVertices);

        // act
        graph.kraskal();
        List<Edge> outputEdges = graph.getOutputEdges();
        sortEdges(outputEdges);
        List<Edge> expectedEdges = Arrays.asList(
                new Edge('a', 'b', 1),
                new Edge('c', 'd', 1),
                new Edge('f', 'g', 1),
                new Edge('f', 'j', 1),
                new Edge('g', 'i', 1),
                new Edge('a', 'e', 2),
                new Edge('b', 'd', 2),
                new Edge('f', 'h', 4),
                new Edge('e', 'j', 10)
        );

        // assert
        assertEquals(outputEdges, expectedEdges);
    }

    @Test
    void kraskalGraphOf40Edges() {
        // arrange
        Graph graph = new Graph();
        List<Edge> inputEdges = Arrays.asList(
                new Edge('a', 'b', 4),
                new Edge('a', 'c', 20),
                new Edge('b', 'd', 10),
                new Edge('b', 'x', 1),
                new Edge('c', 'x', 30),
                new Edge('c', 'z', 4),
                new Edge('d', 'f', 21),
                new Edge('d', 'w', 6),
                new Edge('e', 'z', 15),
                new Edge('f', 'h', 5),
                new Edge('g', 'i', 26),
                new Edge('g', 'v', 9),
                new Edge('h', 'n', 22),
                new Edge('h', 'w', 1),
                new Edge('i', 'v', 5),
                new Edge('i', 'z', 7),
                new Edge('j', 'y', 13),
                new Edge('k', 'm', 29),
                new Edge('k', 'z', 27),
                new Edge('l', 'u', 12),
                new Edge('m', 'o', 28),
                new Edge('m', 'v', 25),
                new Edge('n', 'u', 23),
                new Edge('o', 'z', 16),
                new Edge('p', 'u', 7),
                new Edge('q', 'v', 14),
                new Edge('r', 't', 11),
                new Edge('r', 'y', 18),
                new Edge('r', 'z', 8),
                new Edge('s', 't', 24),
                new Edge('s', 'v', 6),
                new Edge('t', 'v', 8),
                new Edge('t', 'z', 19),
                new Edge('u', 'v', 17),
                new Edge('v', 'y', 3),
                new Edge('v', 'z', 9),
                new Edge('w', 'x', 2),
                new Edge('x', 'y', 10),
                new Edge('x', 'z', 2),
                new Edge('y', 'z', 3)
        );

        List<Character> inputVertices = Arrays.asList
                ('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                        't', 'u', 'v', 'w', 'x', 'y', 'z');
        graph.initGraph(inputEdges, inputVertices);

        // act
        graph.kraskal();
        List<Edge> outputEdges = graph.getOutputEdges();
        sortEdges(outputEdges);
        List<Edge> expectedEdges = Arrays.asList(
                new Edge('b', 'x', 1),
                new Edge('h', 'w', 1),
                new Edge('w', 'x', 2),
                new Edge('x', 'z', 2),
                new Edge('v', 'y', 3),
                new Edge('y', 'z', 3),
                new Edge('a', 'b', 4),
                new Edge('c', 'z', 4),
                new Edge('f', 'h', 5),
                new Edge('i', 'v', 5),
                new Edge('d', 'w', 6),
                new Edge('s', 'v', 6),
                new Edge('p', 'u', 7),
                new Edge('r', 'z', 8),
                new Edge('t', 'v', 8),
                new Edge('g', 'v', 9),
                new Edge('l', 'u', 12),
                new Edge('j', 'y', 13),
                new Edge('q', 'v', 14),
                new Edge('e', 'z', 15),
                new Edge('o', 'z', 16),
                new Edge('u', 'v', 17),
                new Edge('h', 'n', 22),
                new Edge('m', 'v', 25),
                new Edge('k', 'z', 27)
        );

        // assert
        assertEquals(outputEdges, expectedEdges);
    }
}