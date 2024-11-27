package com.hexplosif.model;

import java.util.List;

public class Map {
    List<Nodes> nodes;
    List<Segment> segments;

    public Map(List<Nodes> nodes, List<Segment> segments) {
        this.nodes = nodes;
        this.segments = segments;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public List<Nodes> getNodes() {
        return nodes;
    }

    public void loadMap(String XMLfilename) {
        // Load the map from the XML file
    }
}
