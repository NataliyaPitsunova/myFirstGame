package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimaHero {
    private Texture img;
    private TextureRegion region0;
    private Animation anm;
    private float time;


    public AnimaHero(String name, int col, int row, Animation.PlayMode playmode) {
        img = new Texture(name);
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
        anm.setPlayMode(playmode);

        time += Gdx.graphics.getDeltaTime();

    }

    public TextureRegion getFrame() {
        return (TextureRegion) anm.getKeyFrame(time);
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
    }
}
