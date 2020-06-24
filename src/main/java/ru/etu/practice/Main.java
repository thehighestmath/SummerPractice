package ru.etu.practice;

public class Main {
    public static void main(String[] args) {
//        MainWindow mainWindow = new MainWindow();
//        mainWindow.setVisible(true);
        Graph graph = new Graph();
        graph.readGraph();
//        graph.printGraph();
//        graph.printInputEdges();
        graph.sortEdges();
//        graph.printInputEdges();
        graph.kraskal();
        graph.printResult();
    }
}
