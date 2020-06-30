package ru.etu.practice;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();

//        List<Object> list = f();
//        int a = (int) list.get(0);
//        float f = (float) list.get(1);
//        boolean b = (boolean) list.get(2);
//        String s = (String) list.get(3);
//
//        System.out.println(a);
//        System.out.println(f);
//        System.out.println(b);
//        System.out.println(s);
//        Graph graph = new Graph();
//        graph.readGraph();
//        graph.sortEdges();
//        graph.printResult();
    }

    public static List<Object> f() {
        List<Object> list = new LinkedList<>();
        int a = 5;
        float f = 1.2f;
        boolean b = true;
        String s = "Qwerty";
        list.add(a);
        list.add(f);
        list.add(b);
        list.add(s);
        return list;
    }
}
