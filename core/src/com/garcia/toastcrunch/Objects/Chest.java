package com.garcia.toastcrunch.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.garcia.toastcrunch.Screens.GameScreen;
import com.garcia.toastcrunch.Util.Assets;
import com.garcia.toastcrunch.Util.Constants;

public class Chest extends Item {

    boolean open;
    private float stateTimer;
    private State currentState, previousState;

    private Assets.ChestAsset chestAsset;

    public Chest(GameScreen screen, float x, float y) {
        super(screen, x, y);

        chestAsset = Assets.instance.chestAsset;

        stateTimer = 0;

//        currentState = State.CLOSED;
//        previousState = State.CLOSED;

    }

    public void update(float deltaTime) {
        setFrame(deltaTime);
    }

    public void setFrame(float deltaTime) {
        currentState = getState();


        switch (currentState) {
            case OPEN:
                setRegion(chestAsset.opened);
                break;
            case JUSTOPENED:
                setRegion(chestAsset.chestOpenAnimation.getKeyFrame(stateTimer, true));
            case CLOSED:
            default:
                setRegion(chestAsset.closed);
                break;
        }

        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        stateTimer += deltaTime;
        previousState = currentState;

    }


    public State getState() {
//        if()

        return State.JUSTOPENED;
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((float) (getX() + getWidth() / 2), (float) (getY() + getHeight() / 2));

        body = world.createBody(bdef);

        shape.setAsBox((float) (getWidth() / 2), (float) (getHeight() / 2));
        fdef.shape = shape;
        fdef.filter.categoryBits = Constants.CHEST_BIT;
        fdef.filter.maskBits = Constants.ENEMY_BIT |
                Constants.PLAYER_BIT; // compatible collision bits
        body.createFixture(fdef).setUserData(this);
    }

    private enum State {OPEN, CLOSED, JUSTOPENED}

}
