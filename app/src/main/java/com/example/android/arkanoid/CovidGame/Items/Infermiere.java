package com.example.android.arkanoid.CovidGame.Items;

import com.example.android.arkanoid.Game.Items.PowerUp;

public class Infermiere {

    private float x;
    private float y;
    private float dim;

    public final static float HEIGHT = 200; //altezza standard

    public static float STANDARD_DIM = 200; //larghezza standard


    public Infermiere(float x, float y, float dim) {
        this.x = x;
        this.y = y;
        this.dim = dim;
    }

    //controlla se l' infermiere ha impattato il powerup
    public boolean impattoPwrUp(PowerUp p) {
        boolean flag = false;

        if (isNear(p, this.x, this.y, dim)) {
            flag = true;
        }

        return flag;
    }

    //controlla se il powerup è vicino la barra (paddle)
    protected boolean isNear(PowerUp p, float xInfermiere, float yInfermiere, float dimInfermiere) {
        boolean flag = false;

        float xDelta = p.getX();
        float yDelta = p.getY();

        yDelta += p.getSpeed() + (PowerUp.DIM / 2);

        float xRightInfermiere = xInfermiere + (dimInfermiere / 2) + (PowerUp.DIM / 2);
        float xLeftInfermiere = xInfermiere - (dimInfermiere / 2) - (PowerUp.DIM / 2);

        if(yDelta >= yInfermiere && yDelta <= yInfermiere + 20) {
            if(xDelta >= xLeftInfermiere && xDelta <= xRightInfermiere) {
                flag = true;
            }
        }

        return flag;
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
