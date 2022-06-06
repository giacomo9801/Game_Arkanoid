package com.example.android.arkanoid.Multiplayer;

import java.util.Random;

public class Room {
    private String uidPlayer1;
    private String uidPlayer2;
    private String idRoom;

    private boolean full;

    final static int MAXIDCHARS = 5;

    public Room(String uid1, String room) {
        this.uidPlayer1 = uid1;
        this.idRoom = room;
        this.full = false;
    }
    public Room() {
        this.full = false;
    }

    public static String getRandomRoomId() {
        final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String NUMCHARS = "1234567890";

        StringBuilder randStr = new StringBuilder();
        Random rand = new Random();

        for(int i = 0; i < MAXIDCHARS; i++) {
            randStr.append(CHARS.charAt(rand.nextInt(CHARS.length())));
        }

        return randStr.toString();
    }

    public boolean accessRoom(String uid2) {
        boolean flag = false;

        if(full != true) {
            this.uidPlayer2 = uid2;
            this.full = true;
            flag = true;


        }

        return flag;
    }

    public String getUidPlayer1() {
        return uidPlayer1;
    }

    public void setUidPlayer1(String uidPlayer1) {
        this.uidPlayer1 = uidPlayer1;
    }

    public String getUidPlayer2() {
        return uidPlayer2;
    }

    public void setUidPlayer2(String uidPlayer2) {
        this.uidPlayer2 = uidPlayer2;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }
}
