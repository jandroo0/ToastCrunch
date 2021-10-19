package com.garcia.toastcrunch.Objects.Enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.garcia.toastcrunch.Screens.GameScreen;
import com.garcia.toastcrunch.Util.Constants;

public abstract class Enemy extends Sprite {

    public Body b2body;
    public Vector2 velocity;
    protected State currentState, previousState;
    protected GameScreen screen;
    protected World world;
    protected boolean runningRight;
    protected float walkPath;
    float stateTimer;

    private float x, y;
    private boolean chasing;
    public Enemy(GameScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;

        setPosition(x, y);
        defineEnemy();
    }

    public abstract void update(float deltaTime);

    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void move() {



    }

    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Constants.PPM);

        fdef.filter.categoryBits = Constants.ENEMY_BIT; // collision bit
        fdef.filter.maskBits = Constants.GROUND_BIT |
                Constants.BORDER_BIT ; // compatible collision bits

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        runningRight = !runningRight;
        if (y)
            velocity.y = -velocity.y;
    }

    protected enum State {WALKING, IDLING, HURT, STANDING, ATTACKING,}

}
