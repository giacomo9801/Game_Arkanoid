package com.example.android.arkanoid.Multiplayer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.android.arkanoid.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.InetAddress;

public class MultiplayerMode extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); //recupera la connessione al database di firebase
    private DatabaseReference multiRef = firebaseDatabase.getReference().child(MULTIROOMS); //crea un riferimento al ramo delle stanze
    private DatabaseReference roomsRef = firebaseDatabase.getReference().child(ROOMSIDSTR); //crea un riferimento al ramo dove recuperare gli id delle stanze
    final static String MULTIROOMS = "multirooms";
    final static String ROOMSIDSTR = "roomsid";
    final static String PLAYER1 = "player1";
    final static String PLAYER2 = "player2";
    final static String XPADDLE1 = "xPaddle1";
    final static String XPADDLE2 = "xPaddle2";
    final static String BALLPOSS  = "ballPoss";
    final static String XBALL = "xBall";
    final static String YBALL = "yBall";
    final static String START = "start";
    final static String EMPTY_SET = "";

    static int size = 10;

    //private Button playoffline;
    private Button makeroom;
    private TextView countrooms;
    private int maxrooms = 0;
    SharedPreferences userloggedpreferences;
    boolean userLogged;

    private GoogleSignInAccount acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_mode);

        makeroom = (Button) findViewById(R.id.makeroom);
        countrooms = (TextView) findViewById(R.id.contastanze);

        acc = GoogleSignIn.getLastSignedInAccount(getApplicationContext()); //recupera l'account loggato

        countserverrooms(multiRef);

        this.getAllRoom(acc);

        makeroom.setOnClickListener(new View.OnClickListener() {
            //crea la stanza al click
            @Override
            public void onClick(View view) {
                Room r = new Room(acc.getDisplayName(), Room.getRandomRoomId());
                createRoom(r, acc);
                openGame(1, r.getIdRoom());
            }
        });

        userloggedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userLogged = userloggedpreferences.getBoolean("userLogged", false); // get value of last login status

        //controlla se si è loggati e connessi ad internet
        if(userLogged && checkAllConnection()) {
            makeroom.setVisibility(View.VISIBLE);
        }
        else { //in caso di esito negativo non permette di compiere alcuna azione e avvisa l'utente
            makeroom.setVisibility(View.GONE); //cancella il tasto per la creazione della stanza
            countrooms.setVisibility(View.GONE); //cancella il testo che tiene il conto della stanza

            for(int z = 1; z <= 10; z++) { //cancella tutti i tasti per entrare nelle stanze
                getRoomButton(z).setVisibility(View.GONE);
            }

            ConstraintLayout cl = findViewById(R.id.layoutmultiplayer);
            TextView text = new TextView(this);
            text.setTextSize(70);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            text.setY(300);
            text.setText(getString(R.string.Devi_loggarti_per_visualizzare_le_stanze));
            text.setTextColor(getColor(R.color.white));
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams((int)ConstraintLayout.LayoutParams.WRAP_CONTENT,(int) ConstraintLayout.LayoutParams.WRAP_CONTENT);
            text.setTextSize(20);
            text.setGravity(Gravity.CENTER);
            cl.addView(text);
        }

    }

    //accesso ad una stanza
    public void openGame(int player, String key) {
        Intent i = new Intent(this, MainActivityMulti.class);
        i.putExtra("key", key);
        i.putExtra("plmode", player);
        this.startActivity(i);
    }

    //setta il nome del player2 sul ramo della stanza scelta
    public void setRoomPlayerTwo(String id, String pl) {
        multiRef.child(id).child(PLAYER2).setValue(pl);
        multiRef.child(id).child(BALLPOSS).setValue(1);
        Log.d("SONO ENTRATO NELLA FUNZIONE ", " setRoomPlayerTwo ");
    }

    /*public void getAllRoomInfos() {
        multiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String player;

                for(int i = 0; i < keys.size(); i++) {
                    player = snapshot.child(keys.get(i)).child(PLAYER1).getValue(String.class);
                    Room r = new Room(player, keys.get(i));
                    MultiplayerMode.rooms.add(r);
                }

                multiRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    //conto le stanze presenti, indipendemente dalla loro accessibilità
    public void countserverrooms(DatabaseReference multiRef){
        multiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    maxrooms = (int) snapshot.getChildrenCount();
                    int count = maxrooms;
                    Resources res = getResources();
                    String roomsfound = res.getQuantityString(R.plurals.numberOfSRoomsAvailable, count, count);
                    countrooms.setText(roomsfound);
                }
                else {
                    int count = 0;
                    Resources res = getResources();
                    String roomsfound = res.getQuantityString(R.plurals.numberOfSRoomsAvailable, count, count);
                    countrooms.setText(roomsfound);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("SONO ENTRATO NELLA FUNZIONE ", " countserverrooms ");
    }


    //recupero da server tutte le stanze accessibili con le rispettive chiavi di accesso
    public void getAllRoom(GoogleSignInAccount acc) {
        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int c = 0;
                TextView tv = (TextView) findViewById(R.id.norooms);

                for(int i = 1; i <= size; i++) {
                    String key = snapshot.child(String.valueOf(i)).getValue(String.class);

                    if(!key.equals(EMPTY_SET)) {
                        tv.setVisibility(View.GONE);
                        Button b = getRoomButton(i);
                        b.setText(key);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                setRoomPlayerTwo(key, acc.getDisplayName()); //imposta il nome del player2 sul server
                                openGame(2, key); //accede alla stanza con id=key come player2
                            }
                        });
                        c++;
                    }
                    else {
                        Button b = getRoomButton(i);
                        b.setText(EMPTY_SET);
                    }
                }

                if(c == 0)
                    tv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        Log.d("SONO ENTRATO NELLA FUNZIONE ", " getAllRoom ");
    }

    //setta la chiave della stanza nel ramo dove i player2 possono recuperare le chiavi
    private void setIdOnServer(String id, int x) {
        roomsRef.child(String.valueOf(x)).setValue(id);
        Log.d("SONO ENTRATO NELLA FUNZIONE ", " setIdOnServer ");
    }

    //creazione di una nuova stanza
    public void createRoom(Room r, GoogleSignInAccount acc) {

        roomsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean flag = false;
                for(int i = 1; i <= size; i++) { //ciclo per il numero massimo di stanze accessibili nello stesso momento
                    String str = snapshot.child(String.valueOf(i)).getValue(String.class); //recupero chiavi
                    Log.d("CREAZIONE STANZA ", " Ho creato la stanza numero " + i);
                    if(str.equals(EMPTY_SET) && flag == false) { //controlla se vi è uno spazio libero
                        flag = true;
                        setIdOnServer(r.getIdRoom(), i); //setta la chiave della stanza creata sul server
                        Log.d("IF  ", " Sono entrato nell'if ");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference roomRef = multiRef.child(r.getIdRoom()); //creo un riferimento al ramo che conterrà tutti i dati per il funzionamento del gioco nella stanza creata

        roomRef.child(PLAYER1).setValue(acc.getDisplayName()); //salvo il nome del creatore della stanza
        roomRef.child(PLAYER2).setValue(EMPTY_SET); //salvo un record vuoto per il nome del player 2
        roomRef.child(XPADDLE1).setValue(100); //setto 100 come valore di default per la posizione del paddle1
        roomRef.child(XPADDLE2).setValue(100); //setto 100 come valore di default per la posizione del paddle2
        Log.d("SONO ENTRATO NELLA FUNZIONE ", " createRoom ");
    }

    //gestisco i 10 button
    private Button getRoomButton(int x) {
        Button b = null;

        switch(x) {
            default:
                b = (Button) findViewById(R.id.room1);
                break;

            case 1:
                b = (Button) findViewById(R.id.room1);
                break;

               case 2:
                b = (Button) findViewById(R.id.room2);
                break;

            case 3:
                b = (Button) findViewById(R.id.room3);
                break;

            case 4:
                b = (Button) findViewById(R.id.room4);
                break;

            case 5:
                b = (Button) findViewById(R.id.room5);
                break;

            case 6:
                b = (Button) findViewById(R.id.room6);
                break;

            case 7:
                b = (Button) findViewById(R.id.room7);
                break;

            case 8:
                b = (Button) findViewById(R.id.room8);
                break;

            case 9:
                b = (Button) findViewById(R.id.room9);
                break;

            case 10:
                b = (Button) findViewById(R.id.room10);
                break;
        }
        Log.d("SONO ENTRATO NELLA FUNZIONE ", " getRoomButton ");
        return b;

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
}