package com.garcia.toastcrunch.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    //    public AssetFonts fonts;
//    public AssetSounds sounds;
//    public AssetMusic music;
    public PlayerAsset playerAsset;
    public EnemyAsset_01 enemyAsset01;
    public EnemyAsset_02 enemyAsset02;
    public ChestAsset chestAsset;
    private AssetManager assetManager;

    // singleton: prevent instantiation from other classes
    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        // set asset manager error handler
        assetManager.setErrorListener(this);


        // player assets
        assetManager.load("sprites/player/Knight/attack.png", Texture.class);
        assetManager.load("sprites/player/Knight/death.png", Texture.class);
        assetManager.load("sprites/player/Knight/idle.png", Texture.class);
        assetManager.load("sprites/player/Knight/jumpAndfall.png", Texture.class);
        assetManager.load("sprites/player/Knight/rolling.png", Texture.class);
        assetManager.load("sprites/player/Knight/run.png", Texture.class);
        assetManager.load("sprites/player/Knight/useShield.png", Texture.class);

        assetManager.load("sprites/player/Bandit/Bandit.png", Texture.class); // bandit spritesheet

        assetManager.load("sprites/player/Adventurer/spritesheet.png", Texture.class); // adventurer spritesheet


        // enemies
        assetManager.load("sprites/enemies/goblin_spritesheet.png", Texture.class); // goblin spritesheet
        assetManager.load("sprites/enemies/worm/worm_spritesheet.png", Texture.class); // worm spritesheet

        // objects
        assetManager.load("tilemap/tilesets/chest_tileset.png", Texture.class); // chest spritesheet

        // start loading assets and wait until finished
        assetManager.finishLoading();

        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "asset: " + a);
        }

//        TextureAtlas atlas = assetManager.get("sprites/texture_atlas.atlas");
//        // enable texture filtering for pixel smoothing
//        for (Texture t : atlas.getTextures()) {
//            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        }

        // create game resource objects
