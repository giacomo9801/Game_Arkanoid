package com.example.android.arkanoid.CovidGame.Items;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.example.android.arkanoid.R;

public class CovidSmall extends View {

    private static float yVelocita= 4;
    private Bitmap covidSmall;
    private float x;
    private float y;
    private float xVelocita;
    final static float DIM = 20;
    static float WIDTH = 20;
    static float HEIGHT = 20;

    final static float RADIUS = 20;

    public CovidSmall(Context context, float x, float y) {
        super(context);
        this.x = x + (WIDTH / 2);
        this.y = y + (HEIGHT / 2) + 200;
        creaVelocita();

        covidSmall = BitmapFactory.decodeResource(getResources(), R.drawable.covid_rocket);
    }


    //crea uno spostamento del covid small sull'asse delle x casuale
    protected void creaVelocita() {
        int maxX = 2;
        int minX = -2;
        int rangeX = maxX - minX + 1;

        xVelocita = (int) (Math.random() * rangeX) + minX; //spostamento iniziale asse x
    }


    public void fall() {
        x = xVelocita+x;
        y = y + yVelocita;
    }


    //se la palla si scontra con la barra (paddle), cambierà direzione
    public boolean impattoInfermiere(float xInfermiere, float yInfermiere, float dimInfermiere) {
        if (isNearInfermiere(getX(), getY(), xInfermiere, yInfermiere, dimInfermiere)) {
            return true;
        }
        else return false;
    }

    private boolean isNearInfermiere(float ax, float ay, float xInfermiere, float yInfermiere, float dimInfermiere) {
        boolean flag = false;

        float xCovidSmall = ax + xVelocita;
        float yCovidSmall = ay + yVelocita;

        float xDist = Math.abs(xCovidSmall - xInfermiere);
        float yDist = Math.abs(yCovidSmall - yInfermiere);
        float cornerDist = (float) (Math.pow(xCovidSmall - (dimInfermiere / 2), 2) + Math.pow(yCovidSmall - (Infermiere.HEIGHT / 2), 2));

        if(xDist <= (dimInfermiere / 2)) {
            if(yDist <= (Infermiere.HEIGHT / 2)) {
                flag = true;
            }
        }
        if(cornerDist <= Math.pow(RADIUS, 2)) {
            flag = true;
        }
        return flag;
    }

    public Bitmap getCovidSmall() {
        return covidSmall;
    }

    public void setCovidSmall(Bitmap covidSmall) { this.covidSmall = covidSmall; }

    public static void setSpeed(float sped) {
        yVelocita = sped;
    }

    public static float getSpeed() {
        return yVelocita;
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
