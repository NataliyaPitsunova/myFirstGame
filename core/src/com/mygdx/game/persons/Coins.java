package com.mygdx.game.persons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Coins {

    private final TextureAtlas atlas;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> anm;

    private float time;

    public Coins() {
        atlas = new TextureAtlas("atlas/coins.atlas");

        anm = anm = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1 / 30f, atlas.findRegions("coin"));
        time += Gdx.graphics.getDeltaTime();
        anm.setPlayMode(com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
    }

    public TextureRegion getFrame() {
        return anm.getKeyFrame(time);
    }

    public void setTime(float time) {
        this.time += time;
    }

    public void zeroTime() {
        this.time = 0;
    }

    public boolean isAnimationOver() {
        return anm.isAnimationFinished(time);
    }

    public void dispose() {
        atlas.dispose();
    }
}