//        sounds = new AssetSounds(assetManager);
//        music = new AssetMusic(assetManager);
        playerAsset = new PlayerAsset(assetManager);
        enemyAsset01 = new EnemyAsset_01(assetManager);
        enemyAsset02 = new EnemyAsset_02(assetManager);
        chestAsset = new ChestAsset(assetManager);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", throwable);
    }

    public class ChestAsset {
        public final Texture texture;

        public final Animation<TextureRegion> chestOpenAnimation;

        public final TextureRegion closed;
        public final TextureRegion opened;

        public ChestAsset(AssetManager manager) {
            texture = assetManager.get("tilemap/tilesets/chest_tileset.png");
            Array<TextureRegion> frames = new Array<>();

            // chested opened animation
            for (int i = 0; i < 3; i++)
                frames.add(new TextureRegion(texture, i * 16, 32, 16, 16));
            chestOpenAnimation = new Animation<TextureRegion>(.1f, frames);
            frames.clear();

            // chest closed
            closed = new TextureRegion(texture, 0, 32, 16, 16);

            // chest open
            opened = new TextureRegion(texture, 32, 32, 16, 16);


        }
    }

    public class EnemyAsset_01 {
        public final Texture texture;

        //
        public final Animation<TextureRegion> enemyIdle;
        public final Animation<TextureRegion> enemyWalk;
//        public final Animation<TextureRegion> playerAttack;

        public EnemyAsset_01(AssetManager manager) {
            texture = assetManager.get("sprites/enemies/goblin_spritesheet.png");
            Array<TextureRegion> frames = new Array<>();

            // idle animation
            for (int i = 0; i < 4; i++)
                frames.add(new TextureRegion(texture, i * 16, 48, 16, 16));
            enemyIdle = new Animation<TextureRegion>(.15f, frames);
            frames.clear();

            // walking animation
            for (int i = 0; i < 6; i++)
                frames.add(new TextureRegion(texture, i * 16, 0, 16, 16));
            enemyWalk = new Animation<TextureRegion>(0.1f, frames);
            frames.clear();

        }
    }

    public class EnemyAsset_02 {
        public final Texture texture;

        //
        public final Animation<TextureRegion> enemyIdle;
        public final Animation<TextureRegion> enemyHit;
        public final Animation<TextureRegion> attackLeft;
        public final Animation<TextureRegion> attackRight;

        public EnemyAsset_02(AssetManager manager) {
            texture = assetManager.get("sprites/enemies/worm/worm_spritesheet.png");
            Array<TextureRegion> frames = new Array<>();

            // idle animation
            for (int i = 0; i < 8; i++)
                frames.add(new TextureRegion(texture, 1216 + (i * 64), 0, 64, 64));
            enemyIdle = new Animation<TextureRegion>(.1f, frames);
            frames.clear();

            // attack left animation
            for (int i = 0; i < 8; i++)
                frames.add(new TextureRegion(texture, i * 64, 0, 64, 64));
            attackLeft = new Animation<TextureRegion>(0.1f, frames);
            frames.clear();

            // attack right animation
            for (int i = 0; i < 8; i++)
                frames.add(new TextureRegion(texture, 512 + (i * 64), 0, 64, 64));
            attackRight = new Animation<TextureRegion>(0.1f, frames);
            frames.clear();

            // hit animation
            for (int i = 0; i < 3; i++)
                frames.add(new TextureRegion(texture, 1024 + (i * 64), 0, 64, 64));
            enemyHit = new Animation<TextureRegion>(0.1f, frames);
            frames.clear();

        }
    }


    public class PlayerAsset {
        public final Texture texture; // sprite texture

        public final Animation<TextureRegion> playerIdle01;
        public final Animation<TextureRegion> playerWalk;
        public final Animation<TextureRegion> playerJump01;
        public final Animation<TextureRegion> playerJump02;
        public final Animation<TextureRegion> playerFall;
        public final Animation<TextureRegion> playerAttack1;
        public final Animation<TextureRegion> playerAttack2;
        public final Animation<TextureRegion> playerAttack3;
        public final Animation<TextureRegion> playerDuck;

        public PlayerAsset(AssetManager manager) {
            texture = assetManager.get("sprites/player/Adventurer/spritesheet.png");
            Array<TextureRegion> frames = new Array<>();

            // idle animation
            for (int i = 0; i < 4; i++)
                frames.add(new TextureRegion(texture, i * 50, 0, 50, 37));
            playerIdle01 = new Animation<TextureRegion>(.15f, frames);
            frames.clear();

            // walking animation
            for (int i = 0; i < 6; i++)
                frames.add(new TextureRegion(texture, 50 + ( i * 50), 37, 50, 37));
            playerWalk = new Animation<TextureRegion>(.09f, frames);
            frames.clear();

            // jumping animation
            for (int i = 0; i < 4; i++)
                frames.add(new TextureRegion(texture, i * 50, 74, 50, 37));
            playerJump01 = new Animation<TextureRegion>(.05f, frames);
            frames.clear();

            // jump 02
            for (int i = 0; i < 3; i++)
                frames.add(new TextureRegion(texture, 150 + (i * 50), 74, 50, 37));
            frames.add(new TextureRegion(texture, 0, 111, 50, 37));
            playerJump02 = new Animation<TextureRegion>(.08f, frames);
            frames.clear();

            // falling animation
            for (int i = 0; i < 2; i++)
                frames.add(new TextureRegion(texture, 50 + (i * 50), 111, 50, 37));
            playerFall = new Animation<TextureRegion>(.1f, frames);
            frames.clear();


            // attack animation 1
            for (int i = 0; i < 5; i++)
                frames.add(new TextureRegion(texture, i * 50, 222, 50, 37));
            playerAttack2 = new Animation<TextureRegion>(0.1f, frames);
            frames.clear();

            // attack animation 2
            for (int i = 0; i < 2; i++)
                frames.add(new TextureRegion(texture, 250 + (i * 50), 222, 50, 37));
            for (int i = 0; i < 4; i++)
                frames.add(new TextureRegion(texture, i * 50, 259, 50, 37));
            playerAttack1 = new Animation<TextureRegion>(0.1f, frames);
            frames.clear();

            // attack animation 3
            frames.add(new TextureRegion(texture,  300, 259, 50, 37));
            for (int i = 0; i < 3; i++)
                frames.add(new TextureRegion(texture, i * 50, 296, 50, 37));
            playerAttack3 = new Animation<TextureRegion>(0.1f, frames);
            frames.clear();

            // duck animation
            for (int i = 0; i < 3; i++)
                frames.add(new TextureRegion(texture, 200+(i * 50), 0, 50, 37));
            frames.add(new TextureRegion(texture, 0, 74, 50, 37));
            playerDuck = new Animation<TextureRegion>(0.1f, frames);
            frames.clear();


        }
    }
}


