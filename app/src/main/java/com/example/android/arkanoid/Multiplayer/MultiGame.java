package com.example.android.arkanoid.Multiplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.NonNull;

import com.example.android.arkanoid.Game.Threads.AnimationThread;
import com.example.android.arkanoid.Game.Items.Ball;
import com.example.android.arkanoid.Game.Items.Brick;
import com.example.android.arkanoid.Game.Costanti;
import com.example.android.arkanoid.Game.Items.Paddle;
import com.example.android.arkanoid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MultiGame extends View implements View.OnTouchListener, ValueEventListener {
    private Bitmap sfondo;
    private Bitmap edgeSx;
    private Bitmap edgeDx;
    private Bitmap redBall;
    private Bitmap disteso;
    private Bitmap paddle_p;

    private Display display;
    protected Point size;
    protected Point margin;
    protected Point relativeSize;
    private Paint paint;
    private Costanti cost;
    final static int HEIGHT = 1920;
    final static int WIDTH = 1080;

    private String key;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); //recupera la connessione al database di firebase
    private DatabaseReference multiRef = firebaseDatabase.getReference().child(MultiplayerMode.MULTIROOMS); //crea un riferimento al ramo delle stanze
    private DatabaseReference roomsId = firebaseDatabase.getReference().child(MultiplayerMode.ROOMSIDSTR); //crea un riferimento al ramo dove recuperare gli id delle stanze
    private static String XPL1 = "xPaddle1";
    private static String XPL2 = "xPaddle2";
    private static String XBRICK = "xBrick";
    private static String YBRICK = "yBrick";
    private String WIN_STR = getResources().getString(R.string.hai_vinto); //recupera la stringa per la vittoria in base alla lingua
    private String LOSE_STR = getResources().getString(R.string.hai_perso); //recupera la stringa per la sconfitta in base alla lingua
    private String QUIT_STR = getResources().getString(R.string.premi_indietro); //recupera la stringa per dirti come uscire dalla partita
    private String pl2 = getResources().getString(R.string.multipl2); //recupera la stringa in attesa del player 2, successivamente contiene il nome del player
    private String speedTracker;
    private boolean gotname;
    private boolean win;
    private int ballPoss;

    private Ball palla;
    protected Paddle paddle1;
    protected Paddle paddle2;

    private RectF r;

    protected int impactCounter;
    protected int speedCounter;
    protected int level;
    private boolean start;
    private boolean gameOver;
    protected boolean esci;
    private Context context;

    private int barra = R.drawable.paddle;
    private int ball = R.drawable.redball;

    //Settaggio Musica
    //Variabili prelevate dell'activity Main
    MediaPlayer gamemusic = MainActivityMulti.music_game;

    private final static int MAX_VOLUME = 100;
    final float volume_high = (float) (1 - (Math.log(MAX_VOLUME - 40) / Math.log(MAX_VOLUME)));
    final float volume_mid = (float) (1 - (Math.log(MAX_VOLUME - 20) / Math.log(MAX_VOLUME)));
    final float volume_low = (float) (1 - (Math.log(MAX_VOLUME - 5) / Math.log(MAX_VOLUME)));

    private int queryCounter;
    private int plmode;

    //dichiarazione thread e handler dell'animation
    private AnimationThread myAnimationThread;
    private Handler animationHandler;

    public MultiGame(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public MultiGame(Context context) {
        super(context);
    }

    public MultiGame(Context context, String key, int plmode) {
        super(context);

        paint = new Paint();

        cost = new Costanti();
        cost.setCostantiPortraint(); //setta le variabili per portraint o landscape

        //inizializazzione del Animation thread e handler
        creaAnimationHandler();
        myAnimationThread = new AnimationThread(animationHandler);
        creaAnimationHandler();
        myAnimationThread.start();

        //impostare contesto, vite, punteggi e livelli
        this.context = context;
        this.key = key;
        this.win = false;
        this.plmode = plmode;
        this.gotname = false;
        if(plmode == 1) { //verifica se si è il player 1 per il server
            ballPoss = 1;
            multiRef.child(key).child(MultiplayerMode.BALLPOSS).setValue(ballPoss);
        }
        else { //se si è player 2
            closeRoom(); //chiude l'accesso alla stanza
        }

        //inizializza gameover per scoprire se la partita è in piedi e se il giocatore non l'ha persa
        start = false;
        multiRef.child(key).child(MultiplayerMode.START).setValue(start); //setta la variabile sul server start come true per far partire la partita
        gameOver = false;
        queryCounter = 0;
        impactCounter = 0;
        speedCounter = 1;

        leggiSfondo(context);

        //crea una bitmap per la palla e la barra (paddle)
        ball = R.drawable.redball;
        redBall = BitmapFactory.decodeResource(getResources(), ball);
        barra = R.drawable.paddle;
        paddle_p = BitmapFactory.decodeResource(getResources(), barra);

        //crea una nuova palla, una nuova barra (paddle) e un elenco di mattoni
        palla = new Ball(context, size.x / 2, size.y / 2); //setta la posizione della palla

        paddle1 = new Paddle(context, size.x / 2, relativeSize.y - 80, Paddle.STANDARD_DIM); //setta la posizione della barra1
        paddle2 = new Paddle(context, size.x / 2, margin.y + 80, Paddle.STANDARD_DIM); //setta la posizione della barra2


        //cm.lvMultiplayer(context, size, elenco); //Crea i mattoni
        this.setOnTouchListener(this);
        multiRef.addValueEventListener(this);

        //Settaggio musica
        gamemusic.start();
        gamemusic.setLooping(true);
        gamemusic.setVolume(volume_mid, volume_mid);

    }

    //disegna tutto
    @Override
    protected void onDraw(Canvas canvas) {
        //crea uno sfondo solo una volta
        if (disteso == null) {
            disteso = Bitmap.createScaledBitmap(sfondo, size.x, size.y, false);
            edgeSx = Bitmap.createScaledBitmap(edgeSx, cost.getEdgeDxSxWidht(), size.y, false);
            edgeDx = Bitmap.createScaledBitmap(edgeDx, cost.getEdgeDxSxWidht(), size.y, false);
        }

        paint.setAntiAlias(true);

        // disegna sfondo
        paint.setColor(Color.BLACK);
        r = new RectF(margin.x, margin.y, margin.x + WIDTH, margin.y + HEIGHT);
        canvas.drawRect(r, paint);
        canvas.drawBitmap(disteso, 0, 0, paint); //disegna lo sfondo
        canvas.drawBitmap(edgeSx, margin.x, 0, paint); //disegna il bordo sinistro
        canvas.drawBitmap(edgeDx, size.x -  (margin.x + 30) ,  0, paint); //disegna il bordo destro

        //disegnare la palla
        paint.setColor(Color.RED);
        canvas.drawBitmap(redBall, palla.getX() - Ball.RADIUS, palla.getY() - Ball.RADIUS, paint); //disegna la pallina

        //disegna barra (paddle)
        paint.setColor(Color.WHITE);
        r = new RectF(paddle1.getX() - (paddle1.getDim() / 2), paddle1.getY() - (Paddle.HEIGHT / 2), paddle1.getX() + (paddle1.getDim() / 2), paddle1.getY() + (Paddle.HEIGHT / 2));
        canvas.drawBitmap(paddle_p, null, r, paint);
        r = new RectF(paddle2.getX() - (paddle2.getDim() / 2), paddle2.getY() - (Paddle.HEIGHT / 2), paddle2.getX() + (paddle2.getDim() / 2), paddle2.getY() + (Paddle.HEIGHT / 2));
        canvas.drawBitmap(paddle_p, null, r, paint);

        //carica ad ogni ripetizione dispari del thread la posizione dei paddle sul server
        if(queryCounter == 1) {
            if (plmode == 1 && start) {
                float dpx = paddle1.getX();
                uploadPaddlePos(dpx, 1);
                queryCounter = 0;
            } else if (start) {
                float dpx = paddle1.getX();
                uploadPaddlePos(dpx, 2);
                queryCounter = 0;
            }
        }
        else {
            queryCounter++;
        }

        //disegno testo
        paint.setColor(Color.WHITE);
        paint.setTextSize(70);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(pl2, size.x / 2, margin.y + 200, paint);

        //indicatore velocità mentre si gioca
        if(!gameOver) {
            speedTracker = getResources().getString(R.string.speed) + String.valueOf(speedCounter);
            paint.setColor(Color.GRAY);
            paint.setTextSize(70);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(speedTracker, size.x / 2, size.y / 2, paint);
        }
        else { //indicatore velocità a partita finita
            speedTracker = getResources().getString(R.string.speedCounter) + String.valueOf(speedCounter);
            paint.setColor(Color.GRAY);
            paint.setTextSize(70);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(speedTracker, size.x / 2, size.y / 2 - 300, paint);
        }


        //in caso di vittoria scrivo hai vinto
        if (gameOver && win) {
            gamemusic.pause();
            paint.setColor(Color.WHITE);
            paint.setTextSize(120);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(WIN_STR, size.x / 2, size.y / 2, paint);
            paint.setTextSize(50);
            canvas.drawText(QUIT_STR, size.x / 2, (size.y / 2) + 100, paint);
        } //in caso di sconfitta scrivo hai perso
        else if(gameOver) {
            gamemusic.pause();
            paint.setColor(Color.WHITE);
            paint.setTextSize(120);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(LOSE_STR, size.x / 2, size.y / 2, paint);
            paint.setTextSize(50);
            canvas.drawText(QUIT_STR, size.x / 2, (size.y / 2) + 100, paint);
        }
    }

    public void update() {
        if (start) {
            if(palla.impattoBarra(paddle1.getX(), paddle1.getY(), Paddle.STANDARD_DIM)) { //controlla se la palla ha impattato il paddle1
                ballPoss = plmode; //setta la variabile che ricorda chi ha colpito per ultimo la palla col paddle
                multiRef.child(key).child(MultiplayerMode.BALLPOSS).setValue(ballPoss); //setta il valore sul server
                impactCounter++;
            }
            else if(palla.impattoBarra(paddle2.getX(), paddle2.getY(), Paddle.STANDARD_DIM)) { //controlla se la palla ha impattato il paddle2
                impactCounter++;
            }

            //ogni 4 tocchi dei paddle aumento la velocità della palla
            if(impactCounter >= 4) {
                palla.incrementSpeed(); //aumenta la velocità
                impactCounter = 0;
                speedCounter++; //traccia l'aumento di velocità
            }

            controllaBordi(); //controlla se la palla ha colpito un bordo o è andata oltre un paddle

            //se si è il player 1 gestisco e carico sul server la posizione della pallina
            if(plmode == 1) {
                palla.corri();
                multiRef.child(key).child(MultiplayerMode.XBALL).setValue(palla.getX() - margin.x);
                multiRef.child(key).child(MultiplayerMode.YBALL).setValue(palla.getY() - margin.y);
            }
        }
    }

    //in caso di vittoria
    private void vittoria() {
        gameOver = true;
        start = false;
        removeRoom();
        win = true;
    }

    //in caso di sconfitta
    public void gameover() {
        gameOver = true;
        start = false;
        removeRoom();
        win = false;
    }

    //permette di far partire la partita al primo tocco dello schermo da parte del player 2 (setta start: true su firebase)
    //nel caso del player 1 carica la posizione della palla su server dal primo tocco
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(plmode == 1 && !start && !gameOver) {
            multiRef.child(key).child(MultiplayerMode.XBALL).setValue(palla.getX() - margin.x);
            multiRef.child(key).child(MultiplayerMode.YBALL).setValue(palla.getY() - margin.y);
        }
        if(plmode == 2 && !start && !gameOver) {
            multiRef.child(key).child(MultiplayerMode.START).setValue(true);
            start = true;
        }
        return false;
    }

    //impostare lo sfondo
    private void leggiSfondo(Context context) {
        sfondo = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.sfondo_stellato));
        edgeSx = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.edge_left));
        edgeDx = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.edge_right));

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        size = new Point();
        margin = new Point(); //margine da lasciare in caso di schermo più grande di 1920x1080
        relativeSize = new Point(); //calcolo la dimensione relativa (margine + dimensioni predefinite)
        display.getSize(size); //recupero le dimensioni dello schermo
        margin = getMargin(size); //calcolo il margine

        //calcolo le dimensioni relative
        int maxX = margin.x + WIDTH;
        int maxY = margin.y + HEIGHT;
        relativeSize.set(maxX, maxY);
    }

    //crea l'animation Handler
    private void creaAnimationHandler() {
        animationHandler = new Handler() {
            public void handleMessage(Message msg) {
                invalidate();
                super.handleMessage(msg);
            }
        };
    }

    //controlla la posizione della palla in base ai bordi definiti
    private void controllaBordi() {
        if (palla.getX() + palla.getxVelocita() >= relativeSize.x - 38) {
            palla.modificaDirezione("right"); //rimbalzo sul lato destro
        } else if (palla.getX() + palla.getxVelocita() <= margin.x + 38) {
            palla.modificaDirezione("left"); //rimbalzo sul lato sinistro
        } else if (palla.getY() + palla.getyVelocita() <= margin.y + 15) {
            vittoria(); //palla oltre il paddle superiore
        } else if (palla.getY() + palla.getyVelocita() >= relativeSize.y - 15) {
            gameover(); //palla oltre il paddle inferiore
        }

    }

    //gestione dello spostamento del paddle in base al tocco (drag) sullo schemro
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(start) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    if (event.getX() > relativeSize.x - ((paddle1.getDim() / 2) + 26)) { //fa in modo che non vada oltre il limite destro
                        paddle1.setX(relativeSize.x - ((paddle1.getDim() / 2) + 26));
                    } else if (event.getX() < margin.x + 26 + (paddle1.getDim() / 2)) { //fa in modo che non vada oltre il limite sinistro
                        paddle1.setX(margin.x + 26 + (paddle1.getDim() / 2));
                    } else {
                        paddle1.setX((event.getX()));
                    }
                    break;
            }
        }
        return true;
    }

    //carica la posizione del paddle sul server
    public void uploadPaddlePos(float x, int player) {
        if(player == 1) { //se si è player1
            multiRef.child(key).child(XPL1).setValue(x - margin.x);
        }
        else { //se si è player2
            multiRef.child(key).child(XPL2).setValue(x - margin.x);
        }
    }

    //recupera i dati dal server firebase
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        float px = 0;

        try { //ad inizio partita se i dati non vengono creati in tempo si può avere un crash dell'app per NullPointerException
            if (plmode == 1 && start) { //player1
                px = snapshot.child(key).child(XPL2).getValue(float.class); //prende la posizione del paddle2
                px = px + margin.x; //calcola la posizione relativa allo schermo su cui ci si trova
                paddle2.moveToX(getOtherPlayerXPosition(px)); //ottiene la posizione speculare
                pl2 = snapshot.child(key).child(MultiplayerMode.PLAYER2).getValue(String.class); //recupera il nome del player2
                gotname = true;
            } else if (start) { //player2
                px = snapshot.child(key).child(XPL1).getValue(float.class); //prende la posizione del paddle1
                px = px + margin.x; //calcola la posizione relativa allo schermo su cui ci si trova
                paddle2.moveToX(getOtherPlayerXPosition(px)); //ottiene la posizione speculare

                if (!gotname) { //se non si ha il nome prova a ricaricarlo dal server
                    pl2 = snapshot.child(key).child(MultiplayerMode.PLAYER1).getValue(String.class);
                    gotname = true;
                }
            }

            if (start && plmode == 2) { //se si è il player 2 la posizione della palle viene caricata dal server
                float bx = snapshot.child(key).child(MultiplayerMode.XBALL).getValue(float.class);
                palla.setX(getOtherPlayerXPosition(bx + margin.x)); //setta la posizione speculare sull'asse x della palla
                float by = snapshot.child(key).child(MultiplayerMode.YBALL).getValue(float.class);
                palla.setY(getOtherPlayerYPosition(by + margin.y)); //setta la posizione speculare sull'asse y della palla
            }

            if (plmode == 1 && !start) //se non si è ancora avuto lo start = true continua a caricarlo finchè non lo diventa
                start = snapshot.child(key).child(MultiplayerMode.START).getValue(boolean.class);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    public int getOtherPlMode(int plmode) {
        int pl = 0;

        if(plmode == 1)
            pl = 2;
        else {
            pl = 1;
        }

        return pl;
    }

    //ottiene la y speculare
    public float getOtherPlayerXPosition(float x) {
        return size.x - x;
    }

    //ottiene la x speculare
    public float getOtherPlayerYPosition(float y) {
        return size.y - y;
    }

    //chiude l'accesso alla stanza
    private void closeRoom() {
        roomsId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean flag = false;

                //cancello il record dal ramo degli id delle stanze e traslo i record successivi se necessario
                for (int i = 1; i <= MultiplayerMode.size; i++) {
                    String roomkey;
                    String index = String.valueOf(i);
                    String preIndex = String.valueOf(i - 1);

                    if (snapshot.child(index).getValue(String.class) != null) {
                        roomkey = snapshot.child(index).getValue(String.class);

                        if (flag) {
                            roomsId.child(preIndex).setValue(roomkey);
                            roomsId.child(index).setValue("");
                        }

                        if (key.equals(roomkey) && !flag) {
                            flag = true;
                            roomsId.child(index).setValue("");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //rimuove la stanza dal ramo che contiene tutti i dati necessari al funzionamento del gioco
    protected void removeRoom() {
        if(plmode == 1) {
            multiRef.removeEventListener(this);
        }
        else { //il player2 è quello che svolge effettivamente l'eliminazione
            multiRef.removeEventListener(this);
            multiRef.child(key).removeValue();
        }
    }

    //cancella forzatamente la stanza dal ramo che ne contiene tutti i dati necessari al funzionamento
    protected void forceRoomRemoval() {
        multiRef.child(key).removeValue();
        closeRoom();
    }

    //calcola il margine da lasciare per avere la zona giocabile centrata e uguale a 1920x1080
    public Point getMargin(Point size) {
        int deltaH = (size.y - HEIGHT) / 2;
        int deltaW = (size.x - WIDTH) / 2;
        Point res = new Point();
        res.set(deltaW, deltaH);

        return res;
    }

    public Brick generateBrick() {
        float x;
        float y;

        y = (float) Math.random() * 400 + 760;
        x = (float) Math.random() * 880;

        uploadBrickPos(x, y);

        x = x + margin.x;
        y = y + margin.y;

        Brick b = new Brick(context, x, y, 2);

        return b;
    }

    public void uploadBrickPos(float x, float y) {
        multiRef.child(key).child(XBRICK).setValue(x);
        multiRef.child(key).child(YBRICK).setValue(y);
    }

    public String getKey() {
        return key;
    }
}


