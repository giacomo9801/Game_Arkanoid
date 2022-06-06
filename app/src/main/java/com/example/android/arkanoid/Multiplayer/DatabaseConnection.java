package com.example.android.arkanoid.Multiplayer;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseConnection {

    private FirebaseDatabase db;
    public static String userStr = "users";
    static String emailStr = "email";
    static String nomeStr = "username";
    static String nGameStr = "nGame";
    static String bestScoreStr = "bestscore";
    public static String highscoresStr = "highscores";
    public static String scoreStr = "score";
    public static String usernameStr = "username";

    public DatabaseConnection(FirebaseDatabase db) {
        this.db = db;
    }

    //registra l'utente sul server firebase (login con google)
    public void registerUser(FirebaseAuth fba, GoogleSignInAccount acc) {
        DatabaseReference myRef = db.getReference(userStr).child(fba.getUid()); //creo un riferimento al ramo che contiene gli id e i dati degli utenti sul database
        myRef.child(emailStr).setValue(acc.getEmail()); //salvo l'email sul server
        myRef.child(nomeStr).setValue(acc.getDisplayName()); //salvo il nome dell'account google sul server

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            //controllo se è il primo login
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(nGameStr).getValue(int.class) == null) { //se non esiste il campo nGame lo creo e gli assegno 0
                    myRef.child(nGameStr).setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //salvo il punteggio più alto dell'account sul server
    public void saveScore(FirebaseAuth fba, int score) {
        DatabaseReference myRef = db.getReference(userStr).child(fba.getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int serverScore;
                int nGame = snapshot.child(nGameStr).getValue(int.class);

                nGame++;
                myRef.child(nGameStr).setValue(nGame);
                if(snapshot.child(bestScoreStr).getValue(int.class) != null) {
                    serverScore = snapshot.child(bestScoreStr).getValue(int.class);

                    if (score > serverScore) {
                        myRef.child(bestScoreStr).setValue(score);
                    }
                }
                else {
                    myRef.child(bestScoreStr).setValue(score);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //controllo se il punteggio ottenuto rientra nella top10
    public void checkScore(int score, String user) {
        DatabaseReference highscoresRef = db.getReference(highscoresStr);

        highscoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int srvrScore;
                boolean flag = true;

                int x = 1;
                do {
                    srvrScore = snapshot.child(String.valueOf(x)).child(scoreStr).getValue(int.class); //recupero i punteggi della top10 dal server
                    if (score > srvrScore) {
                        pushScore(score, user, highscoresRef, x);
                        flag = false;
                    }

                    x++;
                } while(x <= 10 && flag);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //inserisco il punteggio ottenuto nella top10
    private void pushScore(int myScore, String myUser, DatabaseReference dbr, int pos) {
        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(pos != 10){
                    for(int i = 10; i > pos; i--) {
                        int nextPos = i - 1;

                        String user = snapshot.child(String.valueOf(nextPos)).child(usernameStr).getValue(String.class);
                        int score = snapshot.child(String.valueOf(nextPos)).child(scoreStr).getValue(int.class);

                        dbr.child(String.valueOf(i)).child(usernameStr).setValue(user);
                        dbr.child(String.valueOf(i)).child(scoreStr).setValue(score);
                    }

                    dbr.child(String.valueOf(pos)).child(usernameStr).setValue(myUser);
                    dbr.child(String.valueOf(pos)).child(scoreStr).setValue(myScore);
                }
                else {
                    dbr.child(String.valueOf(pos)).child(scoreStr).setValue(myScore);
                    dbr.child(String.valueOf(pos)).child(usernameStr).setValue(myUser);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
