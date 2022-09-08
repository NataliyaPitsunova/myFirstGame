package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimaHero {
    private Texture img;
    private final TextureAtlas atlas;
    private Animation<TextureRegion> anm;
    private TextureRegion region0;
    private float time;


    public AnimaHero(String name, String action, Animation.PlayMode playMode) {         //action: "hurt" "DIE" "idle" "run" "walk" "jump" "ATTACK"
        atlas = new TextureAtlas("atlas/" + name + ".atlas");
        float fps = 1 / 15f;
        anm = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(fps, atlas.findRegions(action));
        anm.setPlayMode(playMode);
        time =0;
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

    public void setPlayMode(Animation.PlayMode playMode) {
        anm.setPlayMode(playMode);
    }

    public void dispose() {
        atlas.dispose();
    }
}
