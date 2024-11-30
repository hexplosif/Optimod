package com.hexplosif.optimod.model;

import lombok.Data;

@Data
public class Segment {

    private Long id;

    private Long idOrigin;

    private Long idDestination;

    private Double length;

    private String name;
}