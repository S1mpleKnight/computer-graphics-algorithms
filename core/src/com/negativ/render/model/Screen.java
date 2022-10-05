package com.negativ.render.model;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.negativ.render.model.Point;
import com.negativ.render.utils.Constants;

public class Screen implements Disposable {

    private final SpriteBatch batch;
    private final int width;
    private final int height;
    private Texture nextFrame;
    private Pixmap pixmap;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        this.batch = new SpriteBatch();
        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        nextFrame = new Texture(pixmap);
    }

    public void render() {
        batch.begin();
        batch.draw(nextFrame, Constants.TEXTURE_START_X, Constants.TEXTURE_START_Y);
        batch.end();
        nextFrame.dispose();
        nextFrame = new Texture(pixmap);
        pixmap.dispose();
        pixmap = new Pixmap(width, height, Constants.PIXEL_REPRESENTATION_FORMAT);
    }

    @Override
    public void dispose() {
        batch.dispose();
        nextFrame.dispose();
        pixmap.dispose();
    }

    public void drawPixel(int x, int y, int color) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            pixmap.drawPixel(x, y, color);
        }
    }

    public void drawLine(int x1, int y1, int x2, int y2, int color) {
        int dx = Math.abs(x2 - x1);
        int sx = x1 < x2 ? 1 : -1;
        int dy = -Math.abs(y2 - y1);
        int sy = y1 < y2 ? 1 : -1;
        int err = dx + dy;
        for (; ; ) {
            drawPixel(x1, y1, color);
            if (x1 == x2 && y1 == y2) {
                return;
            }
            int err2 = err * 2;
            if (err2 > dy) {
                err += dy;
                x1 += sx;
            }
            if (err2 <= dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    public void drawPolygon(Point v0, Point v1, Point v2) {
        drawPolygon(v0, v1, v2, Constants.DEFAULT_COLOR);
    }

    public void drawPolygon(Point v0, Point v1, Point v2, int color) {
        drawLine(v0.getX(), v0.getY(), v1.getX(), v1.getY(), color);
        drawLine(v0.getX(), v0.getY(), v2.getX(), v2.getY(), color);
        drawLine(v2.getX(), v2.getY(), v1.getX(), v1.getY(), color);
    }

    public void drawPolygon(Point v0, Point v1, Point v2, Point v3, int color) {
        drawLine(v0.getX(), v0.getY(), v1.getX(), v1.getY(), color);
        drawLine(v1.getX(), v1.getY(), v2.getX(), v2.getY(), color);
        drawLine(v2.getX(), v2.getY(), v3.getX(), v3.getY(), color);
        drawLine(v3.getX(), v3.getY(), v0.getX(), v0.getY(), color);
    }

    public void drawPolygon(Point v0, Point v1, Point v2, Point v3) {
        drawPolygon(v0, v1, v2, v3, Constants.DEFAULT_COLOR);
    }
}
