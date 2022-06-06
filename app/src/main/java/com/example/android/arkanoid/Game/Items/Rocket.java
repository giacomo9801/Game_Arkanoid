package com.example.android.arkanoid.Game.Items;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.example.android.arkanoid.R;

public class Rocket extends View {


    private Bitmap rocket;
    protected final float speed = 30;
    private float x;
    private float y;

    public Rocket(Context context, float x, float y) {
        super(context);
        this.x = x;
        this.y = y;
        rocket = BitmapFactory.decodeResource(getResources(), R.drawable.laser_bullet);
    }

    public void fall() {
            y = y - speed;
        }


    public Bitmap getRocket() {
        return rocket;
    }

    public void setRocket(Bitmap rocket) {
        this.rocket = rocket;
    }

    public float getSpeed() {
        return speed;
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

