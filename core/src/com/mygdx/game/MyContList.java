package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.scene.GameScreen;

public class MyContList implements ContactListener {
    private int counter;

    public boolean isOnGround(){
        return counter > 0;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData() != null && b.getUserData() != null) {
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();
            if (tmpA.equals("hero") && tmpB.equals("roll")) {
                GameScreen.bodies.add(b.getBody());
            }
            if (tmpB.equals("hero") && tmpA.equals("roll")) {
                GameScreen.bodies.add(a.getBody());
            }
        }

        if (a.getUserData() != null) {
            String s = (String)a.getUserData();
            if (s.contains("legsRuns")){counter++;}
        }

        if (b.getUserData() != null) {
            String s = (String)b.getUserData();
            if (s.contains("legsRuns")){counter++;}
        }
        }


    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData() != null) {
            String s = (String)a.getUserData();
            if (s.contains("legsRuns")){counter--;}
        }

        if (b.getUserData() != null) {
            String s = (String)b.getUserData();
            if (s.contains("legsRuns")){counter--;}
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
