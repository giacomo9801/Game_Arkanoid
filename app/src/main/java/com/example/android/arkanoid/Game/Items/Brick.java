package com.example.android.arkanoid.Game.Items;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.example.android.arkanoid.Game.Costanti;
import com.example.android.arkanoid.R;
import com.example.android.arkanoid.Settings.Settings;

public class Brick extends View {

    private Bitmap brick;
    private float x;
    private float y;
    private int vita;
    private boolean goldenbrick;
    public static float WIDTH ;
    public static float HEIGHT;

    private Costanti cost;
    private boolean landscape; // variabile per landscape
    SharedPreferences settings; // serve per caricare il valore del landscape

    public Brick(Context context, float x, float y, int a) {
        super(context);

        settings = context.getSharedPreferences(Settings.FILENAME, Context.MODE_PRIVATE);
        landscape = settings.getBoolean("landscape",false);
        cost = new Costanti();

        if(!landscape){
            cost.setBrickWidth(100);//setta le variabili per portraint o landscape
            cost.setBrickHeight(80);
        }else{
            cost.setBrickWidth(100);//setta le variabili per portraint o landscape
            cost.setBrickHeight(50);
        }

        Brick.HEIGHT = cost.getBrickHeight();
        Brick.WIDTH = cost.getBrickWidth();

        this.x = x + (WIDTH / 2);
        this.y = y + (HEIGHT / 2) + 200;
        goldenbrick = false;
        skin(a);

    }

    //assegna un'immagine casuale al mattone
    private void skin(int a) {
        switch (a) {
            case 0:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_aqua);
                vita = 0;
                break;
            case 1:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_blue);
                vita = 0;
                break;
            case 2:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_green);
                vita = 0;
                break;
            case 3:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_orange);
                vita = 0;
                break;
            case 4:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_white);
                vita = 0;
                break;
            case 5:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_red);
                vita = 0;
                break;
            case 6:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_yellow);
                vita = 0;
                break;
            case 7:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_purple);
                vita = 0;
                break;
            case 8:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_silver_10);
                vita = 3;
                break;
            case 9: //questo mattone avrà un potere speciale se colpito (ad esempio passa al livello successivo, oppure avrà il power up life)
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_gold);
                vita = 0;
                goldenbrick = true; //se il mattoncino creato è dorato, controllo per il pup della vita
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

    public Bitmap getBrick() {
        return brick;
    }

    public int getVita() {
        return vita;
    }

    public void setVita(int vita) {
        this.vita = vita;
    }

    //controlla se un missile colpisce un mattone
    public boolean impattoMissile(float x, float y) {
        boolean flag = false;

        if (isNearMissile(x, y)) {
            flag = true;
        }

        return flag;
    }

    public boolean isNearMissile(float x, float y) {
        boolean flag = false;

        if(y <= getY() + (Brick.HEIGHT / 2)) {
            if(x > getX() - (Brick.WIDTH / 2) - 20 && x < getX() + (Brick.WIDTH / 2) + 20) {
                flag = true;
            }
        }

        return flag;
    }

    // cambia il layout del Brick silver
    public void layoutBrickSilver(int vit) {
        switch (vit) {
            case 2:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_silver_10_crepa1);
                break;
            case 1:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_silver_10_crepa2);
                break;
            case 0:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_silver_10_crepa3);
                break;
        }
    }

    public boolean isGoldenbrick() { //serve per controllare se il mattoncino è dorato
        return goldenbrick;
    }
}