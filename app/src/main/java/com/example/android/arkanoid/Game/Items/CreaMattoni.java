package com.example.android.arkanoid.Game.Items;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;

import com.example.android.arkanoid.Game.Costanti;
import com.example.android.arkanoid.Settings.Settings;

import java.util.ArrayList;

public class CreaMattoni {
    private float brickWidth;
    private float brickHeight;
    private int diffLandscape;

    private Costanti cost;
    private boolean landscape; // variabile per landscape
    SharedPreferences settings; // serve per caricare il valore del landscape

    public CreaMattoni(Context context) {

        settings = context.getSharedPreferences(Settings.FILENAME, Context.MODE_PRIVATE);
        landscape = settings.getBoolean("landscape",false);
        cost = new Costanti();

        if(!landscape){
            cost.setBrickWidth(100);//setta le variabili per portraint o landscape
            cost.setBrickHeight(80);
        }else{
            cost.setBrickWidth(100);//setta le variabili per portraint o landscape
            cost.setBrickHeight(50);
        }

        brickWidth = cost.getBrickWidth();
        brickHeight = cost.getBrickHeight();


    }

    public void createLv(int level, Context context, Point size, ArrayList<Brick> elenco) { //Crea Mattoni per i vari Lv

        switch (level) {
            case 1:
                lv1(context, size, elenco);
                break;
            case 2:
                lv2(context, size, elenco);
                break;
            case 3:
                lv3(context, size, elenco);
                break;
            case 4:
                lv4(context, size, elenco);
                break;
            case 5:
                lv5(context, size, elenco);
                break;
            case 6:
                lv6(context, size, elenco);
                break;
            case 7:
                lv7(context, size, elenco);
                break;
            case 8:
                lv8(context, size, elenco);
                break;
            case 9:
                lv9(context, size, elenco);
                break;
            case 10:
                lv10(context, size, elenco);
                break;
            default:
                ultimateLevel(context, size, elenco);
                break; //Dopo il lv 20 caricherà sempre questa
        }


    }

    //riempire l'elenco con i mattoni
    private void lv1(Context context, Point size, ArrayList<Brick> elenco) {

        float brickmetaWidth = brickWidth / 2;
        float xiniziale = ((size.x / 2) - brickmetaWidth);


        if(!landscape){
            diffLandscape = 0;
        }else{
            diffLandscape = -4;
        }



        //Piramide
        elenco.add(new Brick(context, xiniziale, (3+ diffLandscape) * brickHeight, 0));

        elenco.add(new Brick(context, (xiniziale) - 1 * brickmetaWidth, (4+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) + 1 * brickmetaWidth, (4+ diffLandscape) * brickHeight, 2));

        elenco.add(new Brick(context, (xiniziale) - 2 * brickmetaWidth, (5+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale), (5+ diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (xiniziale) + 2 * brickmetaWidth, (5+ diffLandscape) * brickHeight, 2));

        elenco.add(new Brick(context, (xiniziale) - 3 * brickmetaWidth, (6+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) - 1 * brickmetaWidth, (6+ diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale) + 1 * brickmetaWidth, (6+ diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (xiniziale) + 3 * brickmetaWidth, (6+ diffLandscape) * brickHeight, 2));

        elenco.add(new Brick(context, (xiniziale) - 4 * brickmetaWidth, (7+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) - 2 * brickmetaWidth, (7+ diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale), (7+ diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (xiniziale) + 2 * brickmetaWidth, (7+ diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (xiniziale) + 4 * brickmetaWidth, (7+ diffLandscape) * brickHeight, 2));

        elenco.add(new Brick(context, (xiniziale) - 5 * brickmetaWidth, (8+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) - 3 * brickmetaWidth, (8+ diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale) - 1 * brickmetaWidth, (8+ diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (xiniziale) + 1 * brickmetaWidth, (8+ diffLandscape) * brickHeight, 4));
        elenco.add(new Brick(context, (xiniziale) + 3 * brickmetaWidth, (8+ diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (xiniziale) + 5 * brickmetaWidth, (8+ diffLandscape) * brickHeight, 2));

        elenco.add(new Brick(context, (xiniziale) - 6 * brickmetaWidth, (9+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) - 4 * brickmetaWidth, (9+ diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale) - 2 * brickmetaWidth, (9+ diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (xiniziale), (9+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) + 2 * brickmetaWidth, (9+ diffLandscape) * brickHeight, 4));
        elenco.add(new Brick(context, (xiniziale) + 4 * brickmetaWidth, (9+ diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (xiniziale) + 6 * brickmetaWidth, (9+ diffLandscape) * brickHeight, 2));

        elenco.add(new Brick(context, (xiniziale) - 7 * brickmetaWidth, (10+ diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (xiniziale) - 5 * brickmetaWidth, (10+ diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale) - 3 * brickmetaWidth, (10+ diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale) - 1 * brickmetaWidth, (10+ diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (xiniziale) + 1 * brickmetaWidth, (10+ diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (xiniziale) + 3 * brickmetaWidth, (10+ diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (xiniziale) + 5 * brickmetaWidth, (10+ diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (xiniziale) + 7 * brickmetaWidth, (10+ diffLandscape) * brickHeight, 6));


    }

    private void lv2(Context context, Point size, ArrayList<Brick> elenco) {

        float brickmetaWidth = brickWidth / 2;
        float xiniziale = ((size.x / 2) - brickmetaWidth);

        if(!landscape){
            diffLandscape = 0;
        }else{
            diffLandscape = -4;
        }

        //Piramide rovesciata
        elenco.add(new Brick(context, xiniziale, (10+ diffLandscape) * brickHeight, 0));

        elenco.add(new Brick(context, (xiniziale) - 1 * brickmetaWidth, (9+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) + 1 * brickmetaWidth, (9+ diffLandscape) * brickHeight, 2));

        elenco.add(new Brick(context, (xiniziale) - 2 * brickmetaWidth, (8+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale), (8+ diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (xiniziale) + 2 * brickmetaWidth, (8+ diffLandscape) * brickHeight, 2));

        elenco.add(new Brick(context, (xiniziale) - 3 * brickmetaWidth, (7+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) - 1 * brickmetaWidth, (7+ diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale) + 1 * brickmetaWidth, (7+ diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (xiniziale) + 3 * brickmetaWidth, (7+ diffLandscape) * brickHeight, 2));

        elenco.add(new Brick(context, (xiniziale) - 4 * brickmetaWidth, (6+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) - 2 * brickmetaWidth, (6+ diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale), (6+ diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (xiniziale) + 2 * brickmetaWidth, (6+ diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (xiniziale) + 4 * brickmetaWidth, (6+ diffLandscape) * brickHeight, 2));

        elenco.add(new Brick(context, (xiniziale) - 5 * brickmetaWidth, (5+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) - 3 * brickmetaWidth, (5+ diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale) - 1 * brickmetaWidth, (5+ diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (xiniziale) + 1 * brickmetaWidth, (5+ diffLandscape) * brickHeight, 4));
        elenco.add(new Brick(context, (xiniziale) + 3 * brickmetaWidth, (5+ diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (xiniziale) + 5 * brickmetaWidth, (5+ diffLandscape) * brickHeight, 2));

        elenco.add(new Brick(context, (xiniziale) - 6 * brickmetaWidth, (4+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) - 4 * brickmetaWidth, (4+ diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale) - 2 * brickmetaWidth, (4+ diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (xiniziale), (4+ diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) + 2 * brickmetaWidth, (4+ diffLandscape) * brickHeight, 4));
        elenco.add(new Brick(context, (xiniziale) + 4 * brickmetaWidth, (4+ diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (xiniziale) + 6 * brickmetaWidth, (4+ diffLandscape) * brickHeight, 2));

        elenco.add(new Brick(context, (xiniziale) - 7 * brickmetaWidth, (3+ diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (xiniziale) - 5 * brickmetaWidth, (3+ diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale) - 3 * brickmetaWidth, (3+ diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale) - 1 * brickmetaWidth, (3+ diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (xiniziale) + 1 * brickmetaWidth, (3+ diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (xiniziale) + 3 * brickmetaWidth, (3+ diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (xiniziale) + 5 * brickmetaWidth, (3+ diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (xiniziale) + 7 * brickmetaWidth, (3+ diffLandscape) * brickHeight, 6));


    }


    private void lv4(Context context, Point size, ArrayList<Brick> elenco) {

        final float displayWidth = size.x / 2;

        int diffLandscape2;


        if(!landscape){
            diffLandscape = 0;
            diffLandscape2 = 0;
        }else{
            diffLandscape = -4;
            diffLandscape2 = +2;
        }


        //prima finestra
        for (int i = 2; i < 6; i++) {
            elenco.add(new Brick(context, (displayWidth) - i * brickWidth, (4+diffLandscape) * brickHeight, 0));
            elenco.add(new Brick(context, (displayWidth) - i * brickWidth, (8+diffLandscape) * brickHeight, 0));
        }
        elenco.add(new Brick(context, (displayWidth) - 5 * brickWidth, (5+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) - 5 * brickWidth, (6+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) - 5 * brickWidth, (7+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) - 2 * brickWidth, (5+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) - 2 * brickWidth, (6+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) - 2 * brickWidth, (7+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (5+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (6+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (7+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (5+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (6+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (7+diffLandscape) * brickHeight, 3));

        //seconda finestra
        for (int i = 1; i < 5; i++) {
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (4+diffLandscape) * brickHeight, 0));
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (8+diffLandscape) * brickHeight, 0));
        }
        elenco.add(new Brick(context, (displayWidth) + 4 * brickWidth, (5+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) + 4 * brickWidth, (6+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) + 4 * brickWidth, (7+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) + 1 * brickWidth, (5+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) + 1 * brickWidth, (6+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) + 1 * brickWidth, (7+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (5+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (6+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (7+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (5+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (6+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (7+diffLandscape) * brickHeight, 3));

        //bocca
        for (int i = (-3-diffLandscape2); i < (3+diffLandscape2); i++) {
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (12+diffLandscape) * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (13+diffLandscape) * brickHeight, 6));
        }


    }

    private void lv3(Context context, Point size, ArrayList<Brick> elenco) {

        float brickmetaWidth = brickWidth / 2;
        float xiniziale = ((size.x / 2) - brickmetaWidth);
        final float displayWidth = size.x / 2;
        int diffLandscape2;
        int diffLandscape3;
        int diffLandscape4;

        if(!landscape){
            diffLandscape = 0;
            diffLandscape2 = 0;
            diffLandscape3 = 0;
            diffLandscape4 = 0;
        }else{
            diffLandscape = -4;
            diffLandscape2 = +2;
            diffLandscape3 = +1;
            diffLandscape4 = 4;
        }

        int nColonne = 8;

        float xMod = (displayWidth % brickWidth) / 2;
        float xDeltaColonne = (float) Math.floor(displayWidth / brickWidth) - nColonne;
        float xDelta = xMod + ((brickWidth / 2) * xDeltaColonne);

        //prima piramide
        elenco.add(new Brick(context, (xiniziale) - (4+diffLandscape4) * brickmetaWidth, (4+diffLandscape) * brickHeight, 4));
        elenco.add(new Brick(context, (xiniziale) - (5+diffLandscape4) * brickmetaWidth, (5+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) - (3+diffLandscape4) * brickmetaWidth, (5+diffLandscape) * brickHeight, 2));
        elenco.add(new Brick(context, (xiniziale) - (6+diffLandscape4) * brickmetaWidth, (6+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) - (4+diffLandscape4) * brickmetaWidth, (6+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (xiniziale) - (2+diffLandscape4) * brickmetaWidth, (6+diffLandscape) * brickHeight, 2));
        elenco.add(new Brick(context, (xiniziale) - (7+diffLandscape4) * brickmetaWidth, (7+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (xiniziale) - (5+diffLandscape4) * brickmetaWidth, (7+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (xiniziale) - (3+diffLandscape4) * brickmetaWidth, (7+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (xiniziale) - (1+diffLandscape4) * brickmetaWidth, (7+diffLandscape) * brickHeight, 2));

        //seconda piramide
        elenco.add(new Brick(context, (xiniziale) + (4+diffLandscape4) * brickmetaWidth, (7+diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (xiniziale) + (5+diffLandscape4) * brickmetaWidth, (6+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (xiniziale) + (3+diffLandscape4) * brickmetaWidth, (6+diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale) + (6+diffLandscape4) * brickmetaWidth, (5+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (xiniziale) + (4+diffLandscape4) * brickmetaWidth, (5+diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (xiniziale) + (2+diffLandscape4) * brickmetaWidth, (5+diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (xiniziale) + (7+diffLandscape4) * brickmetaWidth, (4+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (xiniziale) + (5+diffLandscape4) * brickmetaWidth, (4+diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (xiniziale) + (3+diffLandscape4) * brickmetaWidth, (4+diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (xiniziale) + (1+diffLandscape4) * brickmetaWidth, (4+diffLandscape) * brickHeight, 5));

        //rettangolo
        for (int i = 10; i < 13; i++) { //righe (asse y)
            for (int j = 5; j < (9+diffLandscape2); j++) { //colonne (asse x)
                int n = (int) (Math.random() * 7) + 1;
                elenco.add(new Brick(context, ((j+diffLandscape3) * brickWidth) + xDelta, (i+diffLandscape) * brickHeight, n));
            }
        }


    }

    private void lv5(Context context, Point size, ArrayList<Brick> elenco) {

        final float displayWidth = size.x / 2;
        int a = 2;

        int diffLandscape2;
        int diffLandscape3;
        int diffLandscape4;

        if(!landscape){
            diffLandscape = 0;
            diffLandscape2 = 0;
            diffLandscape3 = 0;
            diffLandscape4 = 0;
        }else{
            diffLandscape = -4;
            diffLandscape2 = +2;
            diffLandscape3 = +3;
            diffLandscape4 = +1;
        }


        for (int i = (-5-diffLandscape3); i < 4; i++) {

            for (int j = a; j < (10+diffLandscape2); j++) {
                int n = (int) (Math.random() * 7) + 1;
                elenco.add(new Brick(context, (displayWidth) + i * (brickWidth), (j+diffLandscape) * brickHeight, n));
            }
            a++;
        }

        for (int i = (-5-diffLandscape3); i < (4-diffLandscape4); i++) {

            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, ((10+diffLandscape2)+diffLandscape) * brickHeight, 8));

        }


    }

    private void lv6(Context context, Point size, ArrayList<Brick> elenco) {

        final float displayWidth = size.x / 2;

        int diffLandscape2;

        if(!landscape){
            diffLandscape = 0;
            diffLandscape2 = 0;
        }else{
            diffLandscape = -4;
            diffLandscape2 = +2;
        }

        //base
        for (int i = -3; i < 3; i++) {
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (12+diffLandscape) * brickHeight, 8));
        }
        elenco.add(new Brick(context, (displayWidth) - 5 * brickWidth, (12+diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (12+diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (12+diffLandscape) * brickHeight, 4));
        elenco.add(new Brick(context, (displayWidth) + 4 * brickWidth, (12+diffLandscape) * brickHeight, 4));

        //prima finestra
        for (int i = 1+diffLandscape2; i < 4+diffLandscape2; i++) {
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (3+diffLandscape) * brickHeight, 2));
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (5+diffLandscape) * brickHeight, 2));
        }
        elenco.add(new Brick(context, (displayWidth) + (1+diffLandscape2) * brickWidth, (4+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) + (3+diffLandscape2) * brickWidth, (4+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) + (2+diffLandscape2) * brickWidth, (4+diffLandscape) * brickHeight, 9));


        //seconda
        for (int i = 1+diffLandscape2; i < 4+diffLandscape2; i++) {
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (7+diffLandscape) * brickHeight, 1));
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (9+diffLandscape) * brickHeight, 1));
        }
        elenco.add(new Brick(context, (displayWidth) + (1+diffLandscape2) * brickWidth, (8+diffLandscape) * brickHeight, 2));
        elenco.add(new Brick(context, (displayWidth) + (3+diffLandscape2) * brickWidth, (8+diffLandscape) * brickHeight, 2));
        elenco.add(new Brick(context, (displayWidth) + (2+diffLandscape2) * brickWidth, (8+diffLandscape) * brickHeight, 5));

        //terza
        for (int i = (-4-diffLandscape2); i < (-1-diffLandscape2); i++) {
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (3+diffLandscape) * brickHeight, 0));
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (5+diffLandscape) * brickHeight, 0));
        }
        elenco.add(new Brick(context, (displayWidth) - (4+diffLandscape2) * brickWidth, (4+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (displayWidth) - (2+diffLandscape2) * brickWidth, (4+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (displayWidth) - (3+diffLandscape2) * brickWidth, (4+diffLandscape) * brickHeight, 5));

        //quarta
        for (int i = (-4-diffLandscape2); i < (-1-diffLandscape2); i++) {
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (7+diffLandscape) * brickHeight, 3));
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (9+diffLandscape) * brickHeight, 3));
        }
        elenco.add(new Brick(context, (displayWidth) - (4+diffLandscape2) * brickWidth, (8+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) - (2+diffLandscape2) * brickWidth, (8+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) - (3+diffLandscape2) * brickWidth, (8+diffLandscape) * brickHeight, 9));


        if(landscape){
            elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, 0 * brickHeight, 8));
            elenco.add(new Brick(context, (displayWidth) - 0 * brickWidth, 0 * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, -1 * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) - 0 * brickWidth, -1 * brickHeight, 8));
            elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, 1 * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) - 0 * brickWidth, 1 * brickHeight, 8));
            elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, 2 * brickHeight, 8));
            elenco.add(new Brick(context, (displayWidth) - 0 * brickWidth, 2 * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, 3 * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) - 0 * brickWidth, 3 * brickHeight, 8));
            elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, 4 * brickHeight, 8));
            elenco.add(new Brick(context, (displayWidth) - 0 * brickWidth, 4 * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, 5 * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) - 0 * brickWidth, 5 * brickHeight, 8));

        }
    }


    private void lv7(Context context, Point size, ArrayList<Brick> elenco) {

        final float displayWidth = size.x / 2;

        if(!landscape){
            diffLandscape = 0;

        }else{
            diffLandscape = -4;
        }

// finestre superiori
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (3+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (3+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, (3+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 0 * brickWidth, (3+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (3+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (3+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (6+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (6+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, (6+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 0 * brickWidth, (6+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (6+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (6+diffLandscape) * brickHeight, 8));

        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (4+diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (4+diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (5+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (5+diffLandscape) * brickHeight, 3));

        elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, (4+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) + 0 * brickWidth, (4+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, (5+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) + 0 * brickWidth, (5+diffLandscape) * brickHeight, 1));

        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (4+diffLandscape) * brickHeight, 2));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (4+diffLandscape) * brickHeight, 2));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (5+diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (5+diffLandscape) * brickHeight, 6));


        //finestre inferiori
        elenco.add(new Brick(context, (displayWidth) - 5 * brickWidth, (9+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (9+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (9+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 5 * brickWidth, (13+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (13+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (13+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 4 * brickWidth, (9+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (9+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (9+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 4 * brickWidth, (13+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (13+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (13+diffLandscape) * brickHeight, 8));

        elenco.add(new Brick(context, (displayWidth) - 5 * brickWidth, (10+diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (10+diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (10+diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (displayWidth) - 5 * brickWidth, (11+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (11+diffLandscape) * brickHeight, 9));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (11+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (displayWidth) - 5 * brickWidth, (12+diffLandscape) * brickHeight, 4));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (12+diffLandscape) * brickHeight, 4));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (12+diffLandscape) * brickHeight, 4));

        elenco.add(new Brick(context, (displayWidth) + 4 * brickWidth, (10+diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (10+diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (10+diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (displayWidth) + 4 * brickWidth, (11+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (11+diffLandscape) * brickHeight, 9));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (11+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (displayWidth) + 4 * brickWidth, (12+diffLandscape) * brickHeight, 4));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (12+diffLandscape) * brickHeight, 4));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (12+diffLandscape) * brickHeight, 4));


    }

    private void lv10(Context context, Point size, ArrayList<Brick> elenco) {

        final float displayWidth = size.x / 2;


        if(!landscape){
            diffLandscape = 0;
        }else{
            diffLandscape = -4;
        }


        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (11+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (10+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (9+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) - 4 * brickWidth, (8+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (11+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (10+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (9+diffLandscape) * brickHeight, 0));
        elenco.add(new Brick(context, (displayWidth) + 3 * brickWidth, (8+diffLandscape) * brickHeight, 0));

        for (int i = -3; i < +3; i++) {
            for (int j = 7; j < 10; j++) {
                elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (j+diffLandscape) * brickHeight, 4));
            }
        }
        elenco.add(new Brick(context, (displayWidth) - 2 * brickWidth, (10+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, (10+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) + 0 * brickWidth, (10+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) + 1 * brickWidth, (10+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) + 1 * brickWidth, (11+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) - 2 * brickWidth, (11+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) + 1 * brickWidth, (12+diffLandscape) * brickHeight, 1));
        elenco.add(new Brick(context, (displayWidth) - 2 * brickWidth, (12+diffLandscape) * brickHeight, 1));


        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (4+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 2 * brickWidth, (4+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, (4+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 0 * brickWidth, (4+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 1 * brickWidth, (4+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (4+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (6+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 2 * brickWidth, (6+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, (6+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 0 * brickWidth, (6+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 1 * brickWidth, (6+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (6+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (5+diffLandscape) * brickHeight, 8));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (5+diffLandscape) * brickHeight, 8));

        elenco.add(new Brick(context, (displayWidth) + 1 * brickWidth, (5+diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (displayWidth) + 0 * brickWidth, (5+diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, (5+diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (displayWidth) - 2 * brickWidth, (5+diffLandscape) * brickHeight, 5));

        elenco.add(new Brick(context, (displayWidth) + 1 * brickWidth, (3+diffLandscape) * brickHeight, 4));
        elenco.add(new Brick(context, (displayWidth) + 0 * brickWidth, (3+diffLandscape) * brickHeight, 4));
        elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, (3+diffLandscape) * brickHeight, 4));
        elenco.add(new Brick(context, (displayWidth) - 2 * brickWidth, (3+diffLandscape) * brickHeight, 4));

        elenco.add(new Brick(context, (displayWidth) - 2 * brickWidth, (2+diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (displayWidth) + 1 * brickWidth, (2+diffLandscape) * brickHeight, 6));

        elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (1+diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (1+diffLandscape) * brickHeight, 6));

        if(landscape){
            elenco.add(new Brick(context, (displayWidth) - 7 * brickWidth, (4+diffLandscape) * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) - 8 * brickWidth, (4+diffLandscape) * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) - 6 * brickWidth, (4+diffLandscape) * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) - 6 * brickWidth, (3+diffLandscape) * brickHeight, 3));
            elenco.add(new Brick(context, (displayWidth) - 8 * brickWidth, (3+diffLandscape) * brickHeight, 3));
            elenco.add(new Brick(context, (displayWidth) - 7 * brickWidth, (5+diffLandscape) * brickHeight, 3));
            elenco.add(new Brick(context, (displayWidth) - 7 * brickWidth, (6+diffLandscape) * brickHeight, 3));
            elenco.add(new Brick(context, (displayWidth) - 7 * brickWidth, (7+diffLandscape) * brickHeight, 8));

            elenco.add(new Brick(context, (displayWidth) + 5 * brickWidth, (4+diffLandscape) * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) + 6 * brickWidth, (4+diffLandscape) * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) + 4 * brickWidth, (4+diffLandscape) * brickHeight, 5));
            elenco.add(new Brick(context, (displayWidth) + 4 * brickWidth, (3+diffLandscape) * brickHeight, 3));
            elenco.add(new Brick(context, (displayWidth) + 6 * brickWidth, (3+diffLandscape) * brickHeight, 3));
            elenco.add(new Brick(context, (displayWidth) + 5 * brickWidth, (5+diffLandscape) * brickHeight, 3));
            elenco.add(new Brick(context, (displayWidth) + 5 * brickWidth, (6+diffLandscape) * brickHeight, 3));
            elenco.add(new Brick(context, (displayWidth) + 5 * brickWidth, (7+diffLandscape) * brickHeight, 9));
        }

    }

    private void lv8(Context context, Point size, ArrayList<Brick> elenco) {


        final float displayWidth = size.x / 2;
        int a = 1;

        int diffLandscape2;


        if(!landscape){
            diffLandscape = 0;
            diffLandscape2 = 0;

        }else{
            diffLandscape = -3;
            diffLandscape2 = +2;

        }


        //primo triangolo
        for (int i = -5-diffLandscape2; i < 0; i++) {

            for (int j = a; j < 6; j++) {
                int n = (int) (Math.random() * 7) + 1;
                elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (j+diffLandscape) * brickHeight, n));
            }
            a++;
        }

        for (int i = -5-diffLandscape2; i < 1-diffLandscape2; i++) {

            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (6+diffLandscape) * brickHeight, 8));

        }
        //fine primo triangolo

        //secondo triangolo
        a = 7;
        for (int i = 4+diffLandscape2; i > -1+diffLandscape2; i--) {

            for (int j = a; j < 12; j++) {
                int n = (int) (Math.random() * 7) + 1;
                elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (j+diffLandscape) * brickHeight, n));
            }
            a++;
        }


        for (int i = -1+diffLandscape2; i < 5+diffLandscape2; i++) {

            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (12+diffLandscape) * brickHeight, 8));

        } // fine secondo triangolo

        elenco.add(new Brick(context, (displayWidth) - (4+diffLandscape2) * brickWidth, (8+diffLandscape) * brickHeight, 7));
        elenco.add(new Brick(context, (displayWidth) - (3+diffLandscape2) * brickWidth, (8+diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (displayWidth) - (3+diffLandscape2) * brickWidth, (9+diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (displayWidth) - (4+diffLandscape2) * brickWidth, (9+diffLandscape) * brickHeight, 3));

        elenco.add(new Brick(context, (displayWidth) + (3+diffLandscape2) * brickWidth, (2+diffLandscape) * brickHeight, 9));
        elenco.add(new Brick(context, (displayWidth) + (2+diffLandscape2) * brickWidth, (2+diffLandscape) * brickHeight, 6));
        elenco.add(new Brick(context, (displayWidth) + (3+diffLandscape2) * brickWidth, (3+diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (displayWidth) + (2+diffLandscape2) * brickWidth, (3+diffLandscape) * brickHeight, 3));

        if(landscape){
            elenco.add(new Brick(context, (displayWidth) - (2+diffLandscape2) * brickWidth, (8+diffLandscape) * brickHeight, 7));
            elenco.add(new Brick(context, (displayWidth) - (2+diffLandscape2) * brickWidth, (9+diffLandscape) * brickHeight, 6));
            elenco.add(new Brick(context, (displayWidth) + (1+diffLandscape2) * brickWidth, (3+diffLandscape) * brickHeight, 9));
            elenco.add(new Brick(context, (displayWidth) + (1+diffLandscape2) * brickWidth, (2+diffLandscape) * brickHeight, 6));
        }



    }

    private void lv9(Context context, Point size, ArrayList<Brick> elenco) {

        final float displayWidth = size.x / 2;
        int a = 1;



        if(!landscape){
            diffLandscape = 0;

        }else{
            diffLandscape = -4;
        }

        for (int i = -5; i < 5; i++) {
            int n = (int) (Math.random() * 7) + 1;
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (3+diffLandscape) * brickHeight, n));
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (12+diffLandscape) * brickHeight, n));
        }
        for (int j = 4; j < 12; j++) {
            int n = (int) (Math.random() * 7) + 1;
            elenco.add(new Brick(context, (displayWidth) - 5 * brickWidth, (j+diffLandscape) * brickHeight, n));
            elenco.add(new Brick(context, (displayWidth) + 4 * brickWidth, (j+diffLandscape) * brickHeight, n));
        }

        for (int i = -3; i < 3; i++) {
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (5+diffLandscape) * brickHeight, 8));
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, (10+diffLandscape) * brickHeight, 8));
        }
        for (int j = 6; j < 10; j++) {
            elenco.add(new Brick(context, (displayWidth) - 3 * brickWidth, (j+diffLandscape) * brickHeight, 8));
            elenco.add(new Brick(context, (displayWidth) + 2 * brickWidth, (j+diffLandscape) * brickHeight, 8));
        }

        elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, (7+diffLandscape) * brickHeight, 9));
        elenco.add(new Brick(context, (displayWidth) + 0 * brickWidth, (7+diffLandscape) * brickHeight, 5));
        elenco.add(new Brick(context, (displayWidth) - 1 * brickWidth, (8+diffLandscape) * brickHeight, 3));
        elenco.add(new Brick(context, (displayWidth) + 0 * brickWidth, (8+diffLandscape) * brickHeight, 9));


    }

    private void ultimateLevel(Context context, Point size, ArrayList<Brick> elenco) {

        int nRighe = 4;
        int nColonne = 8;
        final float displayWidth = size.x;

        int diffLandscape2;

        if(!landscape){
            diffLandscape = 0;
            diffLandscape2 = 0;

        }else{
            diffLandscape = -4;
            diffLandscape2 = 2;
        }

        float xMod = (displayWidth % brickWidth) / 2;
        float xDeltaColonne = (float) Math.floor(displayWidth / brickWidth) - nColonne;
        float xDelta = xMod + ((brickWidth / 2) * xDeltaColonne);


        for (int i = 2; i < nRighe + 4; i++) { //righe (asse y)
            for (int j = -diffLandscape2; j < nColonne +diffLandscape2; j++) { //colonne (asse x)
                int n = (int) (Math.random() * 9) + 1;
                elenco.add(new Brick(context, (j * brickWidth) + xDelta, (i+diffLandscape) * brickHeight, n));
            }
        }

    }

    public void lvMultiplayer(Context context, Point size, ArrayList<Brick> elenco) {

        final float displayWidth = size.x / 2;

        for (int i = -5; i < 5; i++) {
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, 9 * brickHeight, 2));
            elenco.add(new Brick(context, (displayWidth) + i * brickWidth, 10 * brickHeight, 2));
        }


    }
}
