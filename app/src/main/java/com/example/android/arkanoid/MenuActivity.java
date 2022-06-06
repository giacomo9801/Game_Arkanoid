package com.example.android.arkanoid;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.arkanoid.CovidGame.MainActivityCovid;
import com.example.android.arkanoid.Game.Editor.Editor;
import com.example.android.arkanoid.Game.MainActivity;
import com.example.android.arkanoid.Multiplayer.MultiplayerMode;
import com.example.android.arkanoid.Settings.Settings;
import com.example.android.arkanoid.Settings.UserActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.net.InetAddress;
import java.util.Locale;


public class MenuActivity extends AppCompatActivity {
    private int gamemode = 0;
    private boolean musicischeck;
    Settings s;
    MediaPlayer menuMusic;
    boolean userLogged;
    private Button profile;
    private Button infobtnsettings;
    SharedPreferences userloggedpreferences;
    private FirebaseAuth mAuth;
    private Dialog MyDialog;
    private int playercurrentPosition;

    private final static int MAX_VOLUME = 100; //creiamo una scala per il volume di 100 step
    //impostiamo volume su un qualsiasi intervallo tra 0 e 100. Per modificare basta cambiare il "20" con qualsiasi altro valore. Più il valore è basso , minore sarà l'audio
    final float volume_mid = (float) (1 - (Math.log(MAX_VOLUME - 20) / Math.log(MAX_VOLUME)));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.d("Language:  " , Locale.getDefault().getLanguage());//controllo lingua

        //controlla il cambiamento di stato e legge la posizione corrente della canzone
        if(savedInstanceState != null){
            playercurrentPosition = savedInstanceState.getInt("currentpos_menu");
        }

        menuMusic = MediaPlayer.create(getApplicationContext(), R.raw.menu_music);
        menuMusic.seekTo(playercurrentPosition); //prendo l'ultima posizione corrente
        menuMusic.setLooping(true);
        menuMusic.setVolume(volume_mid, volume_mid);

        checkAllConnection();
        profile = (Button) findViewById(R.id.Profile);

        // prendo il valore di stato del login
        userloggedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userLogged = userloggedpreferences.getBoolean("userLogged", false);


        if(userLogged) { //controlla se l'utente è loggato
            mAuth = FirebaseAuth.getInstance();
            profile.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "Utente loggato  " + mAuth.getUid(), Toast.LENGTH_SHORT).show();
            profile.setOnClickListener(new View.OnClickListener() { //listener per il profilo
                @Override
                public void onClick(View v) {
                    Intent intentprofile = new Intent(getApplicationContext(), UserActivity.class);
                    intentprofile.putExtra("sessioneutente", userLogged);
                    startActivity(intentprofile);
                }
            });

