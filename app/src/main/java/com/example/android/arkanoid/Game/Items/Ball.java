package com.example.android.arkanoid.Game.Items;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.android.arkanoid.Game.Costanti;
import com.example.android.arkanoid.Settings.Settings;

public class Ball {
    protected float xVelocita;
    protected float yVelocita;
    private float x;
    private float y;
    private Costanti cost;
    private MediaPlayer musica;
    private boolean landscape; // variabile per landscape
    SharedPreferences settings; // serve per caricare il valore del landscape


    public static float RADIUS;


    public Ball(Context context, float x, float y) {
        this.x = x;
        this.y = y;

        settings = context.getSharedPreferences(Settings.FILENAME, Context.MODE_PRIVATE);
        landscape = settings.getBoolean("landscape",false);
        cost = new Costanti();

        if(!landscape) {
            cost.setModDirezione(1400);//setta le variabili per portraint o landscape
            Ball.RADIUS = 15;
        }
        else {
            cost.setModDirezione(750);
            Ball.RADIUS = 15;
        }

        creaVelocita();
    }

    //crea una velocità della palla casuale
    public void creaVelocita() {
        int maxX = 13;
        int minX = 7;
        int maxY = -17;
        int minY = -23;
        int rangeX = maxX - minX + 1;
        int rangeY = maxY - minY + 1;

        xVelocita = (int) (Math.random() * rangeX) + minX; //spostamento iniziale asse x
        yVelocita = (int) (Math.random() * rangeY) + minY; //spostamento iniziale asse y
    }

    //cambia direzione in base alla velocità
    public void modificaDirezione() {
        if(y > cost.getModDirezione()) {
            if (xVelocita < 0 && yVelocita > 0) {
                Log.d("mytag", "sotto1");
                otocYRychlost();
            } else if (xVelocita > 0 && yVelocita > 0) {
                Log.d("mytag", "sotto2");
                otocYRychlost();
            }
        }
        else {
            if (xVelocita > 0 && yVelocita < 0) {
                Log.d("mytag", "sopra");
                otocYRychlost();
            } else if (xVelocita < 0 && yVelocita < 0) {
                otocYRychlost();
            } else if (xVelocita < 0 && yVelocita > 0) {
                otocXRychlost();
            } else if (xVelocita > 0 && yVelocita > 0) {
                otocYRychlost();
            }
        }
    }

    //aumenta la velocità in base al livello
    public void aumentaVelocita(int level) {
        if(landscape){
            level = 1;
        }
        xVelocita = xVelocita + (1 * level);
        yVelocita = yVelocita - (1 * level);
    }

    //cambia direzione a seconda del muro toccato e della velocità
    public void modificaDirezione(String stena) {
        if (xVelocita > 0 && yVelocita < 0 && stena.equals("right")) {
            otocXRychlost();
            Log.d("tag","right");
        } else if (xVelocita > 0 && yVelocita < 0 && stena.equals("up")) {
            otocYRychlost();
        } else if (xVelocita < 0 && yVelocita < 0 && stena.equals("up")) {
            otocYRychlost();
        } else if (xVelocita < 0 && yVelocita < 0 && stena.equals("left")) {
            otocXRychlost();
        } else if (xVelocita < 0 && yVelocita > 0 && stena.equals("left")) {
            otocXRychlost();
        } else if (xVelocita > 0 && yVelocita > 0 && stena.equals("dole")) {
            otocYRychlost();
            Log.d("tag","sotto");
        } else if (xVelocita > 0 && yVelocita > 0 && stena.equals("right")) {
            otocXRychlost();
        }
    }

    private boolean isNearPaddle(float ax, float ay, float xPaddle, float yPaddle, float dimPaddle) {
        boolean flag = false;

        float xBall = ax + xVelocita;
        float yBall = ay + yVelocita;

        float xDist = Math.abs(xBall - xPaddle);
        float yDist = Math.abs(yBall - yPaddle);
        float cornerDist = (float) (Math.pow(xBall - (dimPaddle / 2), 2) + Math.pow(yBall - (Paddle.HEIGHT / 2), 2));

        if(xDist <= (dimPaddle / 2)) {
            if(yDist <= (Paddle.HEIGHT / 2)) {
                flag = true;
            }
        }
        if(cornerDist <= Math.pow(RADIUS, 2)) {
            flag = true;
        }
        return flag;
    }

    //scopri se la palla è vicina a un mattone
    private boolean isNearBrick(float x, float y) {
        boolean flag = false;

        float xBall = getX() + xVelocita;
        float yBall = getY() + yVelocita;

        float xDist = Math.abs(xBall - x);
        float yDist = Math.abs(yBall - y);

        float cornerDist = (float) (Math.pow(xBall - (Brick.WIDTH / 2), 2) + Math.pow(yBall - (Brick.HEIGHT / 2), 2));

        if(xDist <= (Brick.WIDTH / 2)) {
            if(yDist <= (Brick.HEIGHT / 2)) {
                flag = true;
            }
        }

        if(cornerDist <= Math.pow(RADIUS, 2)) {
            flag = true;
        }
        return flag;
    }

    //se la palla si scontra con la barra (paddle), cambierà direzione
    public boolean impattoBarra(float xPaddle, float yPaddle, float dimPaddle) {
        if (isNearPaddle(getX(), getY(), xPaddle, yPaddle, dimPaddle)) {
            modificaDirezione();
            return true;
        }
        else return false;
    }

    //se la palla entra in collisione con un mattone, cambia direzione
    public boolean impattoMattone(float xBrick, float yBrick) {
        float xLeft = xBrick - (Brick.WIDTH / 2);
        float xRight = xBrick + (Brick.WIDTH / 2);
        float yUp = yBrick - (Brick.WIDTH / 2);
        float yDown = yBrick + (Brick.WIDTH / 2);

        float xBall = getX() + xVelocita;
        float yBall = getY() + yVelocita;
        if (isNearBrick(xBrick, yBrick)) {
            if(getX() >= xLeft && getX() <= xRight && yBall >= yUp && yBall <= yDown) {
                otocYRychlost();
            }
            else {
                otocXRychlost();
            }
            return true;
        } else return false;
    }
    

    //muovi la palla alla velocità specificata
    public void corri() {
        x = x + xVelocita;
        y = y + yVelocita;
    }

    public void otocXRychlost() {
        xVelocita = -xVelocita;
    } //verso

    public void otocYRychlost() {
        yVelocita = -yVelocita;
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

    public void setY(float y) {
        this.y = y;
    }

    public void setxVelocita(float xVelocita) {
        this.xVelocita = xVelocita;
    }

    public void setyVelocita(float yVelocita) {
        this.yVelocita = yVelocita;
    }

    public float getxVelocita() {
        return xVelocita;
    }

    public float getyVelocita() {
        return yVelocita;
    }

    public void incrementSpeed() {
        if(xVelocita > 0) {
            xVelocita++;
        }
        else {
            xVelocita--;
        }

        if(yVelocita > 0) {
            yVelocita++;
        }
        else {
            yVelocita--;
        }
    }
}
//modifica classe ball