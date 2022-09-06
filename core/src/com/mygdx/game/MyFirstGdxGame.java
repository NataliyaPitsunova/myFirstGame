package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyFirstGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    AnimaHero anm;
    float x;
    boolean dir = true;

    @Override
    public void create() {
        batch = new SpriteBatch();
        anm = new AnimaHero("atlas", "idle", Animation.PlayMode.LOOP);
        x = anm.getFrame().getRegionX();
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);

        anm.setTime(Gdx.graphics.getDeltaTime());
/*
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dir = false;
            if (x > 0) {
                x--;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dir = true;
            if (x < (Gdx.graphics.getWidth() - anm.getFrame().getRegionWidth())) {
                x++;
            }
        }

        if (!anm.getFrame().isFlipX() && !(dir)) {
            anm.getFrame().flip(true,false);
        }

        if (anm.getFrame().isFlipX() && dir) {
            anm.getFrame().flip(true,false);
        }
        batch.begin();
        batch.draw(anm.getFrame(), x, 0);
        batch.end();
        */
//дз перемещение героя без клавиш
        if (x == (Gdx.graphics.getWidth() - anm.getFrame().getRegionWidth())) {
            dir = false;
        }

        if (x == 0 && anm.getFrame().isFlipX()) {
            dir = true;
        }

        if (!anm.getFrame().isFlipX() && !(dir)) {
            anm.getFrame().flip(true,false);
        }

        if (anm.getFrame().isFlipX() && dir) {
            anm.getFrame().flip(true,false);
        }

        batch.begin();
        if (!anm.getFrame().isFlipX()) {
            batch.draw(anm.getFrame(), x++, 0);
        } else{
            batch.draw(anm.getFrame(), x--, 0);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        anm.dispose();
    }
}
