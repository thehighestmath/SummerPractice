package ru.etu.practice;

import java.util.Objects;

public class Node {
    private final char name;

    public char getName() {
        return name;
    }

    public Node(char name){
        this.name = name;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return name == node.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
