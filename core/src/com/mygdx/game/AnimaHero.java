package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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


    public AnimaHero(String name, int col, int row, Animation.PlayMode playMode) {

    /*    img = new Texture(name);
        region0 = new TextureRegion(img);
        int xCnt = region0.getRegionWidth() / col;
        int yCnt = region0.getRegionHeight() / row;
        TextureRegion[][] regions = region0.split(xCnt, yCnt);
        TextureRegion[] region1 = new TextureRegion[regions.length * regions[0].length];
        int cnt = 0;


        for (TextureRegion[] i : regions
        ) {
            for (TextureRegion hero : i
            ) {
                region1[cnt++] = hero;
            }
        }
        anm = new Animation<TextureRegion>(1.0f/16, regions[0]);
        anm.setPlayMode(playMode);

        time += Gdx.graphics.getDeltaTime();*/

        atlas = new TextureAtlas("atlas/atlas.atlas");
 /*       anm = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1 / 30f, atlas.findRegions("idle"));
        anm = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1 / 30f, atlas.findRegions("walk"));*/
        anm = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1 / 15f, atlas.findRegions("run"));
/*
        anm = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1 / 30f, atlas.findRegions("jump"));
        anm = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1 / 30f, atlas.findRegions("ATTACK"));
        anm = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1 / 30f, atlas.findRegions("hurt"));
        anm = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1 / 30f, atlas.findRegions("DIE"));
*/




        anm.setPlayMode(playMode);

        time += Gdx.graphics.getDeltaTime();
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
        img.dispose();
        atlas.dispose();
    }
}
