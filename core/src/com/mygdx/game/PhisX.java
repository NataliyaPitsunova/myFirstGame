package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyContList;

public class PhisX {
    private World world;
    private final Box2DDebugRenderer box2DDebugRenderer;
    public final float PPM = 55;
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
        fdef.friction = 0.95f;
        fdef.density = 1;
        fdef.restitution = (float) obj.getProperties().get("restitution");

        Body body;
        body = world.createBody(def);
        String name = obj.getName();
        body.createFixture(fdef).setUserData(name);
        if (name.equals("hero")) {
            polygonShape.setAsBox(rect.width / 3 / PPM, rect.height / 12 / PPM,new Vector2(0,-rect.width/2/PPM),0);
            body.createFixture(fdef).setUserData("legs");
            body.setFixedRotation(true);
        }

        polygonShape.dispose();
        return body;
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
