package com.garcia.toastcrunch.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.garcia.toastcrunch.Objects.Enemies.Enemy;
import com.garcia.toastcrunch.Screens.GameScreen;
import com.garcia.toastcrunch.Util.Assets;
import com.garcia.toastcrunch.Util.Constants;

public class Player extends Sprite {

    private GameScreen screen;
    private World world;
    private Body b2body;

    private State currentState, previousState;
    private float stateTimer;

    private float accel, jumpAccel, maxSpeed;

    private Assets.PlayerAsset playerAsset;

    private boolean runningRight;
    private int jumpCounter, maxJumps, attackCombo, attackCounter;
    private float timeSinceLastAttack, attackCooldown;

    public Player(GameScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;

        playerAsset = Assets.instance.playerAsset;

        currentState = State.IDLING;
        previousState = State.IDLING;
        stateTimer = 0;

        maxSpeed = 1.2f;
        accel = .7f;
        jumpAccel = 2f;

        runningRight = true;
        maxJumps = 2;
        jumpCounter = 1;
        attackCounter = 1;
        attackCombo = 3;
        timeSinceLastAttack = 0;
        attackCooldown = 2f;

        definePlayer(x, y);
        setBounds(0, 0, 48 / Constants.PPM, 36 / Constants.PPM);

    }

    public void update(float deltaTime) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 3);
        setRegion(getFrame(deltaTime));
        timeSinceLastAttack += deltaTime;
    }

    public void hit(Enemy enemy) {
        enemy.reverseVelocity(true, false);
    }

    public void attack(float deltaTime) {
        if(timeSinceLastAttack - attackCooldown >= 0) {
            b2body.setLinearVelocity(0, b2body.getLinearVelocity().y); // stop motion
            if (attackCounter >= attackCombo) {
                attackCounter = 1;
            } else {
                attackCounter++;
            }
        }
    }

    // player input
    public void handleInput(float deltaTime) {

        // attacking

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            attack(deltaTime);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W) && currentState != State.ATTACKING) { // jump
            if (jumpCounter < maxJumps) {
                if (currentState != State.FALLING) {
                    b2body.applyLinearImpulse(new Vector2(0, jumpAccel), b2body.getWorldCenter(), true);
                    currentState = State.JUMPING;
                    jumpCounter++;
                }
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && b2body.getLinearVelocity().x <= maxSpeed && currentState != State.ATTACKING) { // right
            b2body.applyLinearImpulse(new Vector2(accel, 0), b2body.getWorldCenter(), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && b2body.getLinearVelocity().x >= -maxSpeed && currentState != State.ATTACKING) { // left
            b2body.applyLinearImpulse(new Vector2(-accel, 0), b2body.getWorldCenter(), true);
        } else if(Gdx.input.isKeyPressed(Input.Keys.D) == Gdx.input.isKeyPressed(Input.Keys.A)) {
            b2body.setLinearVelocity(0, b2body.getLinearVelocity().y);
        }

    }

    public State getState() {

        if(timeSinceLastAttack - attackCooldown >= 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) return State.ATTACKING;
        }

        if ((b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING)) // if going up as well as falling after a jump
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0) {
            jumpCounter = 0;
            return State.WALKING;
        } else if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
            return State.DUCKING;
        else
            jumpCounter = 0;
        return State.IDLING;
    }

    private enum State {WALKING, DUCKING, FALLING, HANGING, CLIMBING, IDLING, HOLDING, HURT, STANDING, KICKING, ATTACKING, JUMPING}

    public TextureRegion getFrame(float deltaTime) {
        currentState = getState();

        TextureRegion region = playerAsset.playerIdle01.getKeyFrame(stateTimer, false);

        switch (currentState) {
            case JUMPING:
                switch(jumpCounter) {
                    case 1:
                        region = playerAsset.playerJump01.getKeyFrame(stateTimer, false);;
                        break;
                    case 2:
                        region = playerAsset.playerJump02.getKeyFrame(stateTimer, false);
                        break;
                }

                break;
            case FALLING:
                region = playerAsset.playerFall.getKeyFrame(stateTimer, false);
                break;
            case ATTACKING:
                switch(attackCounter) {
                    case 1:
                        region = playerAsset.playerAttack1.getKeyFrame(stateTimer, false);
                        break;
                    case 2:
                        region = playerAsset.playerAttack2.getKeyFrame(stateTimer, false);
                        break;
                    case 3:
                        region = playerAsset.playerAttack3.getKeyFrame(stateTimer, false);
                        break;
                }
                break;
            case WALKING:
                region = playerAsset.playerWalk.getKeyFrame(stateTimer, true);
                break;
            case DUCKING:
                region = playerAsset.playerDuck.getKeyFrame(stateTimer, false);
                break;
            case IDLING:
            default:
                region = playerAsset.playerIdle01.getKeyFrame(stateTimer, true);
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;

        return region;
    }

    public void definePlayer(float x, float y) {
        BodyDef bdef = new BodyDef();

        bdef.position.set(x / Constants.PPM, y / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(9 / Constants.PPM);

        fdef.filter.categoryBits = Constants.PLAYER_BIT; // collision bit
        fdef.filter.maskBits = Constants.GROUND_BIT | Constants.BORDER_BIT | Constants.ENEMY_BIT | Constants.CHEST_BIT; // compatible collision bits

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();
    }


}
