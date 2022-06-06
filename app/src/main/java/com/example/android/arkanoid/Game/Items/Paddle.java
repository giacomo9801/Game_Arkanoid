package com.example.android.arkanoid.Game.Items;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.arkanoid.Game.Costanti;
import com.example.android.arkanoid.Settings.Settings;

public class Paddle {

    private float x;
    private float y;
    private float dim;

    public static float HEIGHT;
    public static float STANDARD_DIM;

    private Costanti cost;
    private boolean landscape; // variabile per landscape
    SharedPreferences settings; // serve per caricare il valore del landscape


    public Paddle(Context context, float x, float y, float dim) {

        this.x = x;
        this.y = y;

        settings = context.getSharedPreferences(Settings.FILENAME, Context.MODE_PRIVATE);
        landscape = settings.getBoolean("landscape",false);
        cost = new Costanti();

        if(!landscape){
            cost.setPaddleHeight(40);//setta le variabili per portraint o landscape
            cost.setPaddleWidth(200);
        }else{
            cost.setPaddleHeight(25);//setta le variabili per portraint o landscape
            cost.setPaddleWidth(170);
        }

        Paddle.STANDARD_DIM = cost.getPaddleWidth();
        Paddle.HEIGHT = cost.getPaddleHeight();

       this.dim = cost.getPaddleWidth();

    }

    //controlla se la barra (paddle) ha impattato il powerup
    public boolean impattoPwrUp(PowerUp p) {
        boolean flag = false;

        if (isNear(p, this.x, this.y, dim)) {
            flag = true;
        }

        return flag;
    }

    //controlla se il powerup è vicino la barra (paddle)
    public boolean isNear(PowerUp p, float xPaddle, float yPaddle, float dimPaddle) {
        boolean flag = false;

        float xDelta = p.getX();
        float yDelta = p.getY();

        yDelta += p.getSpeed() + (PowerUp.DIM / 2);

        float xRightPaddle = xPaddle + (dimPaddle / 2) + (PowerUp.DIM / 2);
        float xLeftPaddle = xPaddle - (dimPaddle / 2) - (PowerUp.DIM / 2);

        if(yDelta >= yPaddle && yDelta <= yPaddle + 20) {
            if(xDelta >= xLeftPaddle && xDelta <= xRightPaddle) {
                flag = true;
            }
        }

        return flag;
    }

    public void moveToX(float x) {
        float dim = 4;
        float xPaddle = this.getX();
        float dist = Math.abs(xPaddle - x);

        if(x > xPaddle) {
            float deltaDist = dist / dim;
            int c = 0;
            do {
                this.setX(this.getX() + deltaDist);
                c++;
            } while(c < dim);
        }
        else if(x == xPaddle) {

        }
        else {
            float deltaDist = dist / dim;
            int c = 0;
            do {
                this.setX(this.getX() - deltaDist);
                c++;
            } while(c < dim);
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getDim() {
        return dim;
    }

    public void setDim(float dim) {
        this.dim = dim;
    }
}