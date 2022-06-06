package com.example.android.arkanoid.CovidGame.Threads;

import android.os.Handler;

public class CovidSmallThread extends Thread {

    Handler covidSmallHandler;

    public CovidSmallThread(Handler uh) {
        super();
        covidSmallHandler = uh;
    }

    public void run() {
        while (true) {
            try {
                sleep(5000);
            } catch (Exception ex) {
            }
            covidSmallHandler.sendEmptyMessage(0);
        }
    }
}
