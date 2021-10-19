package com.garcia.toastcrunch.Objects.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.garcia.toastcrunch.Screens.GameScreen;
import com.garcia.toastcrunch.Util.Assets;
import com.garcia.toastcrunch.Util.Constants;


public class Worm extends Enemy {

    private float StateTime;

    private Assets.EnemyAsset_02 wormAsset;

    public Worm(GameScreen screen, float x, float y) {
        super(screen, x, y);

        wormAsset = Assets.instance.enemyAsset02;
        runningRight = true;

        setBounds(getX(), getY(), 48 / Constants.PPM, 48 / Constants.PPM);

    }

    @Override
    public void update(float deltaTime) {
        setRegion(getFrame(deltaTime));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - (getHeight() * 0.15f));
        move();
    }

    public TextureRegion getFrame(float deltaTime) {
        TextureRegion region;
        currentState = getState();

        switch (currentState) {
            case IDLING:
            default:
                region = wormAsset.enemyIdle.getKeyFrame(stateTimer, true);
                break;
        }


        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;

        return region;
    }

    public State getState() {
        return State.IDLING;

    }
}
