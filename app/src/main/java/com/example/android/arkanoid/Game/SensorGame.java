package com.example.android.arkanoid.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;

import com.example.android.arkanoid.Settings.Settings;

public class SensorGame extends AbstractGame implements SensorEventListener {

    private Costanti cost;
    private boolean landscape; // variabile per landscape
    SharedPreferences settings; // serve per caricare il valore del landscape


    private SensorManager sManager;
    private Sensor accelerometer;

    public SensorGame(Context context, int lifes, int score) {
        super(context, lifes, score);

        settings = context.getSharedPreferences(Settings.FILENAME, Context.MODE_PRIVATE);
        landscape = settings.getBoolean("landscape",false);
        cost = new Costanti();

        if(!landscape){
            cost.setValoreSensorLeft(26);
            cost.setValoreSensorRight(26);//setta le variabili per portraint o landscape
        }else{
            cost.setValoreSensorLeft(260);
            cost.setValoreSensorRight(280);
        }

        //crea un accelerometro e un SensorManager
        sManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE); //istanzia il sensore
        accelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //definisce l'accelerometro come sensore
    }

    public void stopTiro() {
        sManager.unregisterListener(this);
    }


    public void correreTiro() {
        sManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    // modifica accelerometro
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(isSystem_ready() && !super.pausa) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                if(landscape) {
                    float x = (float) 1.5;
                    super.paddle.setX(super.paddle.getX() + (event.values[1] * x) + (event.values[1] * x));
                    Log.d("valore cc", String.valueOf(event.values[0]));
                } else{
                    float x = (float) 1.3;
                    super.paddle.setX(super.paddle.getX() - (event.values[0]*x) - (event.values[0]*x));
                }

                if (super.paddle.getX() + event.values[0] > size.x - (paddle.getDim() / 2) - cost.getValoreSensorRight()) { //velocità barra
                    super.paddle.setX(size.x - (paddle.getDim() / 2) - cost.getValoreSensorRight());
                } else if (super.paddle.getX() - event.values[0] <= cost.getValoreSensorLeft() + (paddle.getDim() / 2)) {
                    super.paddle.setX(cost.getValoreSensorLeft() + (paddle.getDim() / 2));
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    //gestione funzione della pausa
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isSystem_ready()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //qui fa il controllo se clicchi su pausa
                    if (event.getX() > -10 && event.getY() > -10 && event.getX() < 200 && event.getY() < 200) {
                        super.pausa=true;
                        break;
                    }
                    //qui fa il controllo se clicchi su help
                    if (event.getX() > size.x -200 && event.getY() > -10 && event.getX() < size.x +10 && event.getY() < 200) {
                        super.pausa=true;
                        super.help=true;
                        break;
                    }
                case MotionEvent.ACTION_MOVE:
                    if(!landscape) {
                        if (pausa && !restart && !help) {
                            //play
                            if (event.getX() > size.x / 2 - 200 && event.getY() > size.y / 2 - 450 && event.getX() < size.x / 2 + 200 && event.getY() < size.y / 2 - 50) {
                                super.pausa = false;
                            }
                            //restart
                            if (event.getX() > size.x / 2 - 200 && event.getY() > size.y / 2 + 75 && event.getX() < size.x / 2 + 200 && event.getY() < size.y / 2 + 325) {
                                super.restart = true;
                                //super.lifes = 1;
                                //super.controllaVite();
                            }
                            //esci
                            if (event.getX() > size.x / 2 - 200 && event.getY() > size.y / 2 + 500 && event.getX() < size.x / 2 + 200 && event.getY() < size.y / 2 + 750) {
                                super.esci = true;
                            }
                        }

                    }else{
                        if (pausa && !restart && !help) {
                            //play
                            if (event.getX() > size.x / 2 - 650 && event.getY() > size.y / 2 +25 && event.getX() < size.x / 2 -250 && event.getY() < size.y / 2 +250) {
                                super.pausa = false;
                            }
                            //restart
                            if (event.getX() > size.x / 2 - 200 && event.getY() > size.y / 2 + 25 && event.getX() < size.x / 2 + 200 && event.getY() < size.y / 2 + 250) {
                                super.restart = true;
                                //super.lifes = 1;
                                //super.controllaVite();
                            }
                            //esci
                            if (event.getX() > size.x / 2 + 250 && event.getY() > size.y / 2 + 25 && event.getX() < size.x / 2 + 650 && event.getY() < size.y / 2 + 250) {
                                super.esci = true;
                            }
                        }

                    }
                    if (restart) {
                        //si
                        if (event.getX() > size.x / 2 - 320 && event.getY() > size.y / 2 + 100 && event.getX() <size.x / 2 - 70  && event.getY() < size.y / 2 + 275 ) {
                            super.pausa =false;
                            super.restart = false;
                            level = 1;
                            score = 0;
                            lifes = 3;
                            resetLevel();
                            invalidate();
                            start = false;
                        }
                        //no
                        if (event.getX() > size.x / 2 + 130 && event.getY() > size.y / 2 + 100 && event.getX() <size.x / 2 + 380  && event.getY() < size.y / 2 + 275 ) {
                            super.restart = false;
                        }
                    }
                    if (help) {
                        // x
                        if(!landscape){
                            if (event.getX() > size.x/2 -50 && event.getY() > size.y/2 + 500 && event.getX() < size.x/2 + 50 && event.getY() < size.y/2 +750) {
                                super.pausa = false;
                                super.help = false;
                            }
                        } else{
                            if (event.getX() > size.x/2 +700 && event.getY() > size.y/2 - 500 && event.getX() < size.x/2 + 800 && event.getY() < size.y/2 -250) {
                                super.pausa = false;
                                super.help = false;
                            }
                        }
                    }
                    break;
            }
        }
        return true;
    }
}




