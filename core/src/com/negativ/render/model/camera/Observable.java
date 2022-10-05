package com.negativ.render.model.camera;

import org.la4j.Matrix;
import org.la4j.vector.dense.BasicVector;

public interface Observable {

    String getState();

    Matrix getViewMatrix();

    void rotate(BasicVector rotation);

    void move(BasicVector movement);

    void restoreDefault();
}
