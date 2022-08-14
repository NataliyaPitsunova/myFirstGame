package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyFirstGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    int clck = 0;       //first hw

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("free-png.ru-442.png");   //first hw
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);


        float x = Gdx.input.getX() - img.getHeight() / 2;          //first hw
        float y = Gdx.graphics.getHeight() - Gdx.input.getY() - img.getHeight() / 2;        //first hw

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {        //first hw
            clck++;
        }

        Gdx.graphics.setTitle("clicked " + clck + " times");    //first hw


        batch.begin();
        batch.draw(img, x, y);     //first hw
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
