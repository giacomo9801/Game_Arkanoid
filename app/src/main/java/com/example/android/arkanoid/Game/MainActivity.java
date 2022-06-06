package com.example.android.arkanoid.Game;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.arkanoid.Game.Threads.UpdateThread;
import com.example.android.arkanoid.MenuActivity;
import com.example.android.arkanoid.R;
import com.example.android.arkanoid.Settings.Settings;


public class MainActivity extends AppCompatActivity {

    private AbstractGame game;
    private UpdateThread myThread;
    private Handler updateHandler;
    Settings s;
    private boolean landscape;
    private boolean musicischeck;
    private boolean audiofx;
    private int gamemode = 0;

    protected static MediaPlayer music_game, sound_brick, sound_rocket, sound_gameover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        music_game = MediaPlayer.create(this, R.raw.game_music);
        sound_brick = MediaPlayer.create(this, R.raw.crack_bricks);
        sound_rocket = MediaPlayer.create(this, R.raw.shoot_rocket);
        sound_gameover = MediaPlayer.create(this, R.raw.game_over);

        Intent i = this.getIntent();
        SharedPreferences settings = getSharedPreferences(Settings.FILENAME, Context.MODE_PRIVATE);
        s = new Settings();

        //imposta l'orientamento dello schermo
        landscape = settings.getBoolean("landscape",false); //recupera le impostazioni sull'orientamento dello schermo

        if(!landscape) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //blocco in portrait il telefono
        }
        else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //blocco in landscape il telefono
        }


        gamemode = i.getIntExtra("gamemode", 0); //recupero le impostazioni scelte per la modalità di gioco per i controlli

        //creare un nuovo gioco
        if(gamemode == 0) //controlli del gioco touch
            game = new DragControllerGame(this, 3, 0);
        else if (gamemode == 1) //controlli del gioco accelerometro
            game = new SensorGame(this, 3, 0);
        else { //default
            game = new DragControllerGame(this, 3, 0);
            gamemode = 1;
        }


        setContentView(game);
        //creare un handler e thread
        creaHandler();
        myThread = new UpdateThread(updateHandler);
        myThread.start();

        musicischeck = s.loadMusic(settings);
        if(musicischeck){
            //Toast.makeText(this, "Musica attiva", Toast.LENGTH_SHORT).show();

        }else{
            music_game.stop();
            //Toast.makeText(this, "Musica disattiva", Toast.LENGTH_SHORT).show();
        }
        audiofx = s.loadAudioFx(settings); //carica le impostazioni sugli effetti audio

        if(audiofx){
            //Toast.makeText(this, "audiofx attivo", Toast.LENGTH_SHORT).show();

        }else{
            sound_brick.stop();
            sound_rocket.stop();
            sound_gameover.stop();
        }


    }


    private void creaHandler() {
        updateHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (!game.pausa){
                    game.invalidate();
                    game.update();
                }
                if (game.esci){
                    game.esci=false;
                    onBackPressed();
                }
                super.handleMessage(msg);
            }
        };
    }

    //mette in pausa il gioco
    protected void onPause() {
        super.onPause();
        music_game.pause();
        game.stopTiro();
    }

    //riprende il gioco dalla pausa
    protected void onResume() {
        super.onResume();
        music_game.start();
        game.correreTiro();
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this) //creo l'alert che chiede conferma
                .setTitle(R.string.ritorno_al_menu)
                .setMessage(R.string.ritorno_al_menu_principale)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        music_game.stop();
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
}
