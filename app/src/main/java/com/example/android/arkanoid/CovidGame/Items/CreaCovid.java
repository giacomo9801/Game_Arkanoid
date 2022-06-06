package com.example.android.arkanoid.CovidGame.Items;

import android.content.Context;
import android.graphics.Point;

import java.util.ArrayList;

public class CreaCovid {

    final float covidWidth = 150;
    final float covidHight = 150;

    public CreaCovid() {
    }

    public void creaCovid(int level,Context context, Point size, ArrayList<Covid> elenco){

        switch(level){
            case 1: lv1(context, size, elenco); break;
            case 2: lv2(context, size, elenco); break;
            case 3: lv3(context, size, elenco); break;
            case 4: lv4(context, size, elenco); break;
            case 5: lv5(context, size, elenco); break;
            default: lv5(context, size, elenco); break;
        }

    }

    //riempire l'elenco con i covid
    private void lv1(Context context, Point size, ArrayList<Covid> elenco) {

        float displayWidth = size.x/2;
        float covidMetaWidth = covidWidth / 2;
        float xiniziale = ((size.x / 2) - covidMetaWidth);


        elenco.add(new Covid(context, xiniziale, 1 * covidHight, 1));

        elenco.add(new Covid(context, xiniziale - 1 * covidMetaWidth, 2 * covidHight, 0));
        elenco.add(new Covid(context, xiniziale + 1 * covidMetaWidth, 2 * covidHight, 2));
        elenco.add(new Covid(context, xiniziale - 3 * covidMetaWidth, 2 * covidHight, 2));
        elenco.add(new Covid(context, xiniziale + 3 * covidMetaWidth, 2 * covidHight, 0));

        elenco.add(new Covid(context, xiniziale - 1 * covidMetaWidth, 3 * covidHight, 2));
        elenco.add(new Covid(context, xiniziale + 1 * covidMetaWidth, 3 * covidHight, 0));
        elenco.add(new Covid(context, xiniziale, 4 * covidHight, 1));


    }

    private void lv2(Context context, Point size, ArrayList<Covid> elenco) {

        float displayWidth = size.x/2;
        float covidMetaWidth = covidWidth / 2;
        float xiniziale = ((size.x / 2) - covidMetaWidth);


        elenco.add(new Covid(context, xiniziale, 1 * covidHight, 0));
        elenco.add(new Covid(context, xiniziale - 2 * covidMetaWidth, 1 * covidHight, 1));
        elenco.add(new Covid(context, xiniziale + 2 * covidMetaWidth, 1 * covidHight, 2));

        elenco.add(new Covid(context, xiniziale, 2 * covidHight, 2));

        elenco.add(new Covid(context, xiniziale, 3 * covidHight, 2));
        elenco.add(new Covid(context, xiniziale - 2 * covidMetaWidth, 3 * covidHight, 0));
        elenco.add(new Covid(context, xiniziale + 2 * covidMetaWidth, 3 * covidHight, 1));

        elenco.add(new Covid(context, xiniziale, 4 * covidHight, 2));


    }

    private void lv3(Context context, Point size, ArrayList<Covid> elenco) {

        float displayWidth = size.x/2;
        float covidMetaWidth = covidWidth / 2;
        float xiniziale = ((size.x / 2) - covidMetaWidth);


        elenco.add(new Covid(context, xiniziale, 1 * covidHight, 0));

        elenco.add(new Covid(context, xiniziale - 2 * covidMetaWidth, 1 * covidHight, 2));
        elenco.add(new Covid(context, xiniziale + 2 * covidMetaWidth, 1 * covidHight, 2));
        elenco.add(new Covid(context, xiniziale - 4 * covidMetaWidth, 1 * covidHight, 1));
        elenco.add(new Covid(context, xiniziale + 4 * covidMetaWidth, 1 * covidHight, 1));

        elenco.add(new Covid(context, xiniziale - 2 * covidMetaWidth, 2 * covidHight, 0));
        elenco.add(new Covid(context, xiniziale + 2 * covidMetaWidth, 2 * covidHight, 0));

        elenco.add(new Covid(context, xiniziale, 3 * covidHight, 0));

        elenco.add(new Covid(context, xiniziale - 2 * covidMetaWidth, 3 * covidHight, 1));
        elenco.add(new Covid(context, xiniziale + 2 * covidMetaWidth, 3 * covidHight, 1));
        elenco.add(new Covid(context, xiniziale - 4 * covidMetaWidth, 3 * covidHight, 2));
        elenco.add(new Covid(context, xiniziale + 4 * covidMetaWidth, 3 * covidHight, 2));


        elenco.add(new Covid(context, xiniziale, 4 * covidHight, 1));


    }

