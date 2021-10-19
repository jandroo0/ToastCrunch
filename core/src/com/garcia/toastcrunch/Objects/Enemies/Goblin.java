package com.garcia.toastcrunch.Objects.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.garcia.toastcrunch.Screens.GameScreen;
import com.garcia.toastcrunch.Util.Assets;
import com.garcia.toastcrunch.Util.Constants;


public class Goblin extends Enemy {

    private float StateTime;

    private Assets.EnemyAsset_01 goblinAsset;

    public Goblin(GameScreen screen, float x, float y) {
        super(screen, x, y);
        walkPath = 4 / Constants.PPM;

        goblinAsset = Assets.instance.enemyAsset01;
        runningRight = true;
        velocity = new Vector2(.8f, -2);

        setBounds(getX(), getY(), 24 / Constants.PPM, 28 / Constants.PPM);

    }

    @Override
    public void update(float deltaTime) {
        setRegion(getFrame(deltaTime));
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - (getHeight() * 0.4f));
        move();
    }

    public TextureRegion getFrame(float deltaTime) {
        TextureRegion region;
        currentState = getState();

        switch (currentState) {
            case WALKING:
                region = goblinAsset.enemyWalk.getKeyFrame(stateTimer, true);
                break;
            case IDLING:
            default:
                region = goblinAsset.enemyIdle.getKeyFrame(stateTimer, true);
                break;
        }

//        if(b2body.getLinearVelocity().x < 0 || !runningRight) {
//            region.flip(true, false);
//        }

        if ((b2body.getLinearVelocity().x < 0 && !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 && runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;

        return region;
    }

    public State getState() {

        if (b2body.getLinearVelocity().x != 0) {
            return State.WALKING;
        } else {
            return State.IDLING;
        }
    }
}
