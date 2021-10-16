package com.garcia.toastcrunch.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.garcia.toastcrunch.Screens.GameScreen;
import com.garcia.toastcrunch.Util.Constants;

public abstract class Item extends Sprite {

    protected GameScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected Body body;

    public Item(GameScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();

        setPosition(x, y);
        setBounds(getX(), getY(), 16 / Constants.PPM, 16 / Constants.PPM);
        defineItem();
    }

    public abstract void defineItem();
    public abstract void update(float deltaTime);

    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void destroy() {

    }
}
