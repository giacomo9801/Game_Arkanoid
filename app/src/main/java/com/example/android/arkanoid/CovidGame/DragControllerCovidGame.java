package com.example.android.arkanoid.CovidGame;

import android.content.Context;
import android.view.MotionEvent;

public class DragControllerCovidGame extends AbstractCovidGame {



    public DragControllerCovidGame(Context context, int lifes, int score) {
        super(context, lifes, score);


    }

    public void stopTiro() {
    }

    public void correreTiro() {
    }


    //gestione funzione drag del paddle
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
                if (!super.pausaCovid && !gameOver) {
                    if (event.getX() > super.size.x - ((infermiere.getDim() / 2))) {
                        super.infermiere.setX(super.size.x - ((infermiere.getDim() / 2)));
                    } else if (event.getX() < (infermiere.getDim() / 2)) {
                        super.infermiere.setX((infermiere.getDim() / 2));
                    } else {
                        super.infermiere.setX((event.getX()));
                    }
                }
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