package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.scene.GameScreen;
import com.mygdx.game.scene.MenuScreen;

public class Main extends Game {
    SpriteBatch batch;
    AnimaHero anm;
    float x;
    boolean dir = true;


    @Override
    public void create() {
        setScreen(new MenuScreen(this));
        batch = new SpriteBatch();
        anm = new AnimaHero("atlas.png", 1, 1, Animation.PlayMode.LOOP);
        x = 0;
    }

    @Override
    public void render() {
        super.render();
        if (getScreen() instanceof GameScreen) {
            anm.setTime(Gdx.graphics.getDeltaTime());
            if (x + 128 >= Gdx.graphics.getWidth()) {
                dir = false;
            }

            if (x == 0 && anm.getFrame().isFlipX()) {
                dir = true;
            }

            if (!anm.getFrame().isFlipX() && !(dir)) {
                anm.getFrame().flip(true, false);
            }

            if (anm.getFrame().isFlipX() && dir) {
                anm.getFrame().flip(true, false);
            }

            batch.begin();
            if (!anm.getFrame().isFlipX()) {
                batch.draw(anm.getFrame(), x++, 0);
            } else {
                batch.draw(anm.getFrame(), x--, 0);
            }
            batch.end();
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
        anm.dispose();
    }
}


