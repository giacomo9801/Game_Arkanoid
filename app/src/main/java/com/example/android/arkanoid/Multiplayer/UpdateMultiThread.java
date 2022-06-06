package com.example.android.arkanoid.Multiplayer;

import android.os.Handler;

public class UpdateMultiThread extends Thread {
    Handler updatedHandler;
    private long milliseconds = 35; //tempo di ripetizione del thread

    public UpdateMultiThread(Handler uh) {
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
