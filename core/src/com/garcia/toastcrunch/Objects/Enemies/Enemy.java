package com.garcia.toastcrunch.Objects.Enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.garcia.toastcrunch.Screens.GameScreen;
import com.garcia.toastcrunch.Util.Constants;

public abstract class Enemy extends Sprite {

    protected enum State {WALKING,  IDLING,  HURT, STANDING,  ATTACKING,}
    protected State currentState, previousState;

    public Body b2body;
    public Vector2 velocity;
    protected GameScreen screen;
    protected World world;
    protected boolean runningRight;
    float stateTimer;

    private float x,y;
    protected float walkPath;
    private boolean chasing;


    public Enemy(GameScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;

        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(.8f, -2);
    }

    public abstract void update(float deltaTime);

    public abstract void defineEnemy();

    public void draw(Batch batch) {
        super.draw(batch); }

    public void move() {

//        if(Math.abs(getX() - screen.getPlayer().getX()) <= screen.mapWidth / 2 + 2) {
//            if(velocity.x < 0 && this.getX() < this.x - walkPath) {
//                reverseVelocity(true, false);
//            } else if(velocity.x > 0 && this.getX() > this.x + walkPath) {
//                reverseVelocity(true, false);
//            }
//        }

    }

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        runningRight = !runningRight;
        if(y)
            velocity.y = -velocity.y;
    }

}