            profile.setOnLongClickListener(new View.OnLongClickListener() { //se si mantegono premuti i Button viene restituita una breve descrizione
                @Override
                public boolean onLongClick(View v) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.long_press_profile, Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(getColor(R.color.semi_transparent));
                    snackbar.show();
                    return true;
                }
            });
        }else{
            profile.setVisibility(View.GONE);
            //Toast.makeText(this, "Utente non loggato", Toast.LENGTH_SHORT).show();
        }

        //carica i setteggi passati dal menu impostazioni
        SharedPreferences settings = getSharedPreferences(Settings.FILENAME, Context.MODE_PRIVATE);
        s = new Settings();
        musicischeck = s.loadMusic(settings);

        if(musicischeck){
            //Toast.makeText(this, "Musica attiva", Toast.LENGTH_SHORT).show();

        }else{
            menuMusic.stop();
            //Toast.makeText(this, "Musica disattiva", Toast.LENGTH_SHORT).show();
        }

        Button startGame = (Button) findViewById(R.id.start_game);
        startGame.setOnClickListener(new View.OnClickListener() { //listener per avvia nuova partita
            public void onClick(View v) {
                gamemode = s.loadGameMode(settings);
                openGame(gamemode);
                menuMusic.pause();

            }
        });

        startGame.setOnLongClickListener(new View.OnLongClickListener() { //se si mantegono premuti i Button viene restituita una breve descrizione
            @Override
            public boolean onLongClick(View v) { //se si mantegono premuti i Button viene restituita una breve descrizione
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.long_press_newgame, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(getColor(R.color.semi_transparent));
                snackbar.show();
                return true;
            }
        });

        Button multi = (Button) findViewById(R.id.multiply);
        multi.setOnClickListener(new View.OnClickListener() { //listener per il multiplayer
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MultiplayerMode.class);
                startActivity(intent);
            }
        });

        multi.setOnLongClickListener(new View.OnLongClickListener() { //se si mantegono premuti i Button viene restituita una breve descrizione
            @Override
            public boolean onLongClick(View v) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.long_press_multi, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(getColor(R.color.semi_transparent));
                snackbar.show();
                return true;
            }
        });

        Button editorMode = (Button) findViewById(R.id.editorlv);
        editorMode.setOnClickListener(new View.OnClickListener() { //listener per l'editor
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, Editor.class);
                startActivity(intent);
            }
        });

        editorMode.setOnLongClickListener(new View.OnLongClickListener() { //se si mantegono premuti i Button viene restituita una breve descrizione
            @Override
            public boolean onLongClick(View v) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),R.string.long_press_editor, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(getColor(R.color.semi_transparent));
                snackbar.show();
                return true;
            }
        });

        Button partitaPers = (Button) findViewById(R.id.own_game);
        partitaPers.setOnClickListener(new View.OnClickListener() { //listener per il covid game
            public void onClick(View v) {
                openCovidGame();
                menuMusic.pause();
                // Click event trigger here
            }
        });

       partitaPers.setOnLongClickListener(new View.OnLongClickListener() { //se si mantegono premuti i Button viene restituita una breve descrizione
            @Override
            public boolean onLongClick(View v) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.long_press_virus, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(getColor(R.color.semi_transparent));
                snackbar.show();
                return true;
            }
        });

        Button settingBtn = (Button) findViewById(R.id.impostazioni);
        settingBtn.setOnClickListener(new View.OnClickListener() { //listener per le impostazioni
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, Settings.class);
                startActivity(intent);

            }
        });

        settingBtn.setOnLongClickListener(new View.OnLongClickListener() { //se si mantegono premuti i Button viene restituita una breve descrizione
            @Override
            public boolean onLongClick(View v) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.long_press_option, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(getColor(R.color.semi_transparent));
                snackbar.show();
                return true;
            }
        });


        infobtnsettings = findViewById(R.id.infobtn);
        infobtnsettings.setOnClickListener(new View.OnClickListener() { //listener per le info
            @Override
            public void onClick(View v) {
                openDialogpowerup();
            }
        });

        infobtnsettings.setOnLongClickListener(new View.OnLongClickListener() { //se si mantegono premuti i Button viene restituita una breve descrizione
            @Override
            public boolean onLongClick(View v) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.long_press_info, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(getColor(R.color.semi_transparent));
                snackbar.show();
                return true;
            }
        });
    }

    //salva la posizione corrente della canzone al cambiamento di rotazione
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentpos_menu", menuMusic.getCurrentPosition());
    }



    //apre la finestra della legenda
    private void openDialogpowerup() {
        MyDialog = new Dialog(this);
        MyDialog.setContentView(R.layout.customdialog);
        MyDialog.show();


    }

    //metodo che gestisce il pulsante indietro nel menu' principale
    public void onBackPressed() {
        new AlertDialog.Builder(this) //creo l'alert che chiede conferma
                .setTitle("Uscita dal gioco")
                .setMessage("Sei sicuro di voler uscire?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    //intent per aprire un nuovo gioco
    public void openGame(int gm) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("gamemode", gm);
        startActivity(intent);
    }

    //intent per aprire la mod covid
    public void openCovidGame() {
        Intent intent = new Intent(this, MainActivityCovid.class);
        startActivity(intent);
    }

    //metodo che segnala il cambio di rotazione
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Landscape", Toast.LENGTH_LONG).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "Portrait", Toast.LENGTH_LONG).show();
        }
    }

    //controlla se nonostante ci sia un collegamento ad internet, esso funzioni
    private boolean NetworkConnect() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    //controlla se riesce a collegarsi ad internet
    private boolean InternetConnect() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    //controlla se vi è connessione ad internet
    public boolean checkAllConnection(){
        if(!NetworkConnect() && !InternetConnect()){
            Toast.makeText(this, R.string.internet_connect, Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }


    //mette in pausa l'activity
    @Override
    public void onPause() {
        super.onPause();
        menuMusic.pause();

    }

    //riprende l'activity dall'onpause
    @Override
    protected void onResume() {
        super.onResume();
        menuMusic.start();

    }

    public void onStart() {
        super.onStart();
        menuMusic.start();
    }

    //riprende l'activity dal background
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}


