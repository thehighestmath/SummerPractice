package ru.etu.practice;

import org.junit.jupiter.api.Test;

import java.util.*;

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
    void equalsAbAb(){
        // arrange
        Edge ab = new Edge(new Node('a'), new Node('b'), 4);
        Edge ba = new Edge(new Node('a'), new Node('b'), 4);

        // act
        boolean equality = ab.equals(ba);

        // assert
        assertTrue(equality);
    }

    @Test
    void equalsAbBa(){
        // arrange
        Edge ab = new Edge(new Node('a'), new Node('b'), 4);
        Edge ba = new Edge(new Node('b'), new Node('a'), 4);

        // act
        boolean equality = ab.equals(ba);

        // assert
        assertTrue(equality);
    }

    @Test
    void notEqualsAb3Ab4(){
        // arrange
        Edge ab1 = new Edge(new Node('a'), new Node('b'), 3);
        Edge ab2 = new Edge(new Node('a'), new Node('b'), 4);

        // act
        boolean equality = ab1.equals(ab2);

        // assert
        assertFalse(equality);
    }

    @Test
    void notEqualsAbAndNull(){
        // arrange
        Edge ab1 = new Edge(new Node('a'), new Node('b'), 3);
        Edge ab2 = null;

        // act
        boolean equality = ab1.equals(ab2);

        // assert
        assertFalse(equality);
    }

    @Test
    void sort4Edges(){
        // arrange
        
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('b'), new Node('c'), 4),
                new Edge(new Node('a'), new Node('b'), 3),
                new Edge(new Node('a'), new Node('c'), 7),
                new Edge(new Node('c'), new Node('d'), 6)
        );

        List<Node> inputVertices = Arrays.asList(
                new Node('a'),
                new Node('b'),
                new Node('c')
        );

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        alg.sortEdges();
        inputEdges = alg.getInputEdges();
        List<Edge> expectedEdges = Arrays.asList(
                new Edge(new Node('a'), new Node('b'), 3),
                new Edge(new Node('b'), new Node('c'), 4),
                new Edge(new Node('c'), new Node('d'), 6),
                new Edge(new Node('a'), new Node('c'), 7)
        );

        // assert
        assertEquals(inputEdges, expectedEdges);
    }

    @Test
    void sort8Edges(){
        // arrange
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('a'), new Node('b'), 1),
                new Edge(new Node('b'), new Node('d'), 3),
                new Edge(new Node('a'), new Node('c'), 2),
                new Edge(new Node('c'), new Node('d'), 6),
                new Edge(new Node('d'), new Node('m'), 10),
                new Edge(new Node('c'), new Node('l'), 4),
                new Edge(new Node('l'), new Node('a'), 8),
                new Edge(new Node('c'), new Node('b'), 9)
        );

        List<Node> inputVertices = Arrays.asList(
                new Node('a'),
                new Node('b'),
                new Node('c'),
                new Node('d'),
                new Node('m'),
                new Node('l')
        );

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        alg.sortEdges();
        inputEdges = alg.getInputEdges();
        List<Edge> expectedEdges = Arrays.asList(
                new Edge(new Node('a'), new Node('b'), 1),
                new Edge(new Node('a'), new Node('c'), 2),
                new Edge(new Node('b'), new Node('d'), 3),
                new Edge(new Node('c'), new Node('l'), 4),
                new Edge(new Node('c'), new Node('d'), 6),
                new Edge(new Node('l'), new Node('a'), 8),
                new Edge(new Node('c'), new Node('b'), 9),
                new Edge(new Node('d'), new Node('m'), 10)
        );

        // assert
        assertEquals(inputEdges, expectedEdges);
    }

    @Test
    void firstNextStep(){
        // arrange
        
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('b'), new Node('c'), 4),
                new Edge(new Node('a'), new Node('b'), 3),
                new Edge(new Node('a'), new Node('c'), 7)
        );
        List<Node> inputVertices = Arrays.asList(
                new Node('a'),
                new Node('b'),
                new Node('c')
        );

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        List<Object> tuple = alg.nextStep();
        List<Object> expectedTuple = new LinkedList<>();
        expectedTuple.add(State.SORT);

        // assert
        assertEquals(expectedTuple, tuple);
    }

    @Test
    void newComponentNextStep(){
        // arrange
        
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('b'), new Node('c'), 4),
                new Edge(new Node('a'), new Node('b'), 3),
                new Edge(new Node('a'), new Node('c'), 7)
        );
        List<Node> inputVertices = Arrays.asList(
                new Node('a'),
                new Node('b'),
                new Node('c')
        );

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        List<Object> tuple = alg.nextStep();
        List<Object> expectedTuple = new LinkedList<>();
        expectedTuple.add(State.SORT);

        // assert
        assertEquals(expectedTuple, tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.NEW_COMPONENT);
        expectedTuple.add(alg.getInputEdges().get(0));

        // assert
        assertEquals(expectedTuple, tuple);
    }

    @Test
    void appendNextStep(){
        // arrange
        
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('b'), new Node('c'), 4),
                new Edge(new Node('a'), new Node('b'), 3),
                new Edge(new Node('a'), new Node('c'), 7)
        );
        List<Node> inputVertices = Arrays.asList(
                new Node('a'),
                new Node('b'),
                new Node('c')
        );

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        List<Object> tuple = alg.nextStep();
        List<Object> expectedTuple = new LinkedList<>();
        expectedTuple.add(State.SORT);

        // assert
        assertEquals(expectedTuple, tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.NEW_COMPONENT);
        expectedTuple.add(alg.getInputEdges().get(0));

        // assert
        assertEquals(expectedTuple, tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.APPEND);
        expectedTuple.add(alg.getInputEdges().get(1));

        // assert
        assertEquals(expectedTuple, tuple);
    }

    @Test
    void endNextStep(){
        // arrange
        
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('b'), new Node('c'), 4),
                new Edge(new Node('a'), new Node('b'), 3),
                new Edge(new Node('a'), new Node('c'), 7)
        );
        List<Node> inputVertices = Arrays.asList(
                new Node('a'),
                new Node('b'),
                new Node('c')
        );

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        List<Object> tuple = alg.nextStep();
        List<Object> expectedTuple = new LinkedList<>();
        expectedTuple.add(State.SORT);

        // assert
        assertEquals(expectedTuple , tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.NEW_COMPONENT);
        expectedTuple.add(alg.getInputEdges().get(0));

        // assert
        assertEquals(expectedTuple , tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.APPEND);
        expectedTuple.add(alg.getInputEdges().get(1));

        // assert
        assertEquals(expectedTuple , tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.END);
        expectedTuple.add(7);

        // assert
        assertEquals(expectedTuple , tuple);
    }

    @Test
    void loopNextStep(){
        // arrange
        
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('b'), new Node('c'), 5),
                new Edge(new Node('a'), new Node('b'), 6),
                new Edge(new Node('d'), new Node('b'), 2),
                new Edge(new Node('d'), new Node('c'), 3)
        );
        List<Node> inputVertices = Arrays.asList(new Node('a'),
                new Node('b'),
                new Node('c'),
                new Node('d')
        );

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        List<Object> tuple = alg.nextStep();
        List<Object> expectedTuple = new LinkedList<>();
        expectedTuple.add(State.SORT);

        // assert
        assertEquals(expectedTuple, tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.NEW_COMPONENT);
        expectedTuple.add(alg.getInputEdges().get(0));

        // assert
        assertEquals(expectedTuple, tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.APPEND);
        expectedTuple.add(alg.getInputEdges().get(1));

        // assert
        assertEquals(expectedTuple, tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.LOOP);
        expectedTuple.add(alg.getInputEdges().get(2));

        // assert
        assertEquals(expectedTuple, tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.APPEND);
        expectedTuple.add(alg.getInputEdges().get(3));

        // assert
        assertEquals(expectedTuple, tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.END);
        expectedTuple.add(11);

        // assert
        assertEquals(expectedTuple, tuple);
    }

    @Test
    void afterEndNextStep(){
        // arrange
        
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('b'), new Node('c'), 4),
                new Edge(new Node('a'), new Node('b'), 3),
                new Edge(new Node('a'), new Node('c'), 7)
        );
        List<Node> inputVertices = Arrays.asList(
                new Node('a'),
                new Node('b'),
                new Node('c')
        );

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        List<Object> tuple = alg.nextStep();
        List<Object> expectedTuple = new LinkedList<>();
        expectedTuple.add(State.SORT);

        // assert
        assertEquals(expectedTuple , tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.NEW_COMPONENT);
        expectedTuple.add(alg.getOutputEdges().get(0));

        // assert
        assertEquals(expectedTuple , tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.APPEND);
        expectedTuple.add(alg.getInputEdges().get(1));

        // assert
        assertEquals(expectedTuple , tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.END);
        expectedTuple.add(7);

        // assert
        assertEquals(expectedTuple , tuple);

