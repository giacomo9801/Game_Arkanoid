package com.example.android.arkanoid.CovidGame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;

public class SensorCovidGame extends AbstractCovidGame implements SensorEventListener {

    private SensorManager sManager;
    private Sensor accelerometer;

    public SensorCovidGame(Context context, int lifes, int score) {
        super(context, lifes, score);

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
        if (!super.pausaCovid && !gameOver) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                super.infermiere.setX(super.infermiere.getX() - event.values[0] - event.values[0]);

                if (super.infermiere.getX() + event.values[0] > size.x - (infermiere.getDim() / 2)) { //velocità barra
                    super.infermiere.setX(size.x - (infermiere.getDim() / 2));
                } else if (super.infermiere.getX() - event.values[0] <= (infermiere.getDim() / 2)) {
                    super.infermiere.setX((infermiere.getDim() / 2));
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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //qui fa il controllo se clicchi su pausa
                if (event.getX() > -10 && event.getY() > -10 && event.getX() < 200 && event.getY() < 200) {
                    super.pausaCovid =true;
                    break;
                }
                //qui fa il controllo se clicchi su help
                if (event.getX() > size.x -200 && event.getY() > -10 && event.getX() < size.x +10 && event.getY() < 200) {
                    super.pausaCovid=true;
                    super.help = true;
                    break;
                }
            case MotionEvent.ACTION_MOVE:
                if (pausaCovid && !restart && !help ) {
                    //play
                    if (event.getX() > size.x / 2 - 200 && event.getY() > size.y / 2 - 450 && event.getX() < size.x / 2 + 200 && event.getY() < size.y / 2 - 100) {
                        super.pausaCovid = false;
                    }
                    //restart
                    if (event.getX() > size.x / 2 - 200 && event.getY() > size.y / 2 + 75 && event.getX() < size.x / 2 + 200 && event.getY() < size.y / 2 + 425) {
                        super.restart = true;
                    }
                    //esci
                    if (event.getX() > size.x / 2 - 200 && event.getY() > size.y / 2 + 600 && event.getX() < size.x / 2 + 200 && event.getY() < size.y / 2 + 950) {
                        super.esci = true;
                    }
                }
                if (restart) {
                    //si
                    if (event.getX() > size.x / 2 - 320 && event.getY() > size.y / 2 + 100 && event.getX() <size.x / 2 - 70  && event.getY() < size.y / 2 + 275 ) {
                        super.pausaCovid =false;
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
                    if (event.getX() > size.x/2 -50 && event.getY() > size.y/2 + 500 && event.getX() < size.x/2 + 50 && event.getY() < size.y/2 +750) {
                        super.pausaCovid = false;
                        super.help = false;
                    }
                }

                break;
        }
        return true;
    }
}