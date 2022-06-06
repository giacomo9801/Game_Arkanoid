package com.example.android.arkanoid.CovidGame;

import android.content.Context;
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
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.core.content.res.ResourcesCompat;

import com.example.android.arkanoid.CovidGame.Items.Covid;
import com.example.android.arkanoid.CovidGame.Items.CovidSmall;
import com.example.android.arkanoid.CovidGame.Items.CreaCovid;
import com.example.android.arkanoid.CovidGame.Items.Infermiere;
import com.example.android.arkanoid.CovidGame.Items.Vaccino;
import com.example.android.arkanoid.CovidGame.Threads.CovidSmallThread;
import com.example.android.arkanoid.Game.Items.PowerUp;
import com.example.android.arkanoid.Game.Items.Rocket;
import com.example.android.arkanoid.Game.Threads.RocketThread;
import com.example.android.arkanoid.R;

import java.util.ArrayList;
import java.util.Locale;

public abstract class AbstractCovidGame extends View implements View.OnTouchListener {
    private Bitmap sfondo;
    private Bitmap edgeTop;
    private Bitmap disteso;
    private Bitmap infermiere_p;
    private Bitmap blackScreen;
    private Bitmap pause;
    private Bitmap help_;
    private Bitmap menuPausa;
    private Bitmap gameOver_image;
    private Bitmap iniziaNuova_partita;
    private Display display;
    protected Point size;
    private Paint paint;

    private ArrayList<Covid> elenco; //covid
    protected Infermiere infermiere; //paddle
    private ArrayList<PowerUp> powerUps;
    private ArrayList<Vaccino> vaccino;// vaccini
    private ArrayList<Rocket> rocket;// missili
    private ArrayList<CovidSmall> covidSmall;// covid piccoli

    private RectF r;

    protected int lifes;
    protected int score;
    protected int level;
    protected boolean start;
    protected boolean pausaCovid;
    protected boolean esci;
    protected boolean restart;
    protected boolean help;
    protected boolean gameOver;
    private boolean pUp;
    private boolean upInfermiere = false;
    private boolean downInfermiere = false;
    private boolean flagVaccino;
    private boolean flagRocket;
    private boolean flagCovidSmall;
    private boolean flagDevil = false;
    private Context context;
    private CreaCovid cv;


    private RocketThread myVaccinoThread; //Thread animazione vaccini
    private Handler vaccinoHandler; //handler vaccini
    private CovidSmallThread myCovidSmallThread; //Thread covid rilasciati dai covid (brick)
    private Handler covidSmallHandler; //handler covid rilasciati dai covid (brick)
    private int inf = R.drawable.infermiere; //immagine infermiere

    MediaPlayer gamemusic = MainActivityCovid.music_game;
    MediaPlayer sound_gameover = MainActivityCovid.sound_gameover;
    MediaPlayer sneeze_sound = MainActivityCovid.sneeze_sound;

    private final static int MAX_VOLUME = 100;
    final float volume_high = (float) (1 - (Math.log(MAX_VOLUME - 40) / Math.log(MAX_VOLUME))); //parametro volume alto
    final float volume_mid = (float) (1 - (Math.log(MAX_VOLUME - 20) / Math.log(MAX_VOLUME))); //parametro volume medio
    final float volume_low = (float) (1 - (Math.log(MAX_VOLUME - 5) / Math.log(MAX_VOLUME))); //parametro volume basso

    public AbstractCovidGame(Context context, int lifes, int score) {
        super(context);

        paint = new Paint();

        //inizializazzione del Rocket thread e handler per i vaccini
        creaVaccinoHandler();
        myVaccinoThread = new RocketThread(vaccinoHandler);
        creaVaccinoHandler();
        myVaccinoThread.start();

        //inizializazzione del Rocket thread e handler per i covid small
        creaCovidSmallHandler();
        myCovidSmallThread = new CovidSmallThread(covidSmallHandler);
        creaCovidSmallHandler();
        myCovidSmallThread.start();

        //impostare contesto, vite, punteggi e livelli
        this.context = context;
        this.lifes = lifes;
        this.score = score;

        cv = new CreaCovid();

        level = 1;

        //inizializza gameover per scoprire se la partita è in piedi e se il giocatore non l'ha persa
        start = false;
        gameOver = false;
        pUp = false;
        flagRocket = false;
        flagVaccino = false;
        flagCovidSmall = false;

        //imposta sfondo

        leggiSfondo(context);

        //crea una bitmap per l'infermiere(inf)
        inf = R.drawable.infermiere;
        infermiere_p = BitmapFactory.decodeResource(getResources(), inf);

        //gameover
        gameOver_image = BitmapFactory.decodeResource(getResources(),R.drawable.game_over);
        iniziaNuova_partita= BitmapFactory.decodeResource(getResources(),R.drawable.button_nuova_partita);



        //crea un nuovo infermiere (inf) e un elenco di covid
        infermiere = new Infermiere(size.x / 2, size.y - 400, Infermiere.STANDARD_DIM); //setta la posizione della barra
        elenco = new ArrayList<Covid>(); //setta i covid
        powerUps = new ArrayList<PowerUp>(); //istanzia i power up
        rocket = new ArrayList<Rocket>(); //istanzia i missili
        vaccino = new ArrayList<Vaccino>();//istanzia i vaccini
        covidSmall = new ArrayList<CovidSmall>();//istanzia i covid small

        //creo la disposizione dei covid in base al livello
        cv.creaCovid(level,context,size,elenco);
        this.setOnTouchListener(this);

        //settaggio musica
        gamemusic.start();
        gamemusic.setLooping(true);
        gamemusic.setVolume(volume_mid, volume_mid);

    }



    //impostare lo sfondo
    private void leggiSfondo(Context context) {

        sfondo = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.cina));
        edgeTop = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.top));
        blackScreen = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.black_screen));
        pause = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_pause));
        help_ = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.aiuto));

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
    }


    //disegna tutto
    protected void onDraw(Canvas canvas) {
        //crea uno sfondo solo una volta


        if (disteso == null) {
            edgeTop = Bitmap.createScaledBitmap(edgeTop, size.x, 85, false); //bordo superiore sfondo
            blackScreen = Bitmap.createScaledBitmap(blackScreen, size.x + 100, 230, false); //sfondo nero
            pause = Bitmap.createScaledBitmap(pause, 80, 80, false); //sfondo pausa
            help_ = Bitmap.createScaledBitmap(help_, 80, 80, false); //sfondo help
        }

        disteso = Bitmap.createScaledBitmap(sfondo, size.x, size.y, false);
        // disegna sfondo
        canvas.drawBitmap(blackScreen, -20, 0, paint);
        canvas.drawBitmap(disteso, 0, 230, paint);
        canvas.drawBitmap(edgeTop, 0, 200, paint);
        canvas.drawBitmap(pause, 20, 40, paint);
        canvas.drawBitmap(help_, size.x-100, 40, paint);

        paint.setColor(Color.GRAY);
        paint.setTextSize(1000);
        //disegna numero di livello sul background

        //disegna barra (paddle)
        paint.setColor(Color.WHITE);
        r = new RectF(infermiere.getX() - (infermiere.getDim() / 2), infermiere.getY() - (Infermiere.HEIGHT / 2), infermiere.getX() + (infermiere.getDim() / 2), infermiere.getY() + (Infermiere.HEIGHT / 2));
        canvas.drawBitmap(infermiere_p, null, r, paint);

        //disegnare covid
        paint.setColor(Color.GREEN);
        for (int i = 0; i < elenco.size(); i++) {
            Covid c = elenco.get(i);
            r = new RectF(c.getX() - (Covid.WIDTH / 2), c.getY() - (Covid.HEIGHT / 2), c.getX() + (Covid.WIDTH / 2), c.getY() + (Covid.HEIGHT / 2));
            canvas.drawBitmap(c.getCovid(), null, r, paint);
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

        //disegna vaccino
        paint.setColor(Color.BLACK);
        if (flagVaccino) {
            for (int i = 0; i < vaccino.size(); i++) {
                Vaccino v = vaccino.get(i);
                r = new RectF(v.getX(), v.getY(), v.getX() + 20, v.getY() + 60);
                canvas.drawBitmap(v.getVaccino(), null, r, paint);
            }
        }

        //disegna covid small
        paint.setColor(Color.BLACK);
        if (flagCovidSmall) {
            for (int i = 0; i < covidSmall.size(); i++) {
                CovidSmall c = covidSmall.get(i);
                r = new RectF(c.getX(), c.getY(), c.getX() + 20, c.getY() + 60);
                canvas.drawBitmap(c.getCovidSmall(), null, r, paint);
            }
        }

        Typeface typeface = ResourcesCompat.getFont(super.getContext(), R.font.audiowide);
        paint.setTypeface(typeface); //cambia il font

        //disegna counter pwup
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);


        //disegno testo
        paint.setColor(Color.WHITE);
        paint.setTextSize(42);
        canvas.drawText(getResources().getString(R.string.livello) +" " + level, 100, 100, paint);
        canvas.drawText(getResources().getString(R.string.vite) +" "+ lifes, 360, 100, paint);
        canvas.drawText(getResources().getString(R.string.punteggio) +" " + score, 600, 100, paint);

        //in caso di sconfitta disegno "gameover" e visualizzo quanti punti ho totalizzato
        if (gameOver) {
            gamemusic.pause();
            paint.setColor(Color.WHITE);
            paint.setTextSize(60);

            canvas.drawBitmap(gameOver_image, size.x / 4+100, size.y / 2 + 200, paint);
            canvas.drawText("Punteggio " + score, size.x / 4 + 25, size.y / 5 - 50, paint);
            canvas.drawBitmap(iniziaNuova_partita, size.x / 2 - 220, size.y / 2 + 350, paint);
        }

        //in caso di tocco sulla pausa, mostro il menu pausa
        if (pausaCovid && !restart && !help) {
            menuPausa = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_pausa_portrait));
            menuPausa = Bitmap.createScaledBitmap(menuPausa, size.x + 200, size.y + 100, false);
            canvas.drawBitmap(menuPausa, -100, -50, paint);
        }

        //in caso di tocco su help, mostro il menu help
        if (help) {
            if(Locale.getDefault().getDisplayLanguage().equals("English")){
                menuPausa = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.legenda_covid_portrait_en));
            }else {
            menuPausa = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_legenda_covid_portrait));
            }
            menuPausa = Bitmap.createScaledBitmap(menuPausa,  size.x + 200, size.y + 100, false);
            canvas.drawBitmap(menuPausa, -100, -50, paint);
        }

        //permette di creare una nuova partita da subito
        if (restart) {
            if(Locale.getDefault().getDisplayLanguage().equals("English")){
                menuPausa = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_restart_portrait_en));
            }else{
                menuPausa = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_restart_portrait));
            }
            menuPausa = Bitmap.createScaledBitmap(menuPausa, size.x + 200, size.y + 100, false);
            canvas.drawBitmap(menuPausa, -100, -50, paint);
        }

    }

    //controlla lo stato del gioco. Se le mie vite o se il gioco è finito.
    protected void controllaVite() {
        if (lifes == 1) {
            gameOver = true;
            sound_gameover.start();
            sound_gameover.setVolume(volume_high, volume_high);

            level = 1;
            start = false;
            flagVaccino = false;
            flagCovidSmall = false;
            invalidate();
        } else {
            lifes--;
            //se il paddle è ingrandito o ridotto
            infermiere.setDim(Infermiere.STANDARD_DIM);
            downInfermiere = false;
            upInfermiere = false;
            flagDevil = false;
            infermiere.setX(size.x / 2);

            inf = R.drawable.infermiere;
            infermiere_p = BitmapFactory.decodeResource(getResources(), inf);

            start = false;
            flagVaccino = false;
            flagCovidSmall = false;
            invalidate();
            flagDevil = false;
            myVaccinoThread.setMilliseconds(1500);
            Vaccino.speed = 10;
            pUp = false;
            powerUps = new ArrayList<PowerUp>();
            flagRocket = false;
            rocket = new ArrayList<Rocket>();
            flagVaccino = false;
            vaccino = new ArrayList<Vaccino>();
            flagCovidSmall = false;
            covidSmall = new ArrayList<CovidSmall>();
        }
    }


    //ogni passaggio controlla se c'è una collisione, una perdita o una vittoria, ecc.
    public void update() {
        if (start) {
            vittoria();
            flagCovidSmall = true;
            flagVaccino = true;

            //controlla se sono presenti dei covid small, se sì ne gestisce il movimento e gli impatti
            if (flagCovidSmall) {
                for (int i = 0; i < covidSmall.size(); i++) {
                    CovidSmall c = covidSmall.get(i);
                    c.fall(); //attiva la caduta dei covid small
                    if (c.impattoInfermiere(infermiere.getX(), infermiere.getY(), infermiere.getDim())) {
                        controllaVite(); //nel caso il covid small impatta l'infermiere scala le vite
                    }
                    if (c.getY() - c.getSpeed() <= 270) { //controlla se il covid small oltrepassa l'infermiere (paddle)
                        covidSmall.remove(i); //nel caso lo rimuove
                    }
                }
            }

            //controlla se sono prensenti dei vaccini, se sì ne gestisce il movimento e gli impatti
            if (flagVaccino) {
                for (int i = 0; i < vaccino.size(); i++) {
                    Vaccino v = vaccino.get(i);
                    v.fall(); //attiva il movimento dei vaccini
                    for (int j = 0; j < elenco.size(); j++) {
                        Covid c = elenco.get(j);
                        if (c.impattoVaccino(v.getX(), v.getY())) {
                            c.setVita(c.getVita() - 1);   //riduce la vita del covid
                            c.layoutCovid(c.getVita());   //cambia il layout del mattone
                            if (c.getVita() == -1) {
                                elenco.remove(j);
                                //qui dovesti spegnere il flag dei covid small del covid i
                                score = score + 80;
                                pUp = checkPwrUp(c);
                                pUp = !powerUps.isEmpty();
                            }
                            vaccino.remove(i);
                        }
                    }
                    if (v.getY() - v.getSpeed() <= 270) { //controlla che il vaccino non sia arrivato oltre lo schermo
                        vaccino.remove(i); //nel caso lo rimuove
                    }
                }
            }

            //controlla se sono prensenti dei powerup, se sì ne gestisce il movimento e gli impatti
            if (pUp) {
                for (int i = 0; i < powerUps.size(); i++) {
                    PowerUp p = powerUps.get(i);
                    p.fall(); //attiva il movimento del powerup
                    if (infermiere.impattoPwrUp(p)) { //controlla se vi è stata la collisione con l'infermiere (paddle)
                        applyPwrUp(p); //applica gli effetti del powerup
                        powerUps.remove(i);
                        if (powerUps.isEmpty()) {
                            pUp = false;
                        }
                    } else if (p.getY() + p.getSpeed() >= size.y - 200) { //controlla che il powerup non sia arrivato oltre l'infermiere (paddle)
                        powerUps.remove(i); //se sì lo rimuove
                        if (powerUps.isEmpty()) { //controlla se siano presenti ancora dei powerup nel rispettivo arraylist
                            pUp = false;
                        }
                    }
                }
            }

            //controlla se sono presenti dei rocket, se sì ne gestisce il movimento e gli impatti
            if (flagRocket) {
                for (int i = 0; i < rocket.size(); i++) {
                    Rocket r = rocket.get(i);
                    r.fall(); //attiva il movimento
                    for (int j = 0; j < elenco.size(); j++) {
                        Covid c = elenco.get(j);
                        if (c.impattoMissile(r.getX(), r.getY())) { //controlla se vi è stato l'impatto con un covid
                            c.setVita(c.getVita() - 1);   //riduce la vita del covid
                            if (c.getVita() == -1) {
                                elenco.remove(j);
                                //qui dovesti spegnere il flag dei covid small del covid i
                                score = score + 80;
                                pUp = checkPwrUp(c);
                                pUp = !powerUps.isEmpty();
                            }
                            rocket.remove(i);
                        }
                    }
                    if (r.getY() - r.getSpeed() <= 270) { //controlla che i rocket non sia andati oltre lo schermo
                        rocket.remove(i); //nel caso rimuove il rispettivo rocket
                    }
                }
            }
        }
    }

    //serve a sospendere il gioco in caso di nuovo gioco e farla partire
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (gameOver == true && start == false && !pausaCovid) {
            if (event.getX() > size.x / 2 - 220 && event.getY() > size.y / 2 + 350 && event.getX() < size.x / 2 - 220 + 1300 && event.getY() < size.y / 2 + 350 +300) {
                score = 0;
                lifes = 3;
                resetLevel();
                gameOver = false;
            }
        } else {
            start = true;
            flagCovidSmall = true;
            flagVaccino = true;
        }

        return false;
    }

    //imposta il gioco con i valori predefiniti in modo da creare una nuova partita
    protected void resetLevel() {

        switch(level){
            case 1: sfondo = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.cina)); break;
            case 2: sfondo = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.italia)); break;
            case 3: sfondo = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.germania)); break;
            case 4: sfondo = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.spagna)); break;
            case 5: sfondo = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.francia)); break;
            default: sfondo = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.cina)); break;
        }

        infermiere.setX(size.x / 2);
        upInfermiere = false;
        downInfermiere = false;
        infermiere.setDim(Infermiere.STANDARD_DIM);

        elenco = new ArrayList<Covid>(); //istanzio un array vuoto per i covid
        powerUps = new ArrayList<PowerUp>(); //istanzio un array vuoto per i powerup
        rocket = new ArrayList<Rocket>(); //istanzio un array vuoto per i rocket
        vaccino = new ArrayList<Vaccino>(); //istanzio un array vuoto per i vaccini
        covidSmall = new ArrayList<CovidSmall>(); //istanzio un array vuoto per i covid small

        flagRocket = false;
        flagDevil = false;
        Vaccino.speed = 10; //setto la velocità dei vaccini
        myVaccinoThread.setMilliseconds(1500);


        cv.creaCovid(level,context,size,elenco);
        gamemusic.start();
        gamemusic.setVolume(volume_mid, volume_mid);
        inf = R.drawable.infermiere;
        infermiere_p = BitmapFactory.decodeResource(getResources(), inf);
    }

    //scopri se il giocatore ha vinto o meno
    private void vittoria() {
        if (elenco.isEmpty()) { //nel caso in cui l'arraylist dei brick è vuoto, il player ha vinto la partita
            ++level;
            resetLevel();
            start = false;
            flagVaccino = false;
            flagCovidSmall = false;
            invalidate();
        }
    }

    //Fa cadere i vari poweup dai mattoncini
    private boolean checkPwrUp(Covid c) {
        float pwrUpRandomizer = (int) (Math.random() * 6) + 1;
        boolean flag = false;

        if (pwrUpRandomizer >= 1 && pwrUpRandomizer <= 5) {
            powerUps.add(new PowerUp(context, (int) pwrUpRandomizer, c.getX(), c.getY(),2));
            flag = true;
        }

        return flag;
    }

    //implementa i vari powerup
    private void applyPwrUp(PowerUp p) {
        switch (p.getPowerUpType()) {
            case 1: //aumento dimensione dell'infermiere
                checkPupExtend();
                break;

            case 2: //missili
                flagRocket = true;
                break;

            case 3: //diminuisce dimensione barra (paddle)
                checkPdownExtend();
                break;
            case 4: //devil ball
                checkPdownDevil();
                break;
            case 5://vita + 1
                ++lifes;
                break;

        }
    }


    //creazione dei vaccini
    private void checkVaccino(Infermiere inf) {

        if (flagVaccino && !pausaCovid && start) { //spawn vaccini regolare (x2)
            vaccino.add(new Vaccino(context, inf.getX() - (infermiere.getDim() / 2), inf.getY() + (Infermiere.HEIGHT / 2) - 150));
            vaccino.add(new Vaccino(context, inf.getX() + (infermiere.getDim() / 2 - 20), inf.getY() + (Infermiere.HEIGHT / 2) - 150));
        }
        if (flagRocket && !pausaCovid && start) { //spawn vaccini con powerup (x4)
            vaccino.add(new Vaccino(context, inf.getX() - (infermiere.getDim() / 2) + 50 , inf.getY() + (Infermiere.HEIGHT / 2) - 150));
            vaccino.add(new Vaccino(context, inf.getX() + (infermiere.getDim() / 2 - 70), inf.getY() + (Infermiere.HEIGHT / 2) - 150));
        }

    }


    //creazione dei covid small
    private void checkCovidSmall(Covid c) {
        int randomCovid = 0;

        if(level >= 1 && level <= 2) {
            randomCovid = 5;
        }

        if(level >= 3 && level <= 4) {
            randomCovid = 4;
        }
        if(level >= 5){
            randomCovid = 5;
        }

        int covidSmallRandomizer = (int) (Math.random() * randomCovid);
        if ( flagCovidSmall == true && covidSmallRandomizer < 2 && !pausaCovid && start ) {
            sneeze_sound.start();
            covidSmall.add(new CovidSmall(context, c.getX(), c.getY()));
        }
    }

    //definizione metodi per SensorGame
    public abstract void stopTiro();

    public abstract void correreTiro();


    //crea il VaccinoHandler
    private void creaVaccinoHandler() {
        vaccinoHandler = new Handler() {
            public void handleMessage(Message msg) {
                invalidate();
                checkVaccino(infermiere);
                super.handleMessage(msg);
            }
        };
    }

    //crea il CovidHandler
    private void creaCovidSmallHandler() {
        covidSmallHandler = new Handler() {
            public void handleMessage(Message msg) {
                invalidate();
                for (int i = 0; i < elenco.size(); i++) {
                    Covid c = elenco.get(i);
                    checkCovidSmall(c);
                }
                super.handleMessage(msg);
            }
        };
    }

    //Controlli vari sul powerup dell'extends
    public void checkPupExtend() {

        //se l'infermiere è normale
        if (!upInfermiere) {

            //se l'infermiere è piccolo aumenta l'infermiere *2 ma annulla tutto
            if (downInfermiere) {
                upInfermiere = false;
                downInfermiere = false;
                infermiere.setDim(infermiere.getDim() * 2);
            }
            //se l'infermiere è normale aumenta *2 e fa apparire il timer
            else {
                upInfermiere = true;
                downInfermiere = false;
                infermiere.setDim(infermiere.getDim() * 2);
            }

        }
    }


    public void checkPdownExtend() {

        if (!downInfermiere) {

            //se l'infermiere è grande,diminuisce l'infermiere /2 ma annulla tutto
            if (upInfermiere) {
                upInfermiere = false;
                downInfermiere = false;
                infermiere.setDim(infermiere.getDim() / 2);
            }
            //se l'infermiere è normale diminuisce l'infermiere /2 e fa apparire il timer
            else {
                upInfermiere = false;
                downInfermiere = true;
                infermiere.setDim(infermiere.getDim() / 2);
            }
        }
    }

    public void checkPdownDevil() {

        myVaccinoThread.setMilliseconds(600);
        Vaccino.speed = 20;
        flagDevil = true;
    }


}