// knight
//            attackTexture = assetManager.get("sprites/player/Knight/attack.png");
////            texture = assetManager.get("sprites/player/Knight/death.png");
//            idleTexture = assetManager.get("sprites/player/Knight/idle.png");
//            jumpAndFallTexture = assetManager.get("sprites/player/Knight/jumpAndfall.png");
////            texture = assetManager.get("sprites/player/Knight/rolling.png");
//            runTexture = assetManager.get("sprites/player/Knight/run.png");
//            texture = assetManager.get("sprites/player/Knight/useShield.png");

////        idle animation
//            for (int i = 0; i < 15; i++)
//        frames.add(new TextureRegion(idleTexture, i * 64, 0, 64, 64));
//        playerIdle = new Animation<TextureRegion>(.08f, frames);
//        frames.clear();
//
//        // walking animation
//        for (int i = 0; i < 8; i++)
//        frames.add(new TextureRegion(runTexture, i * 96, 0, 96, 64));
//        playerWalk = new Animation<TextureRegion>(.05f, frames);
//        frames.clear();
//
//        // jumping animation
//        for (int i = 0; i < 7; i++)
//        frames.add(new TextureRegion(jumpAndFallTexture, i * 96, 0, 96, 64));
//        playerJump = new Animation<TextureRegion>(0.005f, frames);
//        frames.clear();
//
//        // falling animation
//        for (int i = 0; i < 7; i++)
//        frames.add(new TextureRegion(jumpAndFallTexture, 1080 + (i * 96), 0, 96, 64));
//        playerFall = new Animation<TextureRegion>(0.01f, frames);
//        frames.clear();
//
//        // attack animation 1
//        for (int i = 0; i < 10; i++)
//        frames.add(new TextureRegion(attackTexture, i * 96, 0, 96, 64));
//        playerAttack1 = new Animation<TextureRegion>(0.005f, frames);
//        frames.clear();
//
//        // attack animation 2
//        for (int i = 0; i < 6; i++)
//        frames.add(new TextureRegion(attackTexture, 960 + (i * 96), 0, 96, 64));
//        playerAttack2 = new Animation<TextureRegion>(0.01f, frames);
//        frames.clear();


//    // fonts
//    public class AssetFonts {
//        public final BitmapFont titleFont;
//        public final BitmapFont hudFont;
//        public final BitmapFont buttonFont;
//        public final BitmapFont statFont;
//        public final BitmapFont miniFont;
//
//        public AssetFonts() {
//            //  default font parameters ------
//            FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/GalaxyFont.otf"));
//            FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
//
//            fontParameters.size = 72;
//            fontParameters.borderWidth = 3.6f;
//            fontParameters.color = new Color(1, 1, 1, 0.3f);
//            fontParameters.borderColor = new Color(0, 0, 0, 0.3f);
//            // ------
//
//            // font settings ------
//            titleFont = fontGenerator.generateFont(fontParameters);// generate HUD font with paramater
//            hudFont = fontGenerator.generateFont(fontParameters);
//            buttonFont = fontGenerator.generateFont(fontParameters);
//            statFont = fontGenerator.generateFont(fontParameters);
//            miniFont = fontGenerator.generateFont(fontParameters);
//
//            titleFont.getData().setScale(0.11f);
//            hudFont.getData().setScale(0.08f);
//            buttonFont.getData().setScale(0.09f);
//            statFont.getData().setScale(0.07f);
//            miniFont.getData().setScale(0.03f);
//
//            titleFont.setUseIntegerPositions(false);
//            titleFont.getData().markupEnabled = true; // html markup
//            titleFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//
//            hudFont.setUseIntegerPositions(false);
//            hudFont.getData().markupEnabled = true; // html markup
//            hudFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//
//            buttonFont.setUseIntegerPositions(false);
//            buttonFont.getData().markupEnabled = true; // html markup
//            buttonFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//
//            statFont.setUseIntegerPositions(false);
//            statFont.getData().markupEnabled = true; // html markup
//            statFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//
//            miniFont.setUseIntegerPositions(false);
//            miniFont.getData().markupEnabled = true; // html markup
//            miniFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//
//            // -------------------------------
//        }
//    }


































