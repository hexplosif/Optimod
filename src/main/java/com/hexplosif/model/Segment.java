package com.hexplosif.model;

public class Segment {
    int length;
    String name;
    Node start;
    Node end;

    public Segment(int length, String name, Node start, Node end) {
        this.length = length;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public Node getStart() {
        return start;
    }
}
