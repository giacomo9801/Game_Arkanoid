package com.example.android.arkanoid.Game.Threads;

import android.os.Handler;

public class UpdateThread extends Thread {
    Handler updatedHandler;
    private long milliseconds = 28;

    public UpdateThread(Handler uh) {
        super();
        updatedHandler = uh;
    }

    public void run() {
        while (true) {
            try {
                sleep(milliseconds);
            } catch (Exception ex) {
            }
            updatedHandler.sendEmptyMessage(0);
        }
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }
}