    private void lv4(Context context, Point size, ArrayList<Covid> elenco) {

        float displayWidth = size.x/2;
        float covidMetaWidth = covidWidth / 2;
        float xiniziale = ((size.x / 2) - covidMetaWidth);


        elenco.add(new Covid(context, xiniziale, 1 * covidHight, 0));
        elenco.add(new Covid(context, xiniziale, 2 * covidHight, 1));
        elenco.add(new Covid(context, xiniziale, 3 * covidHight, 2));
        elenco.add(new Covid(context, xiniziale, 4 * covidHight, 0));

        elenco.add(new Covid(context, xiniziale - 3 * covidMetaWidth, 1 * covidHight, 2));
        elenco.add(new Covid(context, xiniziale + 3 * covidMetaWidth, 1 * covidHight, 1));
        elenco.add(new Covid(context, xiniziale - 3 * covidMetaWidth, 4 * covidHight, 1));
        elenco.add(new Covid(context, xiniziale + 3 * covidMetaWidth, 4 * covidHight, 2));

        elenco.add(new Covid(context, xiniziale - 2 * covidMetaWidth, 2 * covidHight, 1));
        elenco.add(new Covid(context, xiniziale + 2 * covidMetaWidth, 2 * covidHight, 2));
        elenco.add(new Covid(context, xiniziale - 2 * covidMetaWidth, 3 * covidHight, 0));
        elenco.add(new Covid(context, xiniziale + 2 * covidMetaWidth, 3 * covidHight, 0));

    }

    private void lv5(Context context, Point size, ArrayList<Covid> elenco) {

        float displayWidth = size.x/2;
        float covidMetaWidth = covidWidth / 2;
        float xiniziale = ((size.x / 2) - covidMetaWidth);


        elenco.add(new Covid(context, xiniziale, 1 * covidHight, 0));
        elenco.add(new Covid(context, xiniziale, 3 * covidHight, 0));


        elenco.add(new Covid(context, xiniziale - 2 * covidMetaWidth, 1 * covidHight, 2));
        elenco.add(new Covid(context, xiniziale + 2 * covidMetaWidth, 1 * covidHight, 1));
        elenco.add(new Covid(context, xiniziale - 4 * covidMetaWidth, 1 * covidHight, 1));
        elenco.add(new Covid(context, xiniziale + 4 * covidMetaWidth, 1 * covidHight, 2));

        elenco.add(new Covid(context, xiniziale - 2 * covidMetaWidth, 2 * covidHight, 0));
        elenco.add(new Covid(context, xiniziale + 2 * covidMetaWidth, 2 * covidHight, 0));
        elenco.add(new Covid(context, xiniziale - 6 * covidMetaWidth, 2 * covidHight, 2));
        elenco.add(new Covid(context, xiniziale + 6 * covidMetaWidth, 2 * covidHight, 1));

        elenco.add(new Covid(context, xiniziale - 2 * covidMetaWidth, 3 * covidHight, 2));
        elenco.add(new Covid(context, xiniziale + 2 * covidMetaWidth, 3 * covidHight, 1));
        elenco.add(new Covid(context, xiniziale - 4 * covidMetaWidth, 3 * covidHight, 1));
        elenco.add(new Covid(context, xiniziale + 4 * covidMetaWidth, 3 * covidHight, 2));

    }
}
