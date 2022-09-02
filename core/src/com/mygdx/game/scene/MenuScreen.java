package com.mygdx.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;


public class MenuScreen implements Screen {
    private final Main game;
    private final SpriteBatch spriteBatch;
    private final Texture img;
    private final Circle circle;
    private final ShapeRenderer shapeRenderer;
/*    private final Music music;
    private final Sound sound;*/

    public MenuScreen(Main game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        img = new Texture("Main.png");
        circle = new Circle(460, 100, 60);
        shapeRenderer = new ShapeRenderer();}
       /* music = Gdx.audio.newMusic(Gdx.files.internal(""));
        music.setLooping(true);
        music.setVolume(0.05f);
        music.play();

        sound = Gdx.audio.newSound(Gdx.files.internal(""));*/




    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);

        spriteBatch.begin();
        spriteBatch.draw(img, 0, 0);
        spriteBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.circle(circle.x, circle.y, circle.radius);
        shapeRenderer.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (circle.contains(x, y)) {
                dispose();
                game.setScreen(new GameScreen(game));
            } else {
                //  sound.play();
            }
        }


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.spriteBatch.dispose();
        this.img.dispose();
        this.shapeRenderer.dispose();
       // this.music.dispose();
    }

}
