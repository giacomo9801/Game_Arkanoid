package com.example.android.arkanoid.CovidGame.Items;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.example.android.arkanoid.R;

public class Vaccino extends View {

    private Bitmap vaccino;
    public static float speed = 10; //velocità vaccino
    private float x;
    private float y;

    public Vaccino(Context context, float x, float y) {
        super(context);
        this.x = x;
        this.y = y;
        vaccino = BitmapFactory.decodeResource(getResources(), R.drawable.vaccino); //bitmap vaccino
    }

    public void fall() {
        y = y - speed;
    }


    public Bitmap getVaccino() {
        return vaccino;
    }

    public void setVaccino(Bitmap vaccino) { this.vaccino = vaccino; }

    public float getSpeed() {
        return speed;
    }


    public void setSpeed(float v) {
        this.speed = v;
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
