package com.negativ.render.model.camera;

import com.negativ.render.utils.Constants;
import com.negativ.render.utils.MatrixUtils;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

public class Camera implements Observable {

    private static final BasicVector DEFAULT_CAMERA_POSITION = new BasicVector(new double[]{0, 0, -10});
    private static final BasicVector DEFAULT_CAMERA_ROTATION = new BasicVector(new double[]{0, Math.PI, Math.PI});
    private BasicVector position = DEFAULT_CAMERA_POSITION;
    private BasicVector rotation = DEFAULT_CAMERA_ROTATION;

    @Override
    public String getState() {
        return "Position: " + position + "\n" +
                "Rotation: " + rotation;
    }

    @Override
    public Matrix getViewMatrix() {
        Matrix translationMatrix = MatrixUtils.getTranslationMatrix(position);
        Matrix xRotationMatrix = MatrixUtils.getXRotationMatrix(rotation);
        Matrix yRotationMatrix = MatrixUtils.getYRotationMatrix(rotation);
        Matrix zRotationMatrix = MatrixUtils.getZRotationMatrix(rotation);
        return zRotationMatrix.multiply(yRotationMatrix).multiply(xRotationMatrix).multiply(translationMatrix);
    }

    @Override
    public void move(BasicVector movement) {
        Matrix xRotationMatrix = MatrixUtils.getXRotationMatrix(rotation);
        Matrix yRotationMatrix = MatrixUtils.getYRotationMatrix(rotation);
        Matrix zRotationMatrix = MatrixUtils.getZRotationMatrix(rotation);
        Vector vector = movement.multiply(xRotationMatrix).multiply(yRotationMatrix).multiply(zRotationMatrix);
        position = (BasicVector) position.add(new BasicVector(new double[]{vector.get(Constants.X_PARAM),
                vector.get(Constants.Y_PARAM),
                vector.get(Constants.Z_PARAM)}));
    }

    @Override
    public void restoreDefault() {
        position = DEFAULT_CAMERA_POSITION;
        rotation = DEFAULT_CAMERA_ROTATION;
    }

    @Override
    public void rotate(BasicVector rotation) {
        this.rotation = (BasicVector) this.rotation.add(rotation);
    }
}
