package com.example.android.arkanoid.CovidGame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.arkanoid.Game.Threads.UpdateThread;
import com.example.android.arkanoid.MenuActivity;
import com.example.android.arkanoid.R;
import com.example.android.arkanoid.Settings.Settings;


public class MainActivityCovid extends AppCompatActivity  {
    private AbstractCovidGame game;
    private UpdateThread myThread;
    private Handler updateHandler;
    protected static MediaPlayer music_game, sneeze_sound, sound_gameover;
    private boolean musicischeck;
    private boolean audiofx;
    Settings s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        music_game = MediaPlayer.create(this, R.raw.game_music);
        sound_gameover = MediaPlayer.create(this, R.raw.game_over);
        sneeze_sound = MediaPlayer.create(this,R.raw.sneeze_sound);

        //imposta l'orientamento dello schermo
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent i = this.getIntent();
        SharedPreferences settings = getSharedPreferences(Settings.FILENAME, Context.MODE_PRIVATE);
        s = new Settings();
        
        //creare un nuovo gioco
        game = new DragControllerCovidGame(this, 3, 0);
        setContentView(game);

        musicischeck = s.loadMusic(settings);

        if(musicischeck) {
            //Toast.makeText(this, "Musica attiva", Toast.LENGTH_SHORT).show();

        } else {
            music_game.stop();
            //Toast.makeText(this, "Musica disattiva", Toast.LENGTH_SHORT).show();
        }
        audiofx = s.loadAudioFx(settings);

        if(audiofx){
            //Toast.makeText(this, "audiofx attivo", Toast.LENGTH_SHORT).show();

        } else {
            sneeze_sound.stop();
            sound_gameover.stop();
        }
        
        
        //creare un handler e thread
        creaHandler();
        myThread = new UpdateThread(updateHandler);
        myThread.start();
    }


    private void creaHandler() {
        updateHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (!game.pausaCovid){
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

    protected void onPause() {
        super.onPause();
        music_game.pause();
        game.stopTiro();
    }

    protected void onResume() {
        super.onResume();
        music_game.start();
        game.correreTiro();
    }
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.ritorno_al_menu)
                .setMessage(R.string.ritorno_al_menu_principale)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivityCovid.super.onBackPressed();
                        sneeze_sound.stop();
                        music_game.stop();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

}
