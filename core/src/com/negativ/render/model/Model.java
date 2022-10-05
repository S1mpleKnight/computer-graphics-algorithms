package com.negativ.render.model;

import com.negativ.render.utils.Constants;
import lombok.Getter;
import org.la4j.vector.dense.BasicVector;

@Getter
public class Model {

    private final BasicVector scale;
    private final ModelData data;
    private final String name;
    private final BasicVector defaultScale = new BasicVector(new double[]{1, 1, 1});
    private final BasicVector defaultRotation = new BasicVector(new double[]{0, 0, 0});
    private BasicVector defaultPosition = new BasicVector(new double[]{0, 0, 0});
    private BasicVector position;
    private BasicVector rotation;

    public Model(ModelData data, BasicVector position, String name) {
        this.defaultPosition = position;
        this.position = defaultPosition;
        this.scale = defaultScale;
        this.rotation = defaultRotation;
        this.data = data;
        this.name = name;
    }

    public String getStateString() {
        return "Name: " + name + "\n" +
                "Position: " + position + "\n" +
                "Scale: " + scale + "\n" +
                "Rotation:" + rotation;
    }

    public void move(BasicVector movement) {
        position = (BasicVector) position.add(movement);
    }

    public void scale(BasicVector amount) {
        scale.set(Constants.X_PARAM, scale.get(Constants.X_PARAM) * amount.get(Constants.X_PARAM));
        scale.set(Constants.Y_PARAM, scale.get(Constants.Y_PARAM) * amount.get(Constants.Y_PARAM));
        scale.set(Constants.Z_PARAM, scale.get(Constants.Z_PARAM) * amount.get(Constants.Z_PARAM));
    }

    public void rotate(BasicVector amount) {
        rotation = (BasicVector) rotation.add(amount);
    }
}
