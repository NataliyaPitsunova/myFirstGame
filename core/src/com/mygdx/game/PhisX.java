package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PhisX {
    private World world;
    private final Box2DDebugRenderer box2DDebugRenderer;
    public final float PPM = 100;
    public MyContList myContList;

    public PhisX() {
        world = new World(new Vector2(0, -9.81f), true);
        myContList = new MyContList();
        world.setContactListener(myContList);
        box2DDebugRenderer = new Box2DDebugRenderer();
    }


    public Body addObj(RectangleMapObject obj) {
        Rectangle rect = obj.getRectangle();
        String type = (String) obj.getProperties().get("BodyType");
        BodyDef def = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();


        switch (type) {
            case "StaticBody":
                def.type = BodyDef.BodyType.StaticBody;
                break;
            case "DynamicBody":
                def.type = BodyDef.BodyType.DynamicBody;
                break;
        }

        def.position.set((rect.x + rect.width / 2) / PPM, (rect.y + rect.height / 2) / PPM);
        def.gravityScale = (float) obj.getProperties().get("gravityScale");

        polygonShape.setAsBox(rect.width / 2 / PPM, rect.height / 2 / PPM);

        fdef.shape = polygonShape;
        fdef.friction = 0.85f;
        fdef.density = 1;
        fdef.restitution = (float) obj.getProperties().get("restitution");

                String name = "";
        if (obj.getName() != null) {
            name = obj.getName();
        }
        Body body;
        body = world.createBody(def);

        body.setUserData(new PhisBodies(name, new Vector2(rect.x, rect.y), new Vector2(rect.width, rect.height)));
        body.createFixture(fdef).setUserData(name);

        if (name != null && name.equals("hero")) {
            //сенсор для бега
            polygonShape.setAsBox(rect.width / 3 / PPM, rect.height / 12 / PPM, new Vector2(0, -rect.width / 2 / PPM), 0);
            fdef.restitution = 0;
            body.createFixture(fdef).setUserData("legsRuns");
            body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);
            body.setFixedRotation(true);
            body.getFixtureList().get(0).setRestitution(0);
//маленький сенсор для стония не на земле
            polygonShape.setAsBox(rect.width / 5 / PPM, rect.height / 20 / PPM, new Vector2(0, -rect.width / 2 / PPM), 0);
            fdef.restitution = 0;
            body.createFixture(fdef).setUserData("legsJump");
            body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);
            body.setFixedRotation(true);
            body.getFixtureList().get(0).setRestitution(0);
        }

        polygonShape.dispose();
        return body;
    }

    @SuppressWarnings("NewApi")
    public Array<Body> getBodies(String name) {
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        Arrays.stream(bodies.items).
                filter(body -> ((PhisBodies) body.getUserData()).name.equals(name))
                .collect(Collectors.toList());
        return bodies;
    }

    public void setGravity(Vector2 gravity) {
        world.setGravity(gravity);
    }

    public void step() {
        world.step(1 / 60f, 3, 3);
    }

    public void debugDraw(OrthographicCamera cam) {
        box2DDebugRenderer.render(world, cam.combined);
    }


    public void dispose() {

        world.dispose();
        box2DDebugRenderer.dispose();
    }

    public void destroyBody(Body body) {
        world.destroyBody(body);
    }
}
