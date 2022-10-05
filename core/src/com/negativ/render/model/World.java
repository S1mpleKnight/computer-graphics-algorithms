package com.negativ.render.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.negativ.render.model.camera.Observable;
import com.negativ.render.utils.MatrixUtils;
import org.la4j.Matrix;
import org.la4j.vector.dense.BasicVector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.negativ.render.utils.Constants.*;

public class World {

    private final static Double MODEL_SCALE_COEFFICIENT = 1.05;
    private final int width;
    private final int height;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Screen screen;
    private final List<Model> models;
    private final Double scale = REL_SCALE;
    private final Double MODEL_SPEED = 5 * scale;
    private final Double CAMERA_SPEED = 5 * scale;
    private final Double CAMERA_ROTATION_SPEED = 0.5 * scale;
    private final Double MODEL_ROTATION_SPEED = scale * 0.75;
    private Integer activeModelId = 0;
    private Model activeModel;
    private Observable activeObservable;

    public World(int width, int height, Screen screen) {
        this.width = width;
        this.height = height;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.screen = screen;
        this.models = new ArrayList<>();
    }

    public void setActiveModel(Model activeModel) {
        this.activeModel = activeModel;
    }

    public void addModel(Model model) {
        models.add(model);
    }

    public void setActiveCamera(Observable activeObservable) {
        this.activeObservable = activeObservable;
    }

    public void render() {
        handleInput();
        models.forEach(this::renderModel);
        screen.render();
        printState();
    }

    private void renderModel(Model model) {
        List<Point> vertexes = calculateModelVertexes(model)
                .stream()
                .map(vector -> new Point((int) vector.get(X_PARAM), (int) vector.get(Y_PARAM), (int) vector.get(Z_PARAM)))
                .collect(Collectors.toList());
        model.getData().getPolygons().forEach(p -> {
            if (p.getVertexesIds().size() == 3) {
                screen.drawPolygon(vertexes.get(p.getVertexesIds().get(0) - 1),
                        vertexes.get(p.getVertexesIds().get(1) - 1), vertexes.get(p.getVertexesIds().get(2) - 1));

            } else if (p.getVertexesIds().size() == 4) {
                screen.drawPolygon(vertexes.get(p.getVertexesIds().get(0) - 1),
                        vertexes.get(p.getVertexesIds().get(1) - 1), vertexes.get(p.getVertexesIds().get(2) - 1),
                        vertexes.get(p.getVertexesIds().get(3) - 1));

            }
        });
        vertexes.clear();
    }

    private List<BasicVector> calculateModelVertexes(Model model) {
        Matrix worldMatrix = MatrixUtils.getModelTranslationMatrix(model.getPosition(), model.getScale(), model.getRotation());
        Matrix viewMatrix = activeObservable.getViewMatrix();
        Matrix projectionMatrix = MatrixUtils.getRelativeProjectionMatrixUsingFov((float)width/height, FOV, Z_NEAR, Z_FAR);
        Matrix viewportMatrix = MatrixUtils.getViewportMatrix(width, height);

        List<BasicVector> vertexes = new ArrayList<>((int) (model.getData().getVertexes().size() * 1.5 + 1));

        model.getData().getVertexes().forEach(vertex -> vertexes.add(vertex.multiply(worldMatrix).multiply(viewMatrix)
                .multiply(projectionMatrix).multiply(viewportMatrix).toThreeDimensionalVector()));

        return vertexes;
    }

    private void nextModel() {
        if (activeModelId == models.size() - 1) {
            activeModelId = 0;
        } else {
            activeModelId++;
        }
        activeModel = models.get(activeModelId);
    }

    private void handleInput() {
        handleTransitionInput();
        handleRotationInput();
        handleScaleInput();
        handleCameraInput();
        changeModel();
    }

    private void changeModel() {
        if (Gdx.input.isKeyPressed(Input.Keys.TAB)) {
            nextModel();
        }
    }

