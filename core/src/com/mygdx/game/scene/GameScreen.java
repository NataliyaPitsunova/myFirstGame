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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.AnimaHero;
import com.mygdx.game.KeyboardController;
import com.mygdx.game.Main;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture img;
    private final AnimaHero anmJump;
    private final AnimaHero anmIdle;
    private final AnimaHero anmHurt;
    private final AnimaHero anmWalk;
    private final AnimaHero anmAttack;
    private final AnimaHero anmDie;
    private final AnimaHero anmRun;
    private AnimaHero hero;
    private KeyboardController controller;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRender;
    private PhisX phisX;
    private boolean lookRight = false;
    private final int[] bg;
    private final int[] l1;
    private Body body;
    private final Rectangle heroRect;
    public static ArrayList<Body> bodies;


    public GameScreen(Main game) {
        this.game = game;
        controller = new KeyboardController();
        bodies = new ArrayList<>();
        batch = new SpriteBatch();
        img = new Texture("bg.png");
        anmIdle = new AnimaHero("atlas", "idle", Animation.PlayMode.LOOP);
        anmJump = new AnimaHero("atlas", "jump", Animation.PlayMode.LOOP);
        anmHurt = new AnimaHero("atlas", "hurt", Animation.PlayMode.LOOP);
        anmDie = new AnimaHero("atlas", "DIE", Animation.PlayMode.LOOP);
        anmRun = new AnimaHero("atlas", "run", Animation.PlayMode.LOOP);
        anmWalk = new AnimaHero("atlas", "walk", Animation.PlayMode.LOOP);
        anmAttack = new AnimaHero("atlas", "ATTACK", Animation.PlayMode.LOOP);
        hero = anmIdle;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.35f;

        TiledMap map = new TmxMapLoader().load("map/map1.tmx");
        mapRender = new OrthogonalTiledMapRenderer(map);

        bg = new int[1];
        bg[0] = map.getLayers().getIndex("1");

        l1 = new int[3];
        l1[0] = map.getLayers().getIndex("2");
        l1[1] = map.getLayers().getIndex("3");
        l1[2] = map.getLayers().getIndex("6");

        phisX = new PhisX();

        map.getLayers().get("obj").getObjects().getByType(RectangleMapObject.class);
        RectangleMapObject tmp = (RectangleMapObject) map.getLayers().get("setting").getObjects().get("hero");
        heroRect = tmp.getRectangle();
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
        float step = 3;
        Gdx.input.setInputProcessor(controller);
        hero.setTime(Gdx.graphics.getDeltaTime());
        if (!hero.getFrame().isFlipX() && !(controller.lookright)) {
            hero.getFrame().flip(true, false);
        }

        if (hero.getFrame().isFlipX() && controller.lookright) {
            hero.getFrame().flip(true, false);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            hero = anmRun;
            body.applyForceToCenter(new Vector2(-100, 0), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            hero = anmRun;
            body.applyForceToCenter(new Vector2(+100, 0), true);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            hero = anmJump;
          //  body.applyLinearImpulse(0, 10f, body.getPosition().x, body.getPosition().y, true);
            body.applyForceToCenter(0, 8f, true);
            camera.position.y += 3*step;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            hero = anmIdle;
            camera.position.y -= step;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            camera.zoom -= 0.01f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            if (camera.zoom > 0) {
                camera.zoom += 0.01f;
            }

        }

        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y;

        camera.update();

        ScreenUtils.clear(Color.DARK_GRAY);

        System.out.println(body.getLinearVelocity());

        batch.setProjectionMatrix(camera.combined);
        heroRect.x = body.getPosition().x - heroRect.width / 2;
        heroRect.y = body.getPosition().y - heroRect.height / 2;
        mapRender.setView(camera);
        mapRender.render(bg);
        mapRender.render(l1);

        batch.begin();
        batch.draw(hero.getFrame(), heroRect.x, heroRect.y, heroRect.width, heroRect.height);
        batch.end();


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }


        phisX.step();
        phisX.debugDraw(camera);

/*        Iterator<Body> bodyIterator = bodies.listIterator();
        for (int i = 0; i < bodies.size(); i++) {
            phisX.destroyBody(bodies.get(i));
        }
        bodies.clear();*/
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
    }

}
