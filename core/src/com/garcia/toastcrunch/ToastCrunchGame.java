package com.garcia.toastcrunch;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.garcia.toastcrunch.Screens.GameScreen;
import com.garcia.toastcrunch.Util.Assets;
import com.garcia.toastcrunch.Util.Assets;

public class ToastCrunchGame extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {

        batch = new SpriteBatch(); // sprite batch


        Assets.instance.init(new AssetManager()); // asset manager

        setScreen(new GameScreen(this));

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
