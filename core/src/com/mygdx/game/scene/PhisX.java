package com.mygdx.game.scene;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyContList;

public class PhisX {
    private  World world;
    private final Box2DDebugRenderer box2DDebugRenderer;

    public PhisX() {
        world = new World(new Vector2(0, -9.81f), true);
        world.setContactListener(new MyContList());
        box2DDebugRenderer = new Box2DDebugRenderer();
    }



    public Body addObj(RectangleMapObject obj) {
        Rectangle rect = obj.getRectangle();
        String type = (String) obj.getProperties().get("BodyType");
        BodyDef def = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        CircleShape circleShape;
        ChainShape chainShape;

        switch (type) {
            case "StaticBody":
                def.type = BodyDef.BodyType.StaticBody;
                break;
            case "DynamicBody":
                def.type = BodyDef.BodyType.DynamicBody;
                break;
        }

        def.position.set(rect.x + rect.width / 2, rect.y + rect.height / 2);
        def.gravityScale = (float) obj.getProperties().get("gravityScale");

        polygonShape.setAsBox(rect.width / 2, rect.height / 2);

        fdef.shape = polygonShape;
        fdef.friction = 0.85f;
        fdef.density = 0.0001f;
        fdef.restitution = (float) obj.getProperties().get("restitution");

        Body body;
        body = world.createBody(def);
        String name = obj.getName();
        body.createFixture(fdef).setUserData(name);


        polygonShape.dispose();
        return body;
    }

    public void setGravity(Vector2 gravity) {
        world.setGravity(gravity);
    }

    public void step() {
        world.step(1/60f,3, 3);
    }

    public void debugDraw(OrthographicCamera cam) {
        box2DDebugRenderer.render(world, cam.combined);
    }


    public void dispose() {

        world.dispose();
        box2DDebugRenderer.dispose();
    }

    public void destroyBody(Body body) { world.destroyBody(body);
    }
}
