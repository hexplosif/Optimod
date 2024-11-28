package com.hexplosif.optimod.model;

public class Segment {
    double length;
    String name;
    Node start;
    Node end;

    public Segment(double length, String name, Node start, Node end) {
        this.length = length;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public double getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }
}
