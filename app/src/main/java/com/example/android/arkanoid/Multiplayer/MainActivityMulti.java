package com.example.android.arkanoid.Multiplayer;

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

import com.example.android.arkanoid.R;
import com.example.android.arkanoid.Settings.Settings;

public class MainActivityMulti extends AppCompatActivity {
    private MultiGame game;
    private UpdateMultiThread myThread;
    private Handler updateHandler;
    protected static MediaPlayer music_game, sound_brick, sound_rocket, sound_gameover;
    Settings s;
    private boolean musicischeck;
    private boolean audiofx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        music_game = MediaPlayer.create(this, R.raw.game_music); //musica
        sound_gameover = MediaPlayer.create(this, R.raw.game_over);

        Intent i = this.getIntent();
        SharedPreferences settings = getSharedPreferences(Settings.FILENAME, Context.MODE_PRIVATE);
        s = new Settings();
        String key = i.getStringExtra("key"); //recupera la chiave della stanza
        int plmode = i.getIntExtra("plmode", 1); //recupera il numero del player

        //imposta l'orientamento dello schermo
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //setta l'orientamento solo su portrait

        game = new MultiGame(this, key, plmode); //istanzia il game con i dati del caso

        setContentView(game);
        //creare un handler e thread
        creaHandler();
        myThread = new UpdateMultiThread(updateHandler);
        myThread.start();

        musicischeck = s.loadMusic(settings); //recupera le impostazioni sulla musica
        if(musicischeck) {
            //Toast.makeText(this, "Musica attiva", Toast.LENGTH_SHORT).show();
        }
        else {
            music_game.stop();
            //Toast.makeText(this, "Musica disattiva", Toast.LENGTH_SHORT).show();
        }
        audiofx = s.loadAudioFx(settings); //recupera le impostazione sugli effetti audio

        if(audiofx){
            //Toast.makeText(this, "audiofx attivo", Toast.LENGTH_SHORT).show();
        }
        else {
            sound_gameover.stop();
        }
    }


    private void creaHandler() {
        updateHandler = new Handler() {
            public void handleMessage(Message msg) {
                game.update();
                super.handleMessage(msg);
            }
        };
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this) //crea l'alert
                .setTitle("Ritorno al menù")
                .setMessage("Sei sicuro di voler tornare al menù principale?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        music_game.stop();
                        MainActivityMulti.super.onBackPressed();
                        game.forceRoomRemoval(); //rimuove in modo forzato la stanza su firebase
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}

