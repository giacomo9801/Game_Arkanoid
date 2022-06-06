package com.example.android.arkanoid.Game.Items;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.example.android.arkanoid.R;

public class PowerUp extends View {

    private int powerUpType;
    private Bitmap powerUp;
    final static float SPEED = 10;
    private float x;
    private float y;
    private int game;
    public final static float DIM = 80;

    public PowerUp(Context context, int powerUpType, float x, float y, int game) {
        super(context);
        this.powerUpType = powerUpType;
        this.x = x;
        this.y = y;
        this.game = game;
        skin();
    }

    public void fall() {
        y = y + SPEED;
    }

    public void goUp() {
        y = y - SPEED;
    }

    private void skin() {
        switch(powerUpType) {
            case 1:
                if(game == 1){
                powerUp = BitmapFactory.decodeResource(getResources(), R.drawable.powerup_expand_1);}
                else {
                    powerUp = BitmapFactory.decodeResource(getResources(), R.drawable.extend);
                }
                break;
            case 2:
                if(game == 1){
                    powerUp = BitmapFactory.decodeResource(getResources(), R.drawable.powerup_laser_1);}
                else {
                    powerUp = BitmapFactory.decodeResource(getResources(), R.drawable.up_vaccino);
                }
                break;
            case 3:
                if(game == 1){
                    powerUp = BitmapFactory.decodeResource(getResources(), R.drawable.powerdown_small);}
                else {
                    powerUp = BitmapFactory.decodeResource(getResources(), R.drawable.small);
                }
                break;
            case 4:
                if(game == 1){
                    powerUp = BitmapFactory.decodeResource(getResources(), R.drawable.powerdown_devil);}
                else {
                    powerUp = BitmapFactory.decodeResource(getResources(), R.drawable.speed);
                }
                break;
            case 5:
                if(game == 1){
                    powerUp = BitmapFactory.decodeResource(getResources(), R.drawable.life_up);}
                else {
                    powerUp = BitmapFactory.decodeResource(getResources(), R.drawable.mascherina);
                }
                break;
        }
    }

    public int getPowerUpType() {
        return powerUpType;
    }

    public void setPowerUpType(int powerUpType) {
        this.powerUpType = powerUpType;
    }

    public Bitmap getPowerUp() {
        return powerUp;
    }

    public void setPowerUp(Bitmap powerUp) {
        this.powerUp = powerUp;
    }

    public float getSpeed() {
        return SPEED;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }
}