package com.negativ.render.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.la4j.vector.dense.BasicVector;

import java.util.List;

@Data
@AllArgsConstructor
public class ModelData {
    private List<Vertex> vertexes;
    private List<Polygon> polygons;
    private List<BasicVector> normals;
}
