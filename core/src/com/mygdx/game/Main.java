package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.scene.GameScreen;
import com.mygdx.game.scene.MenuScreen;

public class Main extends Game {
    SpriteBatch batch;


    @Override
    public void create() {
        setScreen(new MenuScreen(this));
        batch = new SpriteBatch();
    }


    @Override
    public void render() {
        super.render();
        if (getScreen() instanceof GameScreen) {
        }
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}


