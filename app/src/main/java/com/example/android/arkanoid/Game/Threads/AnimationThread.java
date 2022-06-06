package com.example.android.arkanoid.Game.Threads;

import android.os.Handler;

public class AnimationThread extends Thread {
    Handler animationHandler;

    public AnimationThread(Handler uh) {
        super();
        animationHandler = uh;
    }

    public void run() {
        while (true) {
            try {
                sleep(110);
            } catch (Exception ex) {
            }
            animationHandler.sendEmptyMessage(0);
        }
    }
}
