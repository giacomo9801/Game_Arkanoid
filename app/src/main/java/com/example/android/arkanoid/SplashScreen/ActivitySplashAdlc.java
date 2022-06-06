package com.example.android.arkanoid.SplashScreen;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.android.arkanoid.R;

import java.lang.ref.WeakReference;

public class ActivitySplashAdlc extends Activity {



    private static final String TAG_LOG = SplashScreenActivity.class.getName();

    private static final long MIN_WAIT_INTERVAL = 1500L;
    private static final long MAX_WAIT_INTERVAL = 6000L;
    private static final int GO_AHEAD_WHAT = 1;


    private static final String IS_DONE_KEY = "it.miodominio.apobus.key.IS_DONE_KEY";

    private static final String START_TIME_KEY = "it.miodominio.apobus.key.START_TIME_KEY";

    private long mStartTime = -1L;
    private boolean mIsDone;


    private static class UiHandler extends Handler {
        private WeakReference<ActivitySplashAdlc> mActivityRef;

        public UiHandler (final ActivitySplashAdlc srcActivity) {
            this.mActivityRef = new WeakReference<ActivitySplashAdlc>(srcActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            final ActivitySplashAdlc srcActivity = this.mActivityRef.get();
            if (srcActivity == null) {
                Log.d(TAG_LOG, "Reference to StateSplashActivity lost!");
                return;
            }
            switch (msg.what) {
                case GO_AHEAD_WHAT:
                    long elapsedTime = SystemClock.uptimeMillis() - srcActivity.mStartTime;
                    if (elapsedTime >= MIN_WAIT_INTERVAL && !srcActivity.mIsDone) {
                        srcActivity.mIsDone = true;
                        srcActivity.goAhead();
                    }
                    break;
            }
        }
    }

    private UiHandler mHandler;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_DONE_KEY, mIsDone);
        outState.putLong(START_TIME_KEY, mStartTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.mIsDone = savedInstanceState.getBoolean(IS_DONE_KEY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_adlc);


        if (savedInstanceState != null) {
            this.mStartTime = savedInstanceState.getLong(START_TIME_KEY);
        }

        mHandler = new UiHandler(this);
        final ImageView logoImageView = (ImageView) findViewById(R.id.splash);
        logoImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d(TAG_LOG, "ImageView touched!!");
                long elapsedTime = SystemClock.uptimeMillis() - mStartTime;
                if (elapsedTime >= MIN_WAIT_INTERVAL && !mIsDone) {
                    mIsDone = true;
                    goAhead();
                } else {
                    Log.d(TAG_LOG, "Too much early!");
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mStartTime == -1L) {
            mStartTime = SystemClock.uptimeMillis();
        }


        final Message goAheadMessage = mHandler.obtainMessage(GO_AHEAD_WHAT);
        mHandler.sendMessageAtTime(goAheadMessage, mStartTime + MAX_WAIT_INTERVAL);
        Log.d(TAG_LOG, "Handler message send!");
    }

    private void goAhead() {
        final Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

}