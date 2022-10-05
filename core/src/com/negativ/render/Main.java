package com.negativ.render;

import com.badlogic.gdx.ApplicationAdapter;
import com.negativ.render.model.Screen;
import com.negativ.render.model.World;
import com.negativ.render.model.camera.Camera;
import com.negativ.render.model.Model;
import com.negativ.render.utils.Constants;
import com.negativ.render.utils.Loader;
import org.la4j.vector.dense.BasicVector;

import java.io.FileNotFoundException;

public class Main extends ApplicationAdapter {

    private static Screen screen;
    private static World world;

    @Override
    public void create() {
        Loader loader = Loader.INSTANCE;
        screen = new Screen(Constants.WIDTH, Constants.HEIGHT);
        world = new World(Constants.WIDTH, Constants.HEIGHT, screen);
        world.setActiveCamera(new Camera());
        try {
            Model model = new Model(loader.loadModel(Constants.RYBA_OBJ_FILE_NAME), new BasicVector(new double[] {1, 0, 0}), "Ryba");
            world.addModel(model);
            world.setActiveModel(model);
            model = new Model(loader.loadModel(Constants.SMILE_OBJ_FILE_NAME), new BasicVector(new double[] {1, 0, 0}), "Smile");
            world.addModel(model);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void render() {
        world.render();
    }

    @Override
    public void dispose() {
        screen.dispose();
    }
}
