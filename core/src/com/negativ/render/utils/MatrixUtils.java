package com.negativ.render.utils;


import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.dense.BasicVector;

public class MatrixUtils {
    public static final MatrixUtils INSTANCE = new MatrixUtils();

    private MatrixUtils() {
    }

    public static Matrix getTranslationMatrix(BasicVector translation) {
        double[][] matrix = new double[][]{
                {1f, 0, 0, translation.get(Constants.X_PARAM)},
                {0, 1f, 0, translation.get(Constants.Y_PARAM)},
                {0, 0, 1f, translation.get(Constants.Z_PARAM)},
                {0, 0, 0, 1f}
        };
        return new Basic2DMatrix(matrix);
    }


    public static Matrix getScaleMatrix(BasicVector scale) {
        double[][] matrix = new double[][]{
                {scale.get(Constants.X_PARAM), 0, 0, 0},
                {0, scale.get(Constants.Y_PARAM), 0, 0},
                {0, 0, scale.get(Constants.Z_PARAM), 0},
                {0, 0, 0, 1f}
        };
        return new Basic2DMatrix(matrix);
    }

    public static Matrix getXRotationMatrix(BasicVector rotation) {
        double[][] matrix = new double[][]{
                {1f, 0, 0, 0},
                {0, Math.cos(rotation.get(Constants.X_PARAM)), -1 * Math.sin(rotation.get(Constants.X_PARAM)), 0},
                {0, Math.sin(rotation.get(Constants.X_PARAM)), Math.cos(rotation.get(Constants.X_PARAM)), 0},
                {0, 0, 0, 1f}
        };
        return new Basic2DMatrix(matrix);
    }

    public static Matrix getYRotationMatrix(BasicVector rotation) {
        double[][] matrix = new double[][]{
                {Math.cos(rotation.get(Constants.Y_PARAM)), 0, Math.sin(rotation.get(Constants.Y_PARAM)), 0},
                {0, 1f, 0, 0},
                {-1 * Math.sin(rotation.get(Constants.Y_PARAM)), 0, Math.cos(rotation.get(Constants.Y_PARAM)), 0},
                {0, 0, 0, 1f}
        };
        return new Basic2DMatrix(matrix);
    }

    public static Matrix getZRotationMatrix(BasicVector rotation) {
        double[][] matrix = new double[][]{
                {Math.cos(rotation.get(Constants.Z_PARAM)), -1 * Math.sin(rotation.get(Constants.Z_PARAM)), 0, 0},
                {Math.sin(rotation.get(Constants.Z_PARAM)), Math.cos(rotation.get(Constants.Z_PARAM)), 0, 0},
                {0, 0, 1f, 0},
                {0, 0, 0, 1f}
        };
        return new Basic2DMatrix(matrix);
    }

    public static Matrix getOrthogonalProjection(double zNear, double zFar, double height, double width) {
        double[][] matrix = {
                {- 2 / width, 0, 0, 0},
                {0, - 2 / height, 0, 0},
                {0, 0, 1 / (zNear - zFar), zNear / (zNear - zFar)},
                {0, 0, 0, 1}
        };
        return new Basic2DMatrix(matrix);
    }

    public static Matrix getRelativeProjectionMatrixUsingFov(double aspect, double fov, double zNear, double zFar) {
        double[][] data = {
                {1 / (aspect * Math.tan(fov / 2)), 0, 0, 0},
                {0,  1 / Math.tan(fov / 2), 0, 0},
                {0, 0, zFar / (zNear - zFar), zNear * zFar / (zNear - zFar)},
                {0, 0, -1, 0}
        };
        return new Basic2DMatrix(data);
    }


    public static Matrix getViewportMatrix(double width, double height) {
        double[][] matrix = {
                {width / 2, 0, 0, width / 2},
                {0, -height / 2, 0, height / 2},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        return new Basic2DMatrix(matrix);
    }


    private static Vector normalize(Vector vector) {
        double norm = vector.norm();
        vector.set(Constants.X_PARAM, vector.get(Constants.X_PARAM) / norm);
        vector.set(Constants.Y_PARAM, vector.get(Constants.Y_PARAM) / norm);
        vector.set(Constants.Z_PARAM, vector.get(Constants.Z_PARAM) / norm);
        return vector;
    }

    public static Matrix getModelTranslationMatrix(BasicVector position, BasicVector scale, BasicVector rotation) {
        Matrix translationMatrix = getTranslationMatrix(position);
        Matrix scaleMatrix = getScaleMatrix(scale);
        Matrix xRotationMatrix = getXRotationMatrix(rotation);
        Matrix yRotationMatrix = getYRotationMatrix(rotation);
        Matrix zRotationMatrix = getZRotationMatrix(rotation);
        return translationMatrix.multiply(scaleMatrix).multiply(xRotationMatrix).multiply(yRotationMatrix).multiply(zRotationMatrix);
    }
}
