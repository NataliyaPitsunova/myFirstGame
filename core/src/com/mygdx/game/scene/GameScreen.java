package com.mygdx.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.KeyboardController;
import com.mygdx.game.Main;
import com.mygdx.game.PhisBodies;
import com.mygdx.game.PhisX;
import com.mygdx.game.persons.AnimaHero;
import com.mygdx.game.persons.Coins;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture img;
    private AnimaHero hero;
    private KeyboardController controller;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRender;
    private PhisX phisX;
    private boolean lookRight = false;
    private final int[] bg;
    private final int[] l1;
    private Body body;
  //  private final Rectangle heroRect;
    public static ArrayList<Body> bodies;
    private Sound sound;
    private Music music;
    private Coins coins;


    public GameScreen(Main game) {
        this.game = game;
        controller = new KeyboardController();
        Gdx.input.setInputProcessor(controller);

        music = Gdx.audio.newMusic(Gdx.files.internal("game.mp3"));
        sound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        music.play();
        long soundId = sound.play();
        sound.setVolume(soundId, 0.5f);

        bodies = new ArrayList<>();
        batch = new SpriteBatch();
        img = new Texture("bg.png");

        hero = new AnimaHero();
        coins = new Coins();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.35f;

        TiledMap map = new TmxMapLoader().load("map/map2.tmx");
        mapRender = new OrthogonalTiledMapRenderer(map);

        bg = new int[1];
        bg[0] = map.getLayers().getIndex("1");

        l1 = new int[1];
        l1[0] = map.getLayers().getIndex("2");


        phisX = new PhisX();

        map.getLayers().get("obj").getObjects().getByType(RectangleMapObject.class);
        RectangleMapObject tmp = (RectangleMapObject) map.getLayers().get("setting").getObjects().get("hero");
        body = phisX.addObj(tmp);

        Array<RectangleMapObject> obj = map.getLayers().get("obj").getObjects().getByType(RectangleMapObject.class);

        for (RectangleMapObject object : obj
        ) {
            phisX.addObj(object);
        }


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GOLD);
        float x = 0, y =0;

        hero.setTime(Gdx.graphics.getDeltaTime());

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (phisX.myContList.isOnGround()){
            body.applyForceToCenter(-0.00098f,0,true);}
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if (phisX.myContList.isOnGround()){
                body.applyForceToCenter(0.00098f,0,true);}
        }


        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            sound.play();

            if (phisX.myContList.isOnGround()) {
                y = 0.009f;
                if (!(controller.lookright)) {
                    x = -0.00090f;
                } else {
                    x = 0.00090f;
                }
            }
            body.applyForceToCenter(new Vector2(x, y), true);
        }

        hero.setFPS(body.getLinearVelocity(), phisX.myContList.isOnGround());

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            camera.zoom += 0.01f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            if (camera.zoom > 0) {
                camera.zoom -= 0.01f;
            }
        }


        camera.position.x = body.getPosition().x * phisX.PPM;
        camera.position.y = body.getPosition().y * phisX.PPM;

        camera.update();



        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();





        Rectangle tmp = hero.getRect(camera, hero.getFrame());
        ((PolygonShape)body.getFixtureList().get(0).getShape()).setAsBox(tmp.width/2/phisX.PPM*camera.zoom, tmp.height/2/ phisX.PPM*camera.zoom);
        ((PolygonShape)body.getFixtureList().get(1).getShape()).setAsBox(
                tmp.width/4/phisX.PPM*camera.zoom,
                tmp.height/12/phisX.PPM*camera.zoom,
                new Vector2(0,-tmp.height/2/phisX.PPM*camera.zoom),
                0);

        hero.setTime(Gdx.graphics.getDeltaTime());

        mapRender.render(l1);


        Sprite spr = new Sprite(hero.getFrame());
        spr.setOriginCenter();
        spr.scale(0.65f);
        spr.setPosition(tmp.x, tmp.y);
        spr.setBounds(tmp.x,tmp.y,tmp.width,tmp.height);
        batch.begin();
        spr.draw(batch);
        batch.end();

        mapRender.setView(camera);
        mapRender.render(bg);

        phisX.step();
        phisX.debugDraw(camera);

/*
        batch.begin();
        batch.draw(hero.getFrame(), tmp.x,tmp.y, tmp.width, tmp.height);
        batch.end();
*/



        for (int i = 0; i < bodies.size(); i++) {
            phisX.destroyBody(bodies.get(i));
        }
        bodies.clear();



        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }
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
        this.hero.dispose();
        this.batch.dispose();
        this.img.dispose();
        this.music.dispose();
        this.mapRender.dispose();
        this.phisX.dispose();
    }

}
