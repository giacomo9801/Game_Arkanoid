package com.example.android.arkanoid.Game.Threads;

import android.os.Handler;

public class RocketThread extends Thread {
    Handler rocketHandler;
    private int milliseconds =1500;

    public RocketThread(Handler uh) {
        super();
        rocketHandler = uh;
    }

    public void run() {
        while (true) {
            try {
                sleep(milliseconds);
            } catch (Exception ex) {
            }
            rocketHandler.sendEmptyMessage(0);
        }
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(int milliseconds) {
        this.milliseconds = milliseconds;
    }
}

