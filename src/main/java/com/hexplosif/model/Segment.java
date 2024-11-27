package com.hexplosif.model;

public class Segment {
    double length;
    String name;
    Nodes start;
    Nodes end;

    public Segment(double length, String name, Nodes start, Nodes end) {
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

    public Nodes getStart() {
        return start;
    }

    public Nodes getEnd() {
        return end;
    }
}
