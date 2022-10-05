package com.negativ.render.utils;

import com.negativ.render.model.Polygon;
import com.negativ.render.model.Vertex;
import com.negativ.render.model.ModelData;
import org.la4j.vector.dense.BasicVector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    public final static Loader INSTANCE = new Loader();
    private final static String SPLITTER = " ";
    private final static String POLYGON_VERTEXES_SPLITTER = "/";
    private final static String VERTEX_MARKER = "v";
    private final static String POLYGON_MARKER = "f";
    private final static String VERTEX_NORMAL_MARKER = "vn";
    private final static int VALUES_IN_VERTEX_LINE_COUNT = 3;
    private final static int VERTEX_NORMALS_IN_LINE_COUNT = 3;
    private final static int MIN_VALUES_IN_POLYGON_LINE_COUNT = 3;

    private Loader() {
    }

    public ModelData loadModel(String fileName) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        List<Vertex> vertexes = new ArrayList<>();
        List<Polygon> polygons = new ArrayList<>();
        List<BasicVector> normals = new ArrayList<>();
        br.lines()
                .filter(line -> line.startsWith(VERTEX_MARKER) || line.startsWith(POLYGON_MARKER))
                .map(line -> line.trim().replaceAll(" +", " "))
                .forEach(line -> {
                    String[] components = line.split(SPLITTER);
                    if (components[0].equals(VERTEX_MARKER)) {
                        if (components.length >= VALUES_IN_VERTEX_LINE_COUNT + 1) {
                            double w = components.length == VALUES_IN_VERTEX_LINE_COUNT + 2 ? Double.parseDouble(components[4]) : 1;
                            vertexes.add(new Vertex(Double.parseDouble(components[1]), Double.parseDouble(components[2]), Double.parseDouble(components[3]), w));
                        } else {
                            System.out.println("LIne '" + line + "' was skipped");
                        }
                    } else if (components[0].equals(POLYGON_MARKER)) {
                        if (components.length > MIN_VALUES_IN_POLYGON_LINE_COUNT) {
                            List<Integer> polygonVertexesId = new ArrayList<>();
                            for (int i = 1; i < components.length; i++) {
                                String[] idArray = components[i].split(POLYGON_VERTEXES_SPLITTER);
                                polygonVertexesId.add(Integer.parseInt(idArray[0]));
                            }
                            polygons.add(new Polygon(polygonVertexesId));
                        } else {
                            System.out.println("Line '" + line + "' was skipped");
                        }
                    } else if (components[0].equals(VERTEX_NORMAL_MARKER)) {
                        if (components.length == VERTEX_NORMALS_IN_LINE_COUNT + 1) {
                            normals.add(new BasicVector(new double[]{Double.parseDouble(components[1]),
                                    Double.parseDouble(components[2]), Double.parseDouble(components[3])}));
                        } else {
                            System.out.println("LIne '" + line + "' was skipped");
                        }
                    }
                });
        return new ModelData(vertexes, polygons, normals);
    }
}
