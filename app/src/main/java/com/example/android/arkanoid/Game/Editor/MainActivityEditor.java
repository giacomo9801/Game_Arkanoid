package com.example.android.arkanoid.Game.Editor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.android.arkanoid.Game.Editor.EditorGame;
import com.example.android.arkanoid.Game.Threads.UpdateThread;
import com.example.android.arkanoid.Settings.Settings;

import com.example.android.arkanoid.Game.Threads.UpdateThread;
import com.example.android.arkanoid.Settings.Settings;

import java.util.Objects;

public class MainActivityEditor extends AppCompatActivity {

    private EditorGame game;
    private UpdateThread myThread;
    private Handler updateHandler;
    private boolean landscape;
    private final static String preferen_life = "pref_lif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences(Settings.FILENAME, Context.MODE_PRIVATE);

        SharedPreferences readlifeshare = getSharedPreferences(preferen_life, Context.MODE_PRIVATE);
        int read_life = readlifeshare.getInt("progress_life", 1);

        //imposta l'orientamento dello schermo
        landscape = settings.getBoolean("landscape", false);

        if (!landscape) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }


        game = new EditorGame(this, read_life, 0);
        setContentView(game);

        //creare un handler e thread
        creaHandler();
        myThread = new UpdateThread(updateHandler);
        myThread.start();


    }

    private void creaHandler() {
        updateHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (!game.pausa) {
                    game.invalidate();
                    game.update();
                }
                if (game.esci) {
                    game.esci = false;
                    onBackPressed();
                }
                super.handleMessage(msg);
            }
        };
    }


}