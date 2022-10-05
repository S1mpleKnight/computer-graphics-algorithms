package com.negativ.render.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Polygon {
    private List<Integer> vertexesIds;
}
