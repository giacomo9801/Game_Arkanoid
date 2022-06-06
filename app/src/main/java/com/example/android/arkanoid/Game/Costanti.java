package com.example.android.arkanoid.Game;


public class Costanti {


    private int posPaddle; //posizione paddle
    private int posPalla; //posizione palla

    //contorni game
    private int edgeTopWidht;
    private int edgeTopHeight;
    private int edgeDxSxWidht;
    private int edgeTopLeft;
    private int edgeTopTop;
    private int edgeSxLeft;
    private int edgeSxTop;
    private int edgeDxLeft;
    private int edgeDxTop;

    //text nel game
    private int textLevelX;
    private int textLevelY;
    private int textLifesX;
    private int textLifesY;
    private int textScoreX;
    private int textScoreY;
    private int scoreX;
    private int scoreY;

    //contatori
    private int logExtendX;
    private int logExtendY;
    private int textExtendX;
    private int textExtendY;
    private int logLaserX;
    private int logLaserY;
    private int textLaserX;
    private int textLaserY;
    private int logSmallX;
    private int logSmallY;
    private int textSmallX;
    private int textSmallY;
    private int logDevilX;
    private int logDevilY;
    private int textDevilX;
    private int textDevilY;

    //collisioni
    private int collisioneUp;
    private int collisioneRight;
    private int collisioneLeft;
    private int collisioneDown;

    //drag and sensor
    private int valoreSensorLeft;
    private int valoreSensorRight;
    private int valoreDragLeft;
    private int valoreDragRight;

    //modifica direzione
    private int modDirezione;

    //dimensione brick
    private float brickHeight;
    private float brickWidth;

    //dimensione paddle
    private float paddleHeight;
    private float paddleWidth;

    //cordinate gameover
    private int gameoverX;
    private int gameoverY;
    private int punteggioX;
    private int punteggioY;
    private int nuovaPartitaX;
    private int nuovaPartitaY;

    private int topSfondoStellato;
    private int leftSfondoStellato;




    public Costanti() {

    }

    public void setCostantiPortraint(){

        posPaddle = 400;
        posPalla = 430;

        edgeTopHeight = 85;
        edgeTopWidht = 38;
        edgeDxSxWidht = 30;
        edgeTopLeft = 30;
        edgeTopTop = 200;
        edgeSxLeft = 0;
        edgeSxTop = 200;
        edgeDxLeft = 30;
        edgeDxTop = 200;

        textLevelX = 100;
        textLevelY = 100;
        textLifesX = 360;
        textLifesY = 100;
        textScoreX = 600;
        textScoreY = 100;
        scoreX = 770;
        scoreY = 100;

        textExtendX = 160;
        textExtendY = 175;
        textLaserX = 640;
        textLaserY = 175;
        textSmallX = 400;
        textSmallY = 175;
        textDevilX = 880;
        textDevilY = 175;

        logExtendX = 80;
        logExtendY = 140;
        logLaserX = 560;
        logLaserY = 140;
        logSmallX = 320;
        logSmallY = 140;
        logDevilX = 800;
        logDevilY = 140;

        collisioneDown = 200;
        collisioneRight = 38;
        collisioneLeft = 38;
        collisioneUp = 270;

        gameoverX = -100;
        gameoverY = 200;
        punteggioX = 25 ;
        punteggioY = -50 ;
        nuovaPartitaX= 220;
        nuovaPartitaY= 350;

        topSfondoStellato = 230;
        leftSfondoStellato = 0;

    }

    public void setCostantiLandscape(){

        posPaddle = 100;
        posPalla = 130;

        edgeTopHeight = 60;
        edgeTopWidht = 530;
        edgeDxSxWidht = 65;
        edgeTopLeft = 250;
        edgeTopTop = 5;
        edgeSxLeft = 200;
        edgeSxTop = 15;
        edgeDxLeft = 290;
        edgeDxTop = 15;

        textLevelX = 0;
        textLevelY = 200;
        textLifesX = 0;
        textLifesY = 500;
        textScoreX = 0;
        textScoreY = 800;
        scoreX = 0;
        scoreY = 880;

        textExtendX = 2030;
        textExtendY = 435;
        textLaserX = 2030;
        textLaserY = 235;
        textSmallX = 2030;
        textSmallY = 635;
        textDevilX = 2030;
        textDevilY = 835;

        logExtendX = 1950;
        logExtendY = 400;
        logLaserX = 1950;
        logLaserY = 200;
        logSmallX = 1950;
        logSmallY = 600;
        logDevilX = 1950;
        logDevilY = 800;

        collisioneDown = 0;
        collisioneRight = 280;
        collisioneLeft = 270;
        collisioneUp = 60;

        gameoverX = -350;
        gameoverY = 100;
        punteggioX = +250 ;
        punteggioY = 590 ;
        nuovaPartitaX= 250;
        nuovaPartitaY= 350;

        topSfondoStellato = 0;
        leftSfondoStellato = 0;



    }

