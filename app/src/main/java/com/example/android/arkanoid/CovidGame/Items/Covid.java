package com.example.android.arkanoid.CovidGame.Items;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.example.android.arkanoid.R;

public class Covid extends View  {

    private Bitmap covid;
    private float x;
    private float y;
    private int vita;
    private int a;
    public static float WIDTH = 150;
    public static float HEIGHT = 150;

    public Covid(Context context, float x, float y, int a) {
        super(context);
        this.x = x + (WIDTH / 2);
        this.y = y + (HEIGHT / 2) + 200;
        this.a = a;
        skin(a);
    }

    //controlla se un vaccino colpisce un covid
    public boolean impattoVaccino(float x, float y) {
        boolean flag = false;

        if (isNearVaccino(x, y)) {
            flag = true;
        }

        return flag;
    }

    //controlla se la posizione successiva del vaccino impatta un covid
    private boolean isNearVaccino(float x, float y) {
        boolean flag = false;

        if(y <= getY() + (Covid.HEIGHT / 2)) {
            if(x > getX() - (Covid.WIDTH / 2) - 20 && x < getX() + (Covid.WIDTH / 2) + 20) {
                flag = true;
            }
        }

        return flag;
    }

    //controlla se un missile colpisce un covid
    public boolean impattoMissile(float x, float y) {
        boolean flag = false;

        if (isNearMissile(x, y)) {
            flag = true;
        }

        return flag;
    }

    //controlla se la posizione successiva del missile impatta un covid
    private boolean isNearMissile(float x, float y) {
        boolean flag = false;

        if(y <= getY() + (Covid.HEIGHT / 2)) {
            if(x > getX() - (Covid.WIDTH / 2) - 20 && x < getX() + (Covid.WIDTH / 2) + 20) {
                flag = true;
            }
        }

        return flag;
    }

    //assegna un'immagine casuale al mattone
    private void skin(int a) {
        switch (a) {
            case 0:
                covid  = BitmapFactory.decodeResource(getResources(), R.drawable.covid_blue);
                vita = 6;
                break;
            case 1:
                covid = BitmapFactory.decodeResource(getResources(), R.drawable.covid_red);
                vita = 8;
                break;
            case 2:
                covid = BitmapFactory.decodeResource(getResources(), R.drawable.covid_green);
                vita = 4;
                break;
        }
    }


    // cambia il layout del covid red
    public void layoutCovid(int vit) {
        switch (a) {
            case 0:
                switch (vit) {
                    case 5:
                        covid = BitmapFactory.decodeResource(getResources(), R.drawable.covid_blue_1);
                        break;
                    case 3:
                        covid = BitmapFactory.decodeResource(getResources(), R.drawable.covid_blue_2);
                        break;
                    case 1:
                        covid = BitmapFactory.decodeResource(getResources(), R.drawable.covid_blue_3);
                        break;
                }
                break;
                case 1:
                    switch (vit) {
                case 6:
                    covid = BitmapFactory.decodeResource(getResources(), R.drawable.covid_red1);
                    break;
                case 4:
                    covid = BitmapFactory.decodeResource(getResources(), R.drawable.covid_red2);
                    break;
                case 2:
                    covid = BitmapFactory.decodeResource(getResources(), R.drawable.covid_red_3);
                    break;
            }
            break;
                    case 2:
                        switch (vit) {
                            case 2:
                                covid = BitmapFactory.decodeResource(getResources(), R.drawable.covid_green_1);
                                break;
                            case 1:
                                covid = BitmapFactory.decodeResource(getResources(), R.drawable.covid_green_2);
                                break;
                            case 0:
                                covid = BitmapFactory.decodeResource(getResources(), R.drawable.covid_green_3);
                                break;
                        }
                        break;
        }
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

    public Bitmap getCovid() {
        return covid;
    }

    public int getVita() {
        return vita;
    }

    public void setVita(int vita) {
        this.vita = vita;
    }

}
