package com.mygdx.game.persons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class AnimaHero {
    HashMap<HeroAction, Animation<TextureRegion>> heroAction;
    private Texture img;
    private TextureAtlas atlas;
    private Animation<TextureRegion> anm;
    private TextureRegion region0;
    private float time;
    private float fps = 1 / 15f;
    private boolean loop;
    private boolean lookright;
    private boolean canJump;
    private float dScale = 2.8f;


    public AnimaHero() {

        heroAction = new HashMap<>();
        atlas = new TextureAtlas("atlas/atlas.atlas");
        heroAction.put(HeroAction.attack, new Animation<TextureRegion>(fps, atlas.findRegions("ATTACK")));
        heroAction.put(HeroAction.idle, new Animation<TextureRegion>(fps, atlas.findRegions("idle")));
        heroAction.put(HeroAction.jump, new Animation<TextureRegion>(fps, atlas.findRegions("jump")));
        heroAction.put(HeroAction.hurt, new Animation<TextureRegion>(fps, atlas.findRegions("hurt")));
        heroAction.put(HeroAction.die, new Animation<TextureRegion>(fps, atlas.findRegions("DIE")));
        heroAction.put(HeroAction.run, new Animation<TextureRegion>(fps, atlas.findRegions("run")));
        heroAction.put(HeroAction.walk, new Animation<TextureRegion>(fps, atlas.findRegions("walk")));
        anm = heroAction.get(HeroAction.idle);
    }

    public void setState(HeroAction state) {
        anm = heroAction.get(state);
        switch (state) {
            case idle:
                loop = true;
                anm.setFrameDuration(fps);
                break;
            case jump:
                loop = false;
                break;
            default:
                loop = true;
        }
    }

    public TextureRegion getFrame() {
        if (time > anm.getAnimationDuration() && loop) time = 0;
        if (time > anm.getAnimationDuration()) time = 0;
        TextureRegion region = anm.getKeyFrame(time);
        if (!region.isFlipX() && !lookright) region.flip(true, false);
        if (region.isFlipX() && lookright) region.flip(true, false);
        return region;
    }

    public float setTime(float time) {
        this.time += time;
        return time;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public void setLookright(boolean lookright) {
        this.lookright = lookright;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setFPS(Vector2 vector, boolean onGround) {
        if (vector.x > 0.1f) {
            lookright = true;
        }
        if (vector.x < -0.1f) {
            lookright = false;
        }
        float tmp = (float) (Math.sqrt(vector.x * vector.x + vector.y * vector.y)) * 10;
        setState(HeroAction.idle);
        if (Math.abs(vector.x) > 0.25f && Math.abs(vector.y) < 10 && onGround) {
            setState(HeroAction.run);
            anm.setFrameDuration(1 / tmp);
        }
        if (Math.abs(vector.y) > 1 && !onGround) {
            setState(HeroAction.jump);
            anm.setFrameDuration(fps);
        }
    }

    public Rectangle getRect(OrthographicCamera camera, TextureRegion region) {
        float cx = Gdx.graphics.getWidth() / 2 - region.getRegionWidth() / 2 / camera.zoom / dScale;
        float cy = Gdx.graphics.getHeight() / 2 - region.getRegionHeight() / 2 / camera.zoom / dScale;
        float cW = region.getRegionWidth() / camera.zoom / dScale;
        float cH = region.getRegionHeight() / camera.zoom / dScale;
        return new Rectangle(cx, cy, cW, cH);
    }

    public void dispose() {
        atlas.dispose();
        this.heroAction.clear();
    }
}
