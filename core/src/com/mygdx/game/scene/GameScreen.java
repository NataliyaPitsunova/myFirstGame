package com.mygdx.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.AnimaHero;
import com.mygdx.game.Main;

public class GameScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture img;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRender;
    private float step = 2;
    private Rectangle mapSize;
    private AnimaHero animaHero;
    private boolean lookRight = true;
    private int x;


    public GameScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        animaHero = new AnimaHero("atlas.png", 1, 1, Animation.PlayMode.LOOP);
        ;
        img = new Texture("bg.png");
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.8f;
        TmxMapLoader tm = new TmxMapLoader();
        map = tm.load("map/map1.tmx");
        mapRender = new OrthogonalTiledMapRenderer(map);


        RectangleMapObject tmp = (RectangleMapObject) map.getLayers().get("obj").getObjects().get("cam");
        camera.position.x = tmp.getRectangle().x;
        camera.position.y = tmp.getRectangle().y;
        tmp = (RectangleMapObject) map.getLayers().get("obj").getObjects().get("border");
        mapSize = tmp.getRectangle();
        x = 0;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && mapSize.x < (camera.position.x - 1)) {
            camera.position.x -= step;
            lookRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mapSize.x + mapSize.width > (camera.position.x + 1)) {
            camera.position.x += step;
            lookRight = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            camera.zoom -= 0.01f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.O) && camera.zoom > 0) {
            camera.zoom += 0.01f;
        }


        camera.update();

        ScreenUtils.clear(Color.DARK_GRAY);


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();

        mapRender.setView(camera);
        mapRender.render();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }

        if (!animaHero.getFrame().isFlipX() && !(lookRight)) {
            animaHero.getFrame().flip(true, false);
        }

        if (animaHero.getFrame().isFlipX() && lookRight) {
            animaHero.getFrame().flip(true, false);
        }
        batch.begin();
        animaHero.setTime(Gdx.graphics.getDeltaTime());
        batch.draw(animaHero.getFrame(), Gdx.graphics.getWidth() / 2, 40);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
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
        this.batch.dispose();
        this.img.dispose();
        this.animaHero.dispose();
    }
}
