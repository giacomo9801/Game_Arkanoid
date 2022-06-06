package com.example.android.arkanoid.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.arkanoid.Multiplayer.DatabaseConnection;
import com.example.android.arkanoid.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference().child(DatabaseConnection.highscoresStr);
    private TextView nomeutente, highscore, partitegiocate;
    private GoogleSignInAccount signInAccount;
    private FirebaseAuth mAuth;
    private Button sharebtn;
    private TextView userTv;
    private TextView scoreTv;
    private int scoretoshare = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mAuth = FirebaseAuth.getInstance();
        nomeutente = findViewById(R.id.setnomeutente_profile);
        highscore = findViewById(R.id.sethighscore_profile);
        partitegiocate = findViewById(R.id.setngame_profile);
        sharebtn = findViewById(R.id.sharescore);

        //setto l immagine di avatar
        ImageView imageavatar = findViewById(R.id.avatarprofile);
        int imageResource = getResources().getIdentifier("@drawable/avatar_android", null, this.getPackageName());
        imageavatar.setImageResource(imageResource);

        signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        //prendo le info del giocatore dal db e le visualizzo
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nome = snapshot.child("username").getValue().toString();
                nomeutente.setText(nome);

                if (snapshot.child("bestscore").getValue() == null) {
                    highscore.setText("0");
                } else {
                    String score = snapshot.child("bestscore").getValue().toString();
                    highscore.setText(score);
                    scoretoshare = Integer.parseInt(score);
                }

                String ngame = snapshot.child("nGame").getValue().toString();
                partitegiocate.setText(ngame);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        getHighscoresData();

        //funzione per inviare lo score su whatsapp
        //viene fatto un controllo sul package per verificare se è installato o meno
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentwhatsapp = new Intent(Intent.ACTION_SEND);
                intentwhatsapp.setType("text/plain");
                intentwhatsapp.setPackage("com.whatsapp");
                intentwhatsapp.putExtra(Intent.EXTRA_TEXT, "Ciao! ecco lo score che ho totalizzato su Arkanoid " + scoretoshare + ". Riesci a battermi? :D");
                try {
                    startActivity(intentwhatsapp);
                } catch (android.content.ActivityNotFoundException ex) {
                    ex.printStackTrace();
                    Toast.makeText(UserActivity.this, "Whatsapp non è installato", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //legge dal firebase lo score del giocatore e stila una classifica di 10 giocatori
    public void getHighscoresData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int x = 1; x <= 10; x++) {
                    String s;
                    s = snapshot.child(String.valueOf(x)).child(DatabaseConnection.usernameStr).getValue(String.class);
                    userTv = getUserTextView(x);
                    userTv.setText(s);
                    s = String.valueOf(snapshot.child(String.valueOf(x)).child(DatabaseConnection.scoreStr).getValue(int.class));
                    scoreTv = getPtTextView(x);
                    scoreTv.setText(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //funzione per identificare le textview utenti nella classifica
    private TextView getUserTextView(int x) {
        TextView tv = null;

        switch (x) {
            case 1:
                tv = findViewById(R.id.first);
                break;
            case 2:
                tv = findViewById(R.id.second);
                break;
            case 3:
                tv = findViewById(R.id.third);
                break;
            case 4:
                tv = findViewById(R.id.fourth);
                break;
            case 5:
                tv = findViewById(R.id.fifth);
                break;
            case 6:
                tv = findViewById(R.id.sixth);
                break;
            case 7:
                tv = findViewById(R.id.seventh);
                break;
            case 8:
                tv = findViewById(R.id.eighth);
                break;
            case 9:
                tv = findViewById(R.id.nineth);
                break;
            case 10:
                tv = findViewById(R.id.tenth);
                break;
        }

        return tv;
    }

    //funzione per identificare le textview punteggi nella classifica
    private TextView getPtTextView(int x) {
        TextView tv = null;

        switch (x) {
            case 1:
                tv = findViewById(R.id.firstpt);
                break;
            case 2:
                tv = findViewById(R.id.secondpt);
                break;
            case 3:
                tv = findViewById(R.id.thirdpt);
                break;
            case 4:
                tv = findViewById(R.id.fourthpt);
                break;
            case 5:
                tv = findViewById(R.id.fifthpt);
                break;
            case 6:
                tv = findViewById(R.id.sixthpt);
                break;
            case 7:
                tv = findViewById(R.id.seventhpt);
                break;
            case 8:
                tv = findViewById(R.id.eighthpt);
                break;
            case 9:
                tv = findViewById(R.id.ninethpt);
                break;
            case 10:
                tv = findViewById(R.id.tenthpt);
                break;
        }

        return tv;
    }
}
