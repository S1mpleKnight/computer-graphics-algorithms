package com.negativ.render.utils;

import com.badlogic.gdx.graphics.Pixmap;

public interface Constants {
    String RYBA_OBJ_FILE_NAME = "Ryba.obj";
    String SMILE_OBJ_FILE_NAME = "smile.obj";

    Integer WIDTH = 1920;
    Integer HEIGHT = 1080;

    Integer DEFAULT_COLOR = 0xFFFFFFFF;

    Integer TEXTURE_START_X = 0;
    Integer TEXTURE_START_Y = 0;

    Pixmap.Format PIXEL_REPRESENTATION_FORMAT = Pixmap.Format.RGB888;

    Double FOV = (Math.PI / 4);
    Double REL_SCALE = 0.01;
    Double REL_ROTATION_SCALE = 0.01;
    Double ORTHOGONAL_SCALE = -1.0;
    Double ORTHOGONAL_ROTATION_SCALE = -0.01;

    Float Z_NEAR = 0.4f;
    Float Z_FAR = 100f;

    Integer X_PARAM = 0;
    Integer Y_PARAM = 1;
    Integer Z_PARAM = 2;
    Integer MATRIX_DIMENSION = 4;
}
