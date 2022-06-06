package com.example.android.arkanoid.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.arkanoid.Multiplayer.DatabaseConnection;
import com.example.android.arkanoid.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings extends AppCompatActivity {
    //variabili necessarie a effettuare il login, relativi pulsanti e gestione parametri smartphone per l invio di bug
    private static final String TAG = "";
    private static final int RC_SIGN_IN = 123;
    private boolean landscape;
    private boolean music;
    private boolean audioFx;
    private boolean logged = false;
    private int gameMode;
    private GoogleSignInAccount signInAccount;
    private FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private SwitchMaterial switch_landscape = null;
    private SwitchMaterial switch_music = null;
    private SwitchMaterial switch_audiofx = null;
    private Spinner spinner = null;
    private TextView email, infolog;
    private Button logout;
    private SignInButton login;
    private RatingBar ratingBar;
    private TextView textratingapp;
    public static final String FILENAME = "arkndsttings";
    String stringmodel = android.os.Build.MODEL;
    String stringdevice = android.os.Build.DEVICE;
    String stringbuild = System.getProperty("os.version");
    String stringproduct = android.os.Build.PRODUCT;
    Intent i;

    public Settings() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.google_signIn);
        logout = findViewById(R.id.signout);
        email = findViewById(R.id.emailutentesettings);
        infolog = findViewById(R.id.textaccedifunzionalita);
        ratingBar = findViewById(R.id.ratingBar);
        textratingapp = findViewById(R.id.textViewRateBar);

        signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        //verifichiamo che il login sia diverso da null, quindi se l'utente è loggato o meno. Nascondiamo e visualizziamo gli elementi in base al caso
        if (signInAccount != null) {
            infolog.setVisibility(View.GONE);
            email.setText(signInAccount.getEmail());
            login.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
            logged = true;
            ratingBar.setVisibility(View.VISIBLE);
            textratingapp.setVisibility(View.VISIBLE);
            DatabaseConnection db = new DatabaseConnection(fdb);
            db.registerUser(mAuth, signInAccount);
            testRatingBar();
        }

        //utente non loggato
        else {
            infolog.setVisibility(View.VISIBLE);
            email.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
            logged = false;
            textratingapp.setVisibility(View.GONE);
            ratingBar.setVisibility(View.GONE);
        }

        //gestisce il logout dell'account google
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                signOut();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        createRequest();
        findViewById(R.id.google_signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        i = this.getIntent();

        switch_landscape = findViewById(R.id.switch_land);
        switch_music = findViewById(R.id.switch_music);
        switch_audiofx = findViewById(R.id.switch_effectsound);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gamemode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //salviamo e verifichiamo i paramentri utilizzati negli switch e nella mod di gioco
        SharedPreferences settings = getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        landscape = settings.getBoolean("landscape", false);
        switch_landscape.setChecked(landscape);

        music = settings.getBoolean("music", true);
        switch_music.setChecked(music);

        audioFx = settings.getBoolean("audiofx", true);
        switch_audiofx.setChecked(audioFx);

        gameMode = settings.getInt("gamemode", 0);
        spinner.setSelection(gameMode);

        SharedPreferences userloggedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userloggedpreferences.edit().putBoolean("userLogged", logged).apply(); // islogin is a boolean value of your login status
        //Funzione per inviare feedback, menù chooser per scelta client email
        //vengono prelevate informazioni standard di ogni dispositivo
        Button feedbackbtn = (Button) findViewById(R.id.Feedback);
        feedbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedbackEmail = new Intent(Intent.ACTION_SEND);
                feedbackEmail.setType("text/email");
                feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"arkanoidboolean@gmail.com"});//qui inseriamo l'email del nostro account
                feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "Segnalazione Bug ");
                feedbackEmail.putExtra(Intent.EXTRA_TEXT, "Informazioni Device " + "\n" + stringbuild + "\n" + stringdevice + "\n" + stringmodel + "\n" + stringproduct);
                startActivity(Intent.createChooser(feedbackEmail, "Invia un feedback:"));
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void signOut() {
        signInAccount = null;
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //gestiamo la connessione a firebase quando avviene il login con google
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            logged = true;
                            finish();
                            startActivity(getIntent());
                        } else {
                            logged = false;
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        //i parametri vengono inviati con il putextra all'activity quando viene premuto il tasto indietro
        setLandscape(switch_landscape.isChecked());
        setMusic(switch_music.isChecked());
        setAudioFx(switch_audiofx.isChecked());
        setGameMode(spinner.getSelectedItemPosition());

        //verifichiamo ogni cambiamento di stato
        onCheckedChanged(gameMode);
        onCheckedChanged(landscape, "landscape");
        onCheckedChanged(music, "music");
        onCheckedChanged(audioFx, "audiofx");

        i.putExtra("landscape", landscape);
        i.putExtra("music", music);
        i.putExtra("audiofx", audioFx);
        i.putExtra("gamemode", gameMode);


        super.onBackPressed();
    }

    //salvo il valore
    public void onCheckedChanged(boolean isChecked, String str) {
        SharedPreferences settings = getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(str, isChecked);
        editor.apply();
    }

    public void onCheckedChanged(int gamemode) {
        SharedPreferences settings = getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("gamemode", gamemode);
        editor.apply();
    }

    //invio i dati in tempo reale al db riguardo la valutazione dell'utente
    private void testRatingBar() {
        final DatabaseReference ref = fdb.getReference("ratingbar").child(mAuth.getUid()).child("rating");
        ref.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot != null && snapshot.getValue() != null) {
                    float rating = Float.parseFloat(snapshot.getValue().toString());
                    ratingBar.setRating(rating);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) ref.setValue(rating);
            }
        });
    }

    public boolean isLandscape() {
        return landscape;
    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public boolean isAudioFx() {
        return audioFx;
    }

    public void setAudioFx(boolean audioFx) {
        this.audioFx = audioFx;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public int loadGameMode(SharedPreferences sp) {
        int gamemode = 0;
        gamemode = sp.getInt("gamemode", 0);
        return gamemode;
    }

    public boolean loadMusic(SharedPreferences sp) {
        boolean musicmusic;
        musicmusic = sp.getBoolean("music", true);
        return musicmusic;
    }

    public boolean loadAudioFx(SharedPreferences sp) {
        boolean audiofx;
        audiofx = sp.getBoolean("audiofx", true);
        return audiofx;
    }
}