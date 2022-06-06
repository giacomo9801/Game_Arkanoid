package com.example.android.arkanoid.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


import androidx.core.content.res.ResourcesCompat;

import com.example.android.arkanoid.Multiplayer.DatabaseConnection;
import com.example.android.arkanoid.Game.Items.Ball;
import com.example.android.arkanoid.Game.Items.Brick;
import com.example.android.arkanoid.Game.Items.CreaMattoni;
import com.example.android.arkanoid.Game.Items.Paddle;
import com.example.android.arkanoid.Game.Items.PowerUp;
import com.example.android.arkanoid.Game.Items.Rocket;
import com.example.android.arkanoid.Game.Threads.RocketThread;
import com.example.android.arkanoid.Game.Threads.AnimationThread;
import com.example.android.arkanoid.R;
import com.example.android.arkanoid.Settings.Settings;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;


public abstract class AbstractGame extends View implements View.OnTouchListener {

    private boolean landscape; // variabile per landscape
    SharedPreferences settings; // serve per caricare il valore del landscape


    private Bitmap sfondo;
    private Bitmap edgeTop;
    private Bitmap edgeSx;
    private Bitmap edgeDx;
    private Bitmap redBall;
    private Bitmap disteso;
    private Bitmap paddle_p;
    private Bitmap blackScreen;
    private Bitmap pause;
    private Bitmap help_;
    private Bitmap menuPausa;
    private Bitmap menuRestart;
    private Bitmap gameOver_image;
    private Bitmap iniziaNuova_partita;

    

    protected boolean system_ready; //Serve per far partire il gioco solo dopo l'animazione finale del paddle

    //immagini per i loghi dei counter per i powerup
    private Bitmap counterPup_extend;
    private Bitmap counterPup_laser;
    private Bitmap counterPup_small;
    private Bitmap counterPup_devil;

    private Display display;
    protected Point size;
    private Paint paint;
    private Costanti cost;

    private Ball palla;
    private ArrayList<Brick> elenco; //mattoncini
    protected Paddle paddle;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<Rocket> rocket;// missili

    private RectF r;
    private CreaMattoni cm;

    protected int lifes;
    protected int score;
    protected int level;
    protected boolean start;
    protected boolean gameOver;
    private boolean pUp;
    private boolean upPaddle = false;
    private boolean downPaddle = false;
    private boolean flagRocket;
    private boolean flagAnimation;
    private boolean flagAnimationLaser;
    private boolean flagAnimationLaserInverse;
    private boolean gameOverAnimation;
    private boolean flag_timer_laser = false;
    private boolean flagDevil = false;
    private boolean flagHit;
    protected boolean pausa;
    protected boolean help;
    protected boolean esci;
    protected boolean restart;
    private Context context;



    private RocketThread myRocketThread;
    private Handler rocketHandler;
    private int barra = R.drawable.paddle;
    private int ball = R.drawable.redball;

    //Contatori per i controlli sulla durata dei poweup
    private int contatoreRocket;
    private int contatore_paddle;
    private int contatore_paddle_small;
    private int contatore_devil;

    //timer per i loghi che appariranno sopra ogni volta che si colpisce un poweup
    private int timer_paddle;
    private int timer_laser;
    private int timer_paddle_small;
    private int timer_devil;

    private int contatoreAnimationPaddle;

    //Settaggio Musica
    //Variabili prelevate dell'activity Main
    MediaPlayer gamemusic = MainActivity.music_game;
    MediaPlayer sound_brick = MainActivity.sound_brick;
    MediaPlayer sound_rocket = MainActivity.sound_rocket;
    MediaPlayer sound_gameover = MainActivity.sound_gameover;
    //private MediaPlayer music_game, sound_brick, sound_rocket, sound_gameover;
    private final static int MAX_VOLUME = 100;
    final float volume_high = (float) (1 - (Math.log(MAX_VOLUME - 40) / Math.log(MAX_VOLUME)));
    final float volume_mid = (float) (1 - (Math.log(MAX_VOLUME - 20) / Math.log(MAX_VOLUME)));
    final float volume_low = (float) (1 - (Math.log(MAX_VOLUME - 5) / Math.log(MAX_VOLUME)));

    //dichiarazione thread e handler dell'animation
    private AnimationThread myAnimationThread;
    private Handler animationHandler;

    private DatabaseConnection db;

    public AbstractGame(Context context, int lifes, int score) {
        super(context);

        //caricamento dei valori in base al portraint o landscape
        settings = context.getSharedPreferences(Settings.FILENAME, Context.MODE_PRIVATE);
        landscape = settings.getBoolean("landscape",false);
        cost = new Costanti();
        cm = new CreaMattoni(context); //Istanzia la classe crea Mattoni per i vari livelli

        if(!landscape){
            cost.setCostantiPortraint();//setta le variabili per portraint o landscape
        }
        else {
            cost.setCostantiLandscape();
        }


        paint = new Paint();

        //inizializazzione del Rocket thread e handler
        creaRocketHandler();
        myRocketThread = new RocketThread(rocketHandler);
        creaRocketHandler();
        myRocketThread.start();

        //inizializazzione del Animation thread e handler
        creaAnimationHandler();
        myAnimationThread = new AnimationThread(animationHandler);
        creaAnimationHandler();
        myAnimationThread.start();
        system_ready = false;

        //impostare contesto, vite, punteggi e livelli
        this.context = context;
        this.lifes = lifes;
        this.score = score;

        level = 1;
        timer_paddle = 0;
        timer_laser = 0;
        timer_paddle_small = 0;
        timer_devil = 0;


        //inizializza gameover per scoprire se la partita è in piedi e se il giocatore non l'ha persa
        start = false;
        gameOver = false;
        pUp = false;
        flagRocket = false;
        flagAnimation = false;


        leggiSfondo(context);

        //crea una bitmap per la palla e la barra (paddle)
        ball = R.drawable.redball;
        redBall = BitmapFactory.decodeResource(getResources(), ball);
        barra = R.drawable.paddle_materialize_1;
        paddle_p = BitmapFactory.decodeResource(getResources(), barra);
        contatoreAnimationPaddle = -5;
        flagAnimation = true;

        //loghi dei counter per i powerup
        counterPup_extend = BitmapFactory.decodeResource(getResources(), R.drawable.counter_extend);
        counterPup_laser = BitmapFactory.decodeResource(getResources(), R.drawable.counter_laser);
        counterPup_small = BitmapFactory.decodeResource(getResources(), R.drawable.counter_small);
        counterPup_devil = BitmapFactory.decodeResource(getResources(), R.drawable.counter_devil);

        //gameover
        gameOver_image = BitmapFactory.decodeResource(getResources(),R.drawable.game_over);
        iniziaNuova_partita= BitmapFactory.decodeResource(getResources(),R.drawable.button_nuova_partita);

        //crea una nuova palla, una nuova barra (paddle) e un elenco di mattoni
        palla = new Ball(context,size.x / 2, size.y - cost.getPosPalla()); //setta la posizione della palla
        paddle = new Paddle(context,size.x / 2, size.y - cost.getPosPaddle(), Paddle.STANDARD_DIM); //setta la posizione della barra
        elenco = new ArrayList<Brick>(); //setta i mattoncini
        powerUps = new ArrayList<PowerUp>(); //istanzia i power up
        rocket = new ArrayList<Rocket>(); //istanzia i missili

        cm.createLv(level,context,size,elenco); //Crea i mattoni in base al livello
        this.setOnTouchListener(this);

        //Settaggio musica
        gamemusic.start();
        gamemusic.setLooping(true);
        gamemusic.setVolume(volume_mid, volume_mid);

    }


    //impostare lo sfondo
    private void leggiSfondo(Context context) {
        sfondo = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.sfondo_stellato));
        edgeTop = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.edge_top));
        edgeSx = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.edge_left));
        edgeDx = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.edge_right));
        blackScreen = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.black_screen));
        pause = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_pause));
        help_ = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.aiuto));

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        Log.d("Altezza", String.valueOf(size.y));
        Log.d("Larghezza", String.valueOf(size.x));
    }

    //disegna tutto
    protected void onDraw(Canvas canvas) {
        //crea uno sfondo solo una volta
        if (disteso == null) {
            disteso = Bitmap.createScaledBitmap(sfondo, size.x, size.y, false);
            edgeTop = Bitmap.createScaledBitmap(edgeTop, size.x - cost.getEdgeTopWidht(), cost.getEdgeTopHeight(), false);
            edgeSx = Bitmap.createScaledBitmap(edgeSx, cost.getEdgeDxSxWidht(), size.y, false);
            edgeDx = Bitmap.createScaledBitmap(edgeDx, cost.getEdgeDxSxWidht(), size.y, false);
            blackScreen = Bitmap.createScaledBitmap(blackScreen, size.x + 100, 230, false);
            pause = Bitmap.createScaledBitmap(pause, 80, 80, false);
            help_ = Bitmap.createScaledBitmap(help_, 80, 80, false);
        }

        paint.setAntiAlias(true);

        // disegna sfondo
        canvas.drawBitmap(blackScreen, -20, 0, paint);
        canvas.drawBitmap(disteso, cost.getLeftSfondoStellato(), cost.getTopSfondoStellato(), paint);
        canvas.drawBitmap(edgeTop, cost.getEdgeTopLeft(), cost.getEdgeTopTop(), paint);
        canvas.drawBitmap(edgeSx, cost.getEdgeSxLeft(), cost.getEdgeSxTop(), paint);
        canvas.drawBitmap(edgeDx, size.x - cost.getEdgeDxLeft(), cost.getEdgeDxTop(), paint);
        canvas.drawBitmap(pause, 20, 40, paint);
        canvas.drawBitmap(help_, size.x-100, 40, paint);



        //disegnare la palla
        paint.setColor(Color.RED);
        r = new RectF (palla.getX() - Ball.RADIUS, palla.getY() - Ball.RADIUS, palla.getX() + Ball.RADIUS, palla.getY() + Ball.RADIUS);
        canvas.drawBitmap(redBall, null, r, paint);

        //disegna barra (paddle)
        paint.setColor(Color.WHITE);
        r = new RectF(paddle.getX() - (paddle.getDim() / 2), paddle.getY() - (Paddle.HEIGHT / 2), paddle.getX() + (paddle.getDim() / 2), paddle.getY() + (Paddle.HEIGHT / 2));
        canvas.drawBitmap(paddle_p, null, r, paint);


        //disegnare mattoni
        paint.setColor(Color.GREEN);
        for (int i = 0; i < elenco.size(); i++) {
            Brick b = elenco.get(i);
            r = new RectF(b.getX() - (Brick.WIDTH / 2), b.getY() - (Brick.HEIGHT / 2), b.getX() + (Brick.WIDTH / 2), b.getY() + (Brick.HEIGHT / 2));
            canvas.drawBitmap(b.getBrick(), null, r, paint);
        }

        //disegna powerup
        paint.setColor(Color.BLUE);
        if (pUp) {
            for (int i = 0; i < powerUps.size(); i++) {
                PowerUp p = powerUps.get(i);
                r = new RectF(p.getX() - (PowerUp.DIM / 2), p.getY() - (PowerUp.DIM / 2), p.getX() + (PowerUp.DIM / 2), p.getY() + (PowerUp.DIM / 2));
                canvas.drawBitmap(p.getPowerUp(), null, r, paint);
            }
        }

        //disegna missili
        paint.setColor(Color.BLACK);
        if (flagRocket) {
            for (int i = 0; i < rocket.size(); i++) {
                Rocket ro = rocket.get(i);
                r = new RectF(ro.getX(), ro.getY(), ro.getX() + 20, ro.getY() + 60);
                canvas.drawBitmap(ro.getRocket(), null, r, paint);
            }
        }



        //disegna counter pwup
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);

       if (upPaddle) {
            canvas.drawBitmap(counterPup_extend, cost.getLogExtendX(), cost.getLogExtendY(), paint);
            canvas.drawText(": " + timer_paddle, cost.getTextExtendX(), cost.getTextExtendY(), paint);
        }

        if (flag_timer_laser) {
            canvas.drawBitmap(counterPup_laser, cost.getLogLaserX(), cost.getLogLaserY(), paint);
            canvas.drawText(": " + timer_laser, cost.getTextLaserX(), cost.getTextLaserY(), paint);
        }

       if (downPaddle) {
            canvas.drawBitmap(counterPup_small, cost.getLogSmallX(), cost.getLogSmallY(), paint);
            canvas.drawText(": " + timer_paddle_small, cost.getTextSmallX(), cost.getTextSmallY(), paint);
        }

        if (flagDevil) {
            canvas.drawBitmap(counterPup_devil, cost.getLogDevilX(), cost.getLogDevilY(), paint);
            canvas.drawText(": " + timer_devil, cost.getTextDevilX(), cost.getTextDevilY() , paint);
        }
        //fine disegni counter pwup

        //disegno testo
        paint.setColor(Color.WHITE);
        if(landscape){
            paint.setTextSize(39);
        }else{
            paint.setTextSize(42);
        }
        Typeface typeface = ResourcesCompat.getFont(super.getContext(), R.font.audiowide);
        paint.setTypeface(typeface); //cambia la font

        canvas.drawText(getResources().getString(R.string.livello) +" "+level, cost.getTextLevelX(), cost.getTextLevelY(), paint);
        canvas.drawText(getResources().getString(R.string.vite) +" "+ lifes, cost.getTextLifesX(), cost.getTextLifesY(), paint);
        canvas.drawText(getResources().getString(R.string.punteggio) , cost.getTextScoreX(), cost.getTextScoreY(), paint);
        canvas.drawText("" + score , cost.getScoreX(), cost.getScoreY(), paint);


        //in caso di sconfitta disegno "gameover" e visualizzo quanti punti ho totalizzato
        if (gameOver && !gameOverAnimation) {
            gamemusic.pause();
            paint.setColor(Color.WHITE);
            paint.setTextSize(60);

            savescore(context,score);
            canvas.drawBitmap(gameOver_image, size.x / 4-cost.getGameoverX(), size.y / 2+cost.getGameoverY(), paint);
            canvas.drawText("Punteggio " + readscore(context), size.x / 4+cost.getPunteggioX(), size.y / 5+cost.getPunteggioY(), paint);
            canvas.drawBitmap(iniziaNuova_partita, size.x / 2 -cost.getNuovaPartitaX(), size.y / 2+cost.getNuovaPartitaY(), paint);
        }


        if (pausa && !restart && !help) {
            if(!landscape) {
                menuPausa = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_pausa_portrait));
            }else{
                menuPausa = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_pausa_landscape));
            }
            menuPausa = Bitmap.createScaledBitmap(menuPausa, size.x + 200, size.y + 100, false);
            canvas.drawBitmap(menuPausa, -100, -50, paint);
        }

        if (help) {
            if(!landscape) {
                if(Locale.getDefault().getDisplayLanguage().equals("English")){
                    menuPausa = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.legenda_portrait_en));
                }else {

                    menuPausa = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_help_portrait));
                }
            }else{
                if(Locale.getDefault().getDisplayLanguage().equals("English")){
                    menuPausa = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.legenda_landscape_en));
                }else{
                    menuPausa = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_help_landscape));
                }

            }
            menuPausa = Bitmap.createScaledBitmap(menuPausa,size.x + 200, size.y + 100, false);
            canvas.drawBitmap(menuPausa, -100, -50, paint);
        }
        if (restart) {
            if(!landscape) {
                if(Locale.getDefault().getDisplayLanguage().equals("English")){
                    menuRestart = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_restart_portrait_en));
                }else{
                    menuRestart = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_restart_portrait));
                }

            }else{
                if(Locale.getDefault().getDisplayLanguage().equals("English")){
                    menuRestart = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_restart_landscape_en));
                }else{
                    menuRestart = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_restart_landscape));
                }

            }
            menuRestart = Bitmap.createScaledBitmap(menuRestart,size.x + 200, size.y + 100, false);
            canvas.drawBitmap(menuRestart, -100, -50, paint);
        }
    }

//funzione per salvare lo score associato alla partita in corso
    private void savescore(Context context , int score){
        SharedPreferences prefs = context.getSharedPreferences("score", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("key", score);
        editor.commit();
    }

    //funzione per leggere lo score appena conseguito
    private int readscore (Context context){
        SharedPreferences prefs = context.getSharedPreferences("score", Context.MODE_PRIVATE);
        int score = prefs.getInt("key", 0); //è il valore usato per default
        return score;
    }

    //controllare che la palla non abbia toccato il bordo
    private void controllaBordi() {

        if (palla.getX() + palla.getxVelocita() >= size.x - cost.getCollisioneRight()) {
            palla.modificaDirezione("right");
        } else if (palla.getX() + palla.getxVelocita() <= cost.getCollisioneLeft()) {
            palla.modificaDirezione("left");
        } else if (palla.getY() + palla.getyVelocita() <= cost.getCollisioneUp()) {
            palla.modificaDirezione("up");
        } else if (palla.getY() + palla.getyVelocita() >= size.y - cost.getCollisioneDown()) {
            controllaVite();
        }

    }

    //controlla lo stato del gioco. Se le mie vite o se il gioco è finito.
    protected void controllaVite() {
        if (lifes == 1) {
            gameOver = true;
            flagAnimationLaserInverse = false;
            flagAnimationLaser = false;
            contatoreAnimationPaddle = 0;
            gameOverAnimation = true;
            GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(getContext());
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            FirebaseDatabase fdb = FirebaseDatabase.getInstance();
            db = new DatabaseConnection(fdb);
            if(user != null) {
                db.saveScore(mAuth, score);
                db.checkScore(score, acc.getDisplayName());
            }

            //sound_gameover = MediaPlayer.create(context, R.raw.game_over);
            sound_gameover.start();
            sound_gameover.setVolume(volume_high, volume_high);
            ball = R.drawable.redball;
            redBall = BitmapFactory.decodeResource(getResources(), ball);
            level = 1;
            start = false;
            invalidate();
        } else {
            lifes--;
            //se il paddle è ingrandito o ridotto
            paddle.setDim(Paddle.STANDARD_DIM);
            downPaddle = false;
            upPaddle = false;
            flagDevil = false;
            paddle.setX(size.x / 2);
            //setta pallina
            palla.setX(size.x / 2);
            palla.setY(size.y - cost.getPosPalla());
            paddle.setX(size.x / 2);
            palla.creaVelocita();
            palla.aumentaVelocita(level);
            barra = R.drawable.paddle;
            paddle_p = BitmapFactory.decodeResource(getResources(), barra);

            start = false;
            invalidate();
            timer_paddle = 0;
            timer_laser = 0;
            timer_paddle_small = 0;
            timer_devil = 0;
            flagDevil = false;
            ball = R.drawable.redball;
            redBall = BitmapFactory.decodeResource(getResources(), ball);
            flag_timer_laser = false;
            pUp = false;
            powerUps = new ArrayList<PowerUp>();
            flagRocket = false;
            rocket = new ArrayList<Rocket>();
            flagAnimationLaserInverse=false;
            flagAnimationLaser=false;





        }
    }

    //ogni passaggio controlla se c'è una collisione, una perdita o una vittoria, ecc.
    public void update() {
        if (start) {
            vittoria();
            controllaBordi();
            checkContatori();

            for (int i = 0; i < elenco.size(); i++) {
                Brick b = elenco.get(i);
                if (palla.impattoMattone(b.getX(), b.getY())) {
                    b.setVita(b.getVita() - 1);   //riduce la vita del mattone
                    b.layoutBrickSilver(b.getVita());   //cambia il layout del mattone
                    if (b.getVita() == -1) {
                        elenco.remove(i);
                        // sound_brick = MediaPlayer.create(context, R.raw.crack_bricks);
                        
                        sound_brick.start();
                        sound_brick.setVolume(volume_mid, volume_mid);
                        score = score + 80;
                        pUp = checkPwrUp(b);
                        pUp = !powerUps.isEmpty();
                    }
                }
            }
            palla.corri();
            if (pUp) {
                for (int i = 0; i < powerUps.size(); i++) {
                    PowerUp p = powerUps.get(i);
                    p.fall();
                    if (paddle.impattoPwrUp(p)) {
                        applyPwrUp(p);
                        powerUps.remove(i);
                        if (powerUps.isEmpty()) {
                            pUp = false;
                        }
                    } else if (p.getY() + p.getSpeed() >= size.y - cost.getCollisioneDown()) {
                        powerUps.remove(i);
                        if (powerUps.isEmpty()) {
                            pUp = false;
                        }
                    }
                }
            }
            if (flagRocket) {
                for (int i = 0; i < rocket.size(); i++) {
                    Rocket r = rocket.get(i);
                    r.fall();
                    flagHit = false;
                    for (int j = 0; j < elenco.size(); j++) {
                        Brick b = elenco.get(j);
                        if (b.impattoMissile(r.getX(), r.getY())) {
                            b.setVita(b.getVita() - 1);   //riduce la vita del mattone
                            b.layoutBrickSilver(b.getVita());   //cambia il layout del mattone
                            if (b.getVita() == -1) {
                                elenco.remove(j);
                                score = score + 80;
                                pUp = checkPwrUp(b);
                                pUp = !powerUps.isEmpty();
                            }
                            if(!flagHit)  {
                                rocket.remove(i);
                                flagHit = true;
                            }
                        }
                    }
                    if (r.getY() - r.getSpeed() <= cost.getCollisioneUp()) {
                        rocket.remove(i);
                    }
                }
            }
        }
    }


    //serve a sospendere il gioco in caso di nuovo gioco
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (system_ready) { //Se l'animazione del paddle è finita, si può startare
            if (gameOver == true && start == false && !gameOverAnimation) {
                if (event.getX() > size.x / 2 - cost.getNuovaPartitaX() && event.getY() > size.y / 2+cost.getNuovaPartitaY() && event.getX() < size.x / 2-cost.getNuovaPartitaX()+ 1300 && event.getY() < size.y / 2+cost.getNuovaPartitaY()+300) {
                    score = 0;
                    lifes = 3;
                    resetLevel();
                    gameOver = false;
                }
            } else {
                start = true;
            }
        }

        return false;
    }

    //imposta il gioco per iniziare
    protected void resetLevel() {

        palla.setX(size.x / 2);
        palla.setY(size.y - cost.getPosPalla());
        ball = R.drawable.redball;
        redBall = BitmapFactory.decodeResource(getResources(), ball);
        palla.creaVelocita();

        paddle.setX(size.x / 2);
        upPaddle = false;
        downPaddle = false;
        paddle.setDim(Paddle.STANDARD_DIM);

        elenco = new ArrayList<Brick>();
        powerUps = new ArrayList<PowerUp>();
        rocket = new ArrayList<Rocket>();

        flagRocket = false;
        timer_laser = 0;
        timer_paddle = 0;
        timer_devil = 0;
        flagDevil = false;
        timer_paddle_small = 0;
        flag_timer_laser = false;
        system_ready = false;
        flagAnimationLaserInverse=false;
        flagAnimationLaser=false;

        cm.createLv(level,context,size,elenco);
        gamemusic.start();
        gamemusic.setVolume(volume_mid, volume_mid);
        contatoreAnimationPaddle = 0;
        flagAnimation = true;           //attivazione dell'animazionione paddle
        barra = R.drawable.paddle;
        paddle_p = BitmapFactory.decodeResource(getResources(), barra);
    }

    //scopri se il giocatore ha vinto o meno
    private void vittoria() {
        if (elenco.isEmpty()) {
            system_ready = false;
            ++level;
            resetLevel();
            palla.aumentaVelocita(level);
            start = false;
            invalidate();
        }
    }

    //Fa cadere i vari poweup dai mattoncini
    private boolean checkPwrUp(Brick b) {
        int pwforlevel = 0; //serve per far uscire pup nel livello adatto

        //controlli power up per i vari livelli
        if (level >= 1 && level <= 2) {
            pwforlevel = 2; // laser ed extend
        }

        if (level >= 3 && level <= 5) {
            pwforlevel = 3; // small paddle
        }

        if (level >= 6) {
            pwforlevel = 4; // devil ball
        }
        //fine controlli

        float pwrUpRandomizer = (int) (Math.random() * pwforlevel) + 1;
        boolean flag = false;

       if(!b.isGoldenbrick()){ //se non viene distrutto il mattone dorato

           float probability = (int) (Math.random() * 15) + 1;// per il rate drop

            if (probability >= 1 && probability <= 3) { //rate drop
                powerUps.add(new PowerUp(context, (int) pwrUpRandomizer, b.getX(), b.getY(),1));
                flag = true;
            }
        }
       else // se viene distrutto il mattone dorato
       {
           powerUps.add(new PowerUp(context, 5, b.getX(), b.getY(),1)); //fai cadere il power up della vita
           flag = true;

       }


            return flag;
        }


    //implementa i vari powerup
    private void applyPwrUp(PowerUp p) {
        switch (p.getPowerUpType()) {
            case 1: //aumento dimensione barra (paddle)
                checkPupExtend();
                break;

            case 2:
                flagAnimationLaserInverse=false;
                if (timer_laser == 0){ // fa partire l'animazione solo se viene attivato per la prima volta
                    contatoreAnimationPaddle = 0;
                flagAnimationLaser = true;
                }
                flagRocket = true;//missili
                flag_timer_laser = true;
                contatoreRocket = 0;
                timer_laser = 10;
                break;

            case 5: //vita + 1
                ++lifes;
                break;
            case 3: //diminuisce dimensione barra (paddle)
                checkPdownExtend();
                break;
            case 4:
                checkPdownDevil();
                break;

        }

    }

    //creazione dei missili
    private void checkMissili(Paddle p) {
        if (flagRocket == true && !flagAnimationLaser && !gameOverAnimation) {
            //sound_rocket = MediaPlayer.create(context, R.raw.shoot_rocket); //questa riga è possibile cancellarla ma si sentirà lo sparo ogni 2 razzi altrimenti
            contatoreRocket = contatoreRocket + 1;
            if (contatoreRocket < 11) { //spara fino a 10 razzi
                sound_rocket.start();
                sound_rocket.setVolume(volume_low, volume_low);
                rocket.add(new Rocket(context, p.getX() - (paddle.getDim() / 2), p.getY() + (Paddle.HEIGHT / 2) - 100 ));
                rocket.add(new Rocket(context, p.getX() + (paddle.getDim() / 2 - 20), p.getY() + (Paddle.HEIGHT / 2) - 100));
                timer_laser--;
                if (timer_laser <= 0) {
                    flag_timer_laser = false;
                }
            }
        }
        if (contatoreRocket == 11) { //durata missili
            contatoreAnimationPaddle=0;
            flagAnimationLaserInverse=true;

        }
        if (contatoreRocket >= 16) {//durata missili
            flagRocket = false;
            rocket = new ArrayList<Rocket>();

        }
    }

    //crea l'animazione del paddle
    private void checkAnimationPaddle() {
        if (flagAnimation) {
            if (contatoreAnimationPaddle <= 15) {
                contatoreAnimationPaddle = contatoreAnimationPaddle + 1;
            }
            if (contatoreAnimationPaddle > 15) {
                contatoreAnimationPaddle = 0;
                flagAnimation = false;
            }
            switch (contatoreAnimationPaddle) {
                case 0:
                    barra = R.drawable.paddle_materialize_1;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 1:
                    barra = R.drawable.paddle_materialize_2;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 2:
                    barra = R.drawable.paddle_materialize_3;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 3:
                    barra = R.drawable.paddle_materialize_4;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 4:
                    barra = R.drawable.paddle_materialize_5;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 5:
                    barra = R.drawable.paddle_materialize_6;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 6:
                    barra = R.drawable.paddle_materialize_7;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 7:
                    barra = R.drawable.paddle_materialize_8;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 8:
                    barra = R.drawable.paddle_materialize_9;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 9:
                    barra = R.drawable.paddle_materialize_10;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 10:
                    barra = R.drawable.paddle_materialize_11;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 11:
                    barra = R.drawable.paddle_materialize_12;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 12:
                    barra = R.drawable.paddle_materialize_13;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 13:
                    barra = R.drawable.paddle_materialize_14;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 14:
                    barra = R.drawable.paddle_materialize_15;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 15:
                    barra = R.drawable.paddle;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    flagAnimation = false;
                    system_ready = true; //se l'animazione è finita si può iniziare a giocare
                    break;
            }
        }
            if (gameOverAnimation) {
                system_ready = false;
                if (contatoreAnimationPaddle <= 8) {
                    contatoreAnimationPaddle = contatoreAnimationPaddle + 1;
                }
                if (contatoreAnimationPaddle > 8) {
                    contatoreAnimationPaddle = 0;
                    gameOverAnimation = false;
                }
                switch (contatoreAnimationPaddle) {
                    case 0:
                        barra = R.drawable.paddle_explode_1;
                        paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                        break;
                    case 1:
                        barra = R.drawable.paddle_explode_2;
                        paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                        break;
                    case 2:
                        barra = R.drawable.paddle_explode_3;
                        paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                        break;
                    case 3:
                        barra = R.drawable.paddle_explode_4;
                        paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                        break;
                    case 4:
                        barra = R.drawable.paddle_explode_5;
                        paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                        break;
                    case 5:
                        barra = R.drawable.paddle_explode_6;
                        paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                        break;
                    case 6:
                        barra = R.drawable.paddle_explode_7;
                        paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                        break;
                    case 7:
                        barra = R.drawable.paddle_explode_8;
                        paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                        break;
                    case 8:
                        barra = R.drawable.paddle_explode_9;
                        paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                        gameOverAnimation = false;
                        system_ready = true;
                        break;
                }
            }
        if (flagAnimationLaser && !flagAnimation && !gameOverAnimation && !gameOver ) {
            if (contatoreAnimationPaddle <= 24) {
                contatoreAnimationPaddle = contatoreAnimationPaddle + 1;
            }
            if (contatoreAnimationPaddle > 24) {
                contatoreAnimationPaddle = 0;
                flagAnimationLaser = false;
            }
            switch (contatoreAnimationPaddle) {
                case 0:
                    barra = R.drawable.paddle_laser_1;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 1:
                    barra = R.drawable.paddle_laser_2;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 2:
                    barra = R.drawable.paddle_laser_3;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 3:
                    barra = R.drawable.paddle_laser_4;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 4:
                    barra = R.drawable.paddle_laser_5;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 5:
                    barra = R.drawable.paddle_laser_6;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 6:
                    barra = R.drawable.paddle_laser_7;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 7:
                    barra = R.drawable.paddle_laser_8;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 8:
                    barra = R.drawable.paddle_laser_9;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 9:
                    barra = R.drawable.paddle_laser_10;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 10:
                    barra = R.drawable.paddle_laser_11;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 11:
                    barra = R.drawable.paddle_laser_12;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 12:
                    barra = R.drawable.paddle_laser_13;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 13:
                    barra = R.drawable.paddle_laser_14;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 14:
                    barra = R.drawable.paddle_laser_15;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 15:
                    barra = R.drawable.paddle_laser_16;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 16:
                case 20:
                    barra = R.drawable.paddle_laser_pulsate_1;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 17:
                case 21:
                    barra = R.drawable.paddle_laser_pulsate_2;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 18:
                case 22:
                    barra = R.drawable.paddle_laser_pulsate_3;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 19:
                case 23:
                    barra = R.drawable.paddle_laser_pulsate_4;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 24:
                    barra = R.drawable.paddle_laser;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    flagAnimationLaser = false;
                    break;
            }
        }
        if (flagAnimationLaserInverse && !flagAnimation && !flagAnimationLaser && !gameOverAnimation && !gameOver) {
            if (contatoreAnimationPaddle <= 20) {
                contatoreAnimationPaddle = contatoreAnimationPaddle + 1;
            }
            if (contatoreAnimationPaddle > 20) {
                contatoreAnimationPaddle = 0;
                flagAnimationLaserInverse = false;
            }
            switch (contatoreAnimationPaddle) {
                case 0:
                    barra = R.drawable.paddle_laser_pulsate_1;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 1:
                    barra = R.drawable.paddle_laser_pulsate_2;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 2:
                    barra = R.drawable.paddle_laser_pulsate_3;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 3:
                    barra = R.drawable.paddle_laser_pulsate_4;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 4:
                    barra = R.drawable.paddle_laser_16;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 5:
                    barra = R.drawable.paddle_laser_15;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 6:
                    barra = R.drawable.paddle_laser_14;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 7:
                    barra = R.drawable.paddle_laser_13;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 8:
                    barra = R.drawable.paddle_laser_12;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 9:
                    barra = R.drawable.paddle_laser_11;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 10:
                    barra = R.drawable.paddle_laser_10;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 11:
                    barra = R.drawable.paddle_laser_9;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 12:
                    barra = R.drawable.paddle_laser_8;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 13:
                    barra = R.drawable.paddle_laser_7;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 14:
                    barra = R.drawable.paddle_laser_6;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 15:
                    barra = R.drawable.paddle_laser_5;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 16:
                    barra = R.drawable.paddle_laser_4;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 17:
                    barra = R.drawable.paddle_laser_3;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 18:
                    barra = R.drawable.paddle_laser_2;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 19:
                    barra = R.drawable.paddle_laser_1;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    break;
                case 20:
                    barra = R.drawable.paddle;
                    paddle_p = BitmapFactory.decodeResource(getResources(), barra);
                    flagAnimationLaserInverse = false;
                    break;
            }
        }
    }


    //definizione metodi per SensorGame
    public abstract void stopTiro();

    public abstract void correreTiro();

    //crea il RocketHandler
    private void creaRocketHandler() {
        rocketHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (!pausa) {
                    invalidate();
                    checkMissili(paddle);
                }
                super.handleMessage(msg);
            }
        };
    }


    // crea l'animation Handler
    private void creaAnimationHandler() {
        animationHandler = new Handler() {
            public void handleMessage(Message msg) {
                invalidate();
                checkAnimationPaddle();
                super.handleMessage(msg);
            }
        };
    }

    //Controlli vari sul powerup dell'extends
    public void checkPupExtend() {

        contatore_paddle = 0;
        timer_paddle = 5;

        //se il paddle è normale
        if (!upPaddle) {

            //se il paddle è piccolo aumenta il paddle *2 ma annulla tutto
            if (downPaddle) {
                upPaddle = false;
                downPaddle = false;
                paddle.setDim(paddle.getDim() * 2);
            }
            //se il paddle è normale aumenta *2 e fa apparire il timer
            else {
                upPaddle = true;
                downPaddle = false;
                paddle.setDim(paddle.getDim() * 2);
            }

        }

    }

    public void checkPdownExtend() {
        contatore_paddle_small = 0;
        timer_paddle_small = 5;

        if (!downPaddle) {

            //se il paddle è grande,diminuisce il paddle /2 ma annulla tutto
            if (upPaddle) {
                upPaddle = false;
                downPaddle = false;
                paddle.setDim(paddle.getDim() / 2);
            }
            //se il paddle è normale diminuisce il paddle /2 e fa apparire il timer
            else {
                upPaddle = false;
                downPaddle = true;
                paddle.setDim(paddle.getDim() / 2);
            }

        }
    }

    public void checkPdownDevil() {
        contatore_devil = 0;
        timer_devil = 5;
        ball = R.drawable.enemy_explosion_1;
        redBall = BitmapFactory.decodeResource(getResources(), ball);
        palla.creaVelocita();
        palla.aumentaVelocita(level + 15);
        flagDevil = true;
    }

    //funzione general per controllo contatori sui poweup del paddle
    public void checkContatori() {

        //Controlli su contatori paddle
        if (upPaddle) { // se il paddle è ingrandito
            if (contatore_paddle >= 5) { // se la palla ha colpito 5 volte il paddle
                upPaddle = false;
                paddle.setDim(paddle.getDim() / 2);
                contatore_paddle = 0;

            }
        }
        if (downPaddle) { // se il paddle è rimpicciolito
            if (contatore_paddle_small >= 5) { // se la palla ha colpito 5 volte il paddle
                downPaddle = false;
                paddle.setDim(paddle.getDim() * 2);
                contatore_paddle_small = 0;
            }
        }
        if (flagDevil) { // se c'è il Devil
            if (contatore_devil >= 5) { // se la palla ha colpito 5 volte il paddle
                flagDevil = false;
                ball = R.drawable.redball;
                redBall = BitmapFactory.decodeResource(getResources(), ball);
                palla.creaVelocita();
                palla.aumentaVelocita(level);
                contatore_devil = 0;
            }
        }
        //ad ogni impatto tra palla e paddle il contatore viene incrementato
        if (palla.impattoBarra(paddle.getX(), paddle.getY(), paddle.getDim())) {
            contatore_paddle++;
            contatore_paddle_small++;
            contatore_devil++;
            if (upPaddle) {
                timer_paddle--;
            }
            if (downPaddle) {
                timer_paddle_small--;
            }
            if (flagDevil) {
                timer_devil--;
            }
        }
        ;
        //Fine controlli contatori paddle
    }

    //getter creato per poterlo utilizzare nel drag and drop e nel sensor
    public boolean isSystem_ready() {
        return system_ready;
    }


}


