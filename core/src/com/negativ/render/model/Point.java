package com.negativ.render.model;

import lombok.Data;

@Data
public class Point {
    private Integer x;
    private Integer y;
    private Integer z;

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return String.format("x: %4d y: %4d z: %4d", x, y, z);
    }
}