    public int getPosPaddle() {
        return posPaddle;
    }

    public int getPosPalla() {
        return posPalla;
    }

    public int getEdgeTopWidht() {
        return edgeTopWidht;
    }

    public int getEdgeTopHeight() {
        return edgeTopHeight;
    }

    public int getEdgeDxSxWidht() {
        return edgeDxSxWidht;
    }

    public int getEdgeTopLeft() {
        return edgeTopLeft;
    }

    public int getEdgeTopTop() {
        return edgeTopTop;
    }

    public int getEdgeSxLeft() {
        return edgeSxLeft;
    }

    public int getEdgeSxTop() {
        return edgeSxTop;
    }

    public int getEdgeDxLeft() {
        return edgeDxLeft;
    }

    public int getEdgeDxTop() {
        return edgeDxTop;
    }

    public int getTextLevelX() {
        return textLevelX;
    }

    public int getTextLevelY() {
        return textLevelY;
    }

    public int getTextLifesX() {
        return textLifesX;
    }

    public int getTextLifesY() {
        return textLifesY;
    }

    public int getTextScoreX() {
        return textScoreX;
    }

    public int getTextScoreY() {
        return textScoreY;
    }

    public int getLogExtendX() {
        return logExtendX;
    }

    public int getLogExtendY() {
        return logExtendY;
    }

    public int getTextExtendX() {
        return textExtendX;
    }

    public int getTextExtendY() {
        return textExtendY;
    }

    public int getLogLaserX() {
        return logLaserX;
    }

    public int getLogLaserY() {
        return logLaserY;
    }

    public int getTextLaserX() {
        return textLaserX;
    }

    public int getTextLaserY() {
        return textLaserY;
    }

    public int getLogSmallX() {
        return logSmallX;
    }

    public int getLogSmallY() {
        return logSmallY;
    }

    public int getTextSmallX() {
        return textSmallX;
    }

    public int getTextSmallY() {
        return textSmallY;
    }

    public int getLogDevilX() {
        return logDevilX;
    }

    public int getLogDevilY() {
        return logDevilY;
    }

    public int getTextDevilX() {
        return textDevilX;
    }

    public int getTextDevilY() {
        return textDevilY;
    }

    public int getCollisioneUp() {
        return collisioneUp;
    }

    public int getCollisioneRight() {
        return collisioneRight;
    }

    public int getCollisioneLeft() {
        return collisioneLeft;
    }

    public int getCollisioneDown() {
        return collisioneDown;
    }

    public int getValoreSensorLeft() {
        return valoreSensorLeft;
    }

    public int getValoreSensorRight() {
        return valoreSensorRight;
    }

    public int getValoreDragLeft() {
        return valoreDragLeft;
    }

    public int getValoreDragRight() {
        return valoreDragRight;
    }

    public int getModDirezione() {
        return modDirezione;
    }

    public int getScoreX() {
        return scoreX;
    }

    public int getScoreY() {
        return scoreY;
    }

    public float getBrickHeight() {
        return brickHeight;
    }

    public float getBrickWidth() {
        return brickWidth;
    }

    public void setModDirezione(int modDirezione) {
        this.modDirezione = modDirezione;
    }

    public void setBrickHeight(float brickHeight) {
        this.brickHeight = brickHeight;
    }

    public void setBrickWidth(float brickWidth) {
        this.brickWidth = brickWidth;
    }

    public void setValoreSensorLeft(int valoreSensorLeft) {
        this.valoreSensorLeft = valoreSensorLeft;
    }

    public void setValoreSensorRight(int valoreSensorRight) {
        this.valoreSensorRight = valoreSensorRight;
    }

    public void setValoreDragLeft(int valoreDragLeft) {
        this.valoreDragLeft = valoreDragLeft;
    }

    public void setValoreDragRight(int valoreDragRight) {
        this.valoreDragRight = valoreDragRight;
    }

    public float getPaddleHeight() {
        return paddleHeight;
    }

    public void setPaddleHeight(float paddleHeight) {
        this.paddleHeight = paddleHeight;
    }

    public float getPaddleWidth() {
        return paddleWidth;
    }

    public void setPaddleWidth(float paddleWidth) {
        this.paddleWidth = paddleWidth;
    }

    public int getGameoverX() {
        return gameoverX;
    }

    public int getGameoverY() {
        return gameoverY;
    }

    public int getPunteggioX() {
        return punteggioX;
    }

    public int getPunteggioY() {
        return punteggioY;
    }

    public int getNuovaPartitaX() { return nuovaPartitaX; }

    public int getNuovaPartitaY() { return nuovaPartitaY; }

    public int getTopSfondoStellato() {
        return topSfondoStellato;
    }

    public int getLeftSfondoStellato() {
        return leftSfondoStellato;
    }
}