    private void handleCameraInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            activeObservable.move(new BasicVector(new double[] {CAMERA_SPEED, 0, 0, 0}));
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            activeObservable.move(new BasicVector(new double[] {-CAMERA_SPEED, 0, 0, 0}));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            activeObservable.move(new BasicVector(new double[] {0, CAMERA_SPEED, 0, 0}));
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            activeObservable.move(new BasicVector(new double[] {0, -CAMERA_SPEED, 0, 0}));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
            activeObservable.move(new BasicVector(new double[] {0, 0, CAMERA_SPEED, 0}));
        } else if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            activeObservable.move(new BasicVector(new double[] {0, 0, -CAMERA_SPEED, 0}));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.B)) {
            activeObservable.rotate(new BasicVector(new double[] {CAMERA_ROTATION_SPEED, 0, 0}));
        } else if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            activeObservable.rotate(new BasicVector(new double[] {-CAMERA_ROTATION_SPEED, 0, 0}));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            activeObservable.rotate(new BasicVector(new double[] {0, CAMERA_ROTATION_SPEED, 0}));
        } else if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            activeObservable.rotate(new BasicVector(new double[] {0, -CAMERA_ROTATION_SPEED, 0}));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.T)) {
            activeObservable.rotate(new BasicVector(new double[] {0, 0, CAMERA_ROTATION_SPEED}));
        } else if (Gdx.input.isKeyPressed(Input.Keys.U)) {
            activeObservable.rotate(new BasicVector(new double[] {0, 0, -CAMERA_ROTATION_SPEED}));
        }
    }

    private void handleScaleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            activeModel.scale(new BasicVector(new double[]{MODEL_SCALE_COEFFICIENT, MODEL_SCALE_COEFFICIENT,
                    MODEL_SCALE_COEFFICIENT}));
        } else if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            activeModel.scale(new BasicVector(new double[]{1 / MODEL_SCALE_COEFFICIENT, 1 / MODEL_SCALE_COEFFICIENT,
                    1 / MODEL_SCALE_COEFFICIENT}));
        }
    }

    private void handleRotationInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            activeModel.rotate(new BasicVector(new double[]{0, MODEL_ROTATION_SPEED, 0}));
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            activeModel.rotate(new BasicVector(new double[]{0, -MODEL_ROTATION_SPEED, 0}));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            activeModel.rotate(new BasicVector(new double[]{MODEL_ROTATION_SPEED, 0, 0}));
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
            activeModel.rotate(new BasicVector(new double[]{-MODEL_ROTATION_SPEED, 0, 0}));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
            activeModel.rotate(new BasicVector(new double[]{0, 0, MODEL_ROTATION_SPEED}));
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
            activeModel.rotate(new BasicVector(new double[]{0, 0, -MODEL_ROTATION_SPEED}));
        }
    }

    private void handleTransitionInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            activeModel.move(new BasicVector(new double[]{0, -MODEL_SPEED, 0}));
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            activeModel.move(new BasicVector(new double[]{0, MODEL_SPEED, 0}));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            activeModel.move(new BasicVector(new double[]{MODEL_SPEED, 0, 0}));
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            activeModel.move(new BasicVector(new double[]{-MODEL_SPEED, 0, 0}));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            activeModel.move(new BasicVector(new double[]{0, 0, MODEL_SPEED}));
        } else if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            activeModel.move(new BasicVector(new double[]{0, 0, -MODEL_SPEED}));
        }
    }

    private void printState() {
        double frameRate = Gdx.graphics.getFramesPerSecond();
        batch.begin();
        font.draw(batch, "ACTIVE CAMERA:\n" + activeObservable.getState(), 0, 930);
        font.draw(batch, String.format("FPS: %7f", frameRate), 0, 960);
        font.draw(batch, "ACTIVE MODEL:\n" + activeModel.getStateString(), 0, 785);
        batch.end();
    }
}
