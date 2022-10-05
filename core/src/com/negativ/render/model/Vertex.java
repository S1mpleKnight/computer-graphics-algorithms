package com.negativ.render.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

@Data
@AllArgsConstructor
public class Vertex {

    private Double x;
    private Double y;
    private Double z;
    private Double w;

    public Vertex(double[] array) {
        x = array[0];
        y = array[1];
        z = array[2];
        w = array[3];
    }

    public Vertex multiply(Matrix matrix) {
        BasicVector vector = (BasicVector) matrix.multiply(toVector());
        return new Vertex(vector.toArray());
    }

    public BasicVector toThreeDimensionalVector() {
        return new BasicVector(new double[] {x / w, y / w, z / w});
    }

    public Vector toVector() {
        return new BasicVector(new double[]{x, y, z, w});
    }

    @Override
    public String toString() {
        return String.format("x: %7f y: %7f z: %7f w: %7f", x, y, z, w);
    }
}
