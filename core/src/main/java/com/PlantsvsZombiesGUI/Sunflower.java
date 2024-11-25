//package com.PlantsvsZombiesGUI;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.utils.Array;
//
//public class Sunflower extends Plant {
//    private Animation<TextureRegion> animation;
//    private float stateTime;
//
//    public Sunflower(float x, float y) {
//        super(x, y);
//        this.stateTime = 0f;
//
//        // Crear la animación desde el sprite sheet
//        this.animation = createAnimation(spriteSheet, 64, 64);
//        this.sprite = animation.getKeyFrame(0); // Frame inicial
//    }
//
//    private Animation<TextureRegion> createAnimation(Texture spriteSheet, int frameWidth, int frameHeight) {
//        TextureRegion[][] regions = TextureRegion.split(spriteSheet, frameWidth, frameHeight);
//        Array<TextureRegion> frames = new Array<>();
//        for (TextureRegion[] row : regions) {
//            for (TextureRegion frame : row) {
//                frames.add(frame);
//            }
//        }
//        return new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
//    }
//
//    @Override
//    public void act(float delta) {
//        super.act(delta);
//        stateTime += delta;
//        sprite = animation.getKeyFrame(stateTime, true); // Actualizar el frame actual
//    }
//}
