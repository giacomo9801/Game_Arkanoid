package com.example.android.arkanoid.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.MotionEvent;

import com.example.android.arkanoid.Settings.Settings;

public class DragControllerGame extends AbstractGame {

    private Costanti cost;
    private boolean landscape; // variabile per landscape
    SharedPreferences settings; // serve per caricare il valore del landscape

    public DragControllerGame(Context context, int lifes, int score) {
        super(context, lifes, score);

        settings = context.getSharedPreferences(Settings.FILENAME, Context.MODE_PRIVATE);
        landscape = settings.getBoolean("landscape",false);
        cost = new Costanti();

        if(!landscape){
            cost.setValoreDragLeft(26);
            cost.setValoreDragRight(26);//setta le variabili per portraint o landscape
        }else{
            cost.setValoreDragLeft(280);
            cost.setValoreDragRight(260);
        }


    }

    public void stopTiro() {
    }

    public void correreTiro() {
    }


    //gestione funzione drag del paddle
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isSystem_ready()) { //se l'animazione del paddle è terminata, si può startare
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
                    if (!super.pausa) {
                        if (event.getX() > super.size.x - ((paddle.getDim() / 2) + cost.getValoreDragLeft())) {
                            super.paddle.setX(super.size.x - ((paddle.getDim() / 2) + cost.getValoreDragLeft()));
                        } else if (event.getX() < cost.getValoreDragRight() + (paddle.getDim() / 2)) {
                            super.paddle.setX(cost.getValoreDragRight() + (paddle.getDim() / 2));
                        } else {
                            super.paddle.setX((event.getX()));
                        }
                    }

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