//        // act
//        tuple = alg.nextStep();
//        expectedTuple.clear();
//        expectedTuple.add(State.END);
//        expectedTuple.add(7);
//
//        // assert
//        assertEquals(expectedTuple , tuple);

        // act
        tuple = alg.nextStep();
        expectedTuple.clear();
        expectedTuple.add(State.SORT);

        // assert
        assertEquals(expectedTuple , tuple);
    }

    @Test
    void kraskalOfEmptyGraph() {
        // arrange
        
        List<Edge> inputEdges = new ArrayList<>();
        List<Node> inputVertices = new ArrayList<>();

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        alg.kruskal();
        List<Edge> outputEdges = alg.getOutputEdges();
        sortEdges(outputEdges);
        List<Edge> expectedEdges = new ArrayList<>();

        // assert
        assertEquals(outputEdges, expectedEdges);
    }

    @Test
    void kraskalGraphOf3Edges() {
        // arrange
        
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('b'), new Node('c'), 4),
                new Edge(new Node('a'), new Node('b'), 3),
                new Edge(new Node('a'), new Node('c'), 7)
        );

        List<Node> inputVertices = Arrays.asList(
                new Node('a'),
                new Node('b'),
                new Node('c')
        );

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        alg.kruskal();
        List<Edge> outputEdges = alg.getOutputEdges();
        sortEdges(outputEdges);
        List<Edge> expectedEdges = Arrays.asList(
                new Edge(new Node('a'), new Node('b'), 3),
                new Edge(new Node('b'), new Node('c'), 4)
        );

        // assert
        assertEquals(outputEdges, expectedEdges);
    }

    @Test
    void kraskalGraphOf7Edges() {
        // arrange
        
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('a'), new Node('c'), 1),
                new Edge(new Node('a'), new Node('d'), 5),
                new Edge(new Node('a'), new Node('g'), 4),
                new Edge(new Node('b'), new Node('c'), 4),
                new Edge(new Node('b'), new Node('d'), 3),
                new Edge(new Node('c'), new Node('f'), 3),
                new Edge(new Node('f'), new Node('e'), 6)
        );

        List<Node> inputVertices = Arrays.asList(
                new Node('a'),
                new Node('b'),
                new Node('c'),
                new Node('d'),
                new Node('e'),
                new Node('f'),
                new Node( 'g')
        );

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        alg.kruskal();
        List<Edge> outputEdges = alg.getOutputEdges();
        sortEdges(outputEdges);
        List<Edge> expectedEdges = Arrays.asList(
                new Edge(new Node('a'), new Node('c'), 1),
                new Edge(new Node('b'), new Node('d'), 3),
                new Edge(new Node('c'), new Node('f'), 3),
                new Edge(new Node('a'), new Node('g'), 4),
                new Edge(new Node('b'), new Node('c'), 4),
                new Edge(new Node('f'), new Node('e'), 6)
        );

        // assert
        assertEquals(outputEdges, expectedEdges);
    }

    @Test
    void kraskalGraphOf8Edges() {
        // arrange
        
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('a'), new Node('b'), 2),
                new Edge(new Node('a'), new Node('c'), 1),
                new Edge(new Node('a'), new Node('d'), 3),
                new Edge(new Node('b'), new Node('c'), 6),
                new Edge(new Node('b'), new Node('d'), 5),
                new Edge(new Node('c'), new Node('d'), 4),
                new Edge(new Node('b'), new Node('e'), 9),
                new Edge(new Node('c'), new Node('e'), 10)
        );

        List<Node> inputVertices = Arrays.asList(
                new Node('a'),
                new Node('b'),
                new Node('c'),
                new Node('d'),
                new Node('e')
        );

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        alg.kruskal();
        List<Edge> outputEdges = alg.getOutputEdges();
        sortEdges(outputEdges);
        List<Edge> expectedEdges = Arrays.asList(
                new Edge(new Node('a'), new Node('c'), 1),
                new Edge(new Node('a'), new Node('b'), 2),
                new Edge(new Node('a'), new Node('d'), 3),
                new Edge(new Node('b'), new Node('e'), 9)
        );

        // assert
        assertEquals(outputEdges, expectedEdges);
    }

    @Test
    void kraskalGraphOf13Edges() {
        // arrange
        
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('a'), new Node('b'), 1),
                new Edge(new Node('a'), new Node('c'), 3),
                new Edge(new Node('a'), new Node('e'), 2),
                new Edge(new Node('b'), new Node('d'), 2),
                new Edge(new Node('c'), new Node('d'), 1),
                new Edge(new Node('c'), new Node('e'), 2),
                new Edge(new Node('e'), new Node('j'), 10),
                new Edge(new Node('f'), new Node('g'), 1),
                new Edge(new Node('f'), new Node('h'), 4),
                new Edge(new Node('f'), new Node('j'), 1),
                new Edge(new Node('g'), new Node('j'), 1),
                new Edge(new Node('g'), new Node('i'), 1),
                new Edge(new Node('h'), new Node('i'), 5)
        );

        List<Node> inputVertices = Arrays.asList(
                new Node('a'),
                new Node('b'),
                new Node('c'),
                new Node('d'),
                new Node('e'),
                new Node('f'),
                new Node('g'),
                new Node('h'),
                new Node('i'),
                new Node('j')
        );

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        alg.kruskal();
        List<Edge> outputEdges = alg.getOutputEdges();
        sortEdges(outputEdges);
        List<Edge> expectedEdges = Arrays.asList(
                new Edge(new Node('a'), new Node('b'), 1),
                new Edge(new Node('c'), new Node('d'), 1),
                new Edge(new Node('f'), new Node('g'), 1),
                new Edge(new Node('f'), new Node('j'), 1),
                new Edge(new Node('g'), new Node('i'), 1),
                new Edge(new Node('a'), new Node('e'), 2),
                new Edge(new Node('b'), new Node('d'), 2),
                new Edge(new Node('f'), new Node('h'), 4),
                new Edge(new Node('e'), new Node('j'), 10)
        );

        // assert
        assertEquals(outputEdges, expectedEdges);
    }

    @Test
    void kraskalGraphOf40Edges() {
        // arrange
        
        List<Edge> inputEdges = Arrays.asList(
                new Edge(new Node('a'), new Node('b'), 4),
                new Edge(new Node('a'), new Node('c'), 20),
                new Edge(new Node('b'), new Node('d'), 10),
                new Edge(new Node('b'), new Node('x'), 1),
                new Edge(new Node('c'), new Node('x'), 30),
                new Edge(new Node('c'), new Node('z'), 4),
                new Edge(new Node('d'), new Node('f'), 21),
                new Edge(new Node('d'), new Node('w'), 6),
                new Edge(new Node('e'), new Node('z'), 15),
                new Edge(new Node('f'), new Node('h'), 5),
                new Edge(new Node('g'), new Node('i'), 26),
                new Edge(new Node('g'), new Node('v'), 9),
                new Edge(new Node('h'), new Node('n'), 22),
                new Edge(new Node('h'), new Node('w'), 1),
                new Edge(new Node('i'), new Node('v'), 5),
                new Edge(new Node('i'), new Node('z'), 7),
                new Edge(new Node('j'), new Node('y'), 13),
                new Edge(new Node('k'), new Node('m'), 29),
                new Edge(new Node('k'), new Node('z'), 27),
                new Edge(new Node('l'), new Node('u'), 12),
                new Edge(new Node('m'), new Node('o'), 28),
                new Edge(new Node('m'), new Node('v'), 25),
                new Edge(new Node('n'), new Node('u'), 23),
                new Edge(new Node('o'), new Node('z'), 16),
                new Edge(new Node('p'), new Node('u'), 7),
                new Edge(new Node('q'), new Node('v'), 14),
                new Edge(new Node('r'), new Node('t'), 11),
                new Edge(new Node('r'), new Node('y'), 18),
                new Edge(new Node('r'), new Node('z'), 8),
                new Edge(new Node('s'), new Node('t'), 24),
                new Edge(new Node('s'), new Node('v'), 6),
                new Edge(new Node('t'), new Node('v'), 8),
                new Edge(new Node('t'), new Node('z'), 19),
                new Edge(new Node('u'), new Node('v'), 17),
                new Edge(new Node('v'), new Node('y'), 3),
                new Edge(new Node('v'), new Node('z'), 9),
                new Edge(new Node('w'), new Node('x'), 2),
                new Edge(new Node('x'), new Node('y'), 10),
                new Edge(new Node('x'), new Node('z'), 2),
                new Edge(new Node('y'), new Node('z'), 3)
        );

        List<Node> inputVertices = Arrays.asList(
                new Node('a'),
                new Node('b'),
                new Node('c'),
                new Node('d'),
                new Node('e'),
                new Node('f'),
                new Node('g'),
                new Node('h'),
                new Node('i'),
                new Node('j'),
                new Node('k'),
                new Node('l'),
                new Node('m'),
                new Node('n'),
                new Node('o'),
                new Node('p'),
                new Node('q'),
                new Node('r'),
                new Node('s'),
                new Node('t'),
                new Node('u'),
                new Node('v'),
                new Node('w'),
                new Node('x'),
                new Node('y'),
                new Node('z')
        );

        // act
        Kruskal alg = new Kruskal();
        alg.initKruskal(inputEdges, inputVertices);
        alg.kruskal();
        List<Edge> outputEdges = alg.getOutputEdges();
        sortEdges(outputEdges);
        List<Edge> expectedEdges = Arrays.asList(
                new Edge(new Node('b'), new Node('x'), 1),
                new Edge(new Node('h'), new Node('w'), 1),
                new Edge(new Node('w'), new Node('x'), 2),
                new Edge(new Node('x'), new Node('z'), 2),
                new Edge(new Node('v'), new Node('y'), 3),
                new Edge(new Node('y'), new Node('z'), 3),
                new Edge(new Node('a'), new Node('b'), 4),
                new Edge(new Node('c'), new Node('z'), 4),
                new Edge(new Node('f'), new Node('h'), 5),
                new Edge(new Node('i'), new Node('v'), 5),
                new Edge(new Node('d'), new Node('w'), 6),
                new Edge(new Node('s'), new Node('v'), 6),
                new Edge(new Node('p'), new Node('u'), 7),
                new Edge(new Node('r'), new Node('z'), 8),
                new Edge(new Node('t'), new Node('v'), 8),
                new Edge(new Node('g'), new Node('v'), 9),
                new Edge(new Node('l'), new Node('u'), 12),
                new Edge(new Node('j'), new Node('y'), 13),
                new Edge(new Node('q'), new Node('v'), 14),
                new Edge(new Node('e'), new Node('z'), 15),
                new Edge(new Node('o'), new Node('z'), 16),
                new Edge(new Node('u'), new Node('v'), 17),
                new Edge(new Node('h'), new Node('n'), 22),
                new Edge(new Node('m'), new Node('v'), 25),
                new Edge(new Node('k'), new Node('z'), 27)
        );

        // assert
        assertEquals(outputEdges, expectedEdges);
    }
}