package com.example.android.arkanoid.Game.Editor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.android.arkanoid.R;

import java.util.Objects;


public class FragmentBrick extends Fragment implements View.OnClickListener {

    Button lv1, lv2, lv3, lv4, lv5, lv6, lv7, lv8, lv9, lv10;
    ImageView imageLevel;
    private final static String preferen_brick = "pref_brick";
    int level = 0;

    public FragmentBrick() {

    }

    //impostiamo ogni mattone come se fosse un livello da scegliere
    //verrà visualizzata un anteprima del livello in base al mattone premuto
    //a ogni pressione del mattone i dati vengono salvati
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brick, container, false);
        imageLevel = view.findViewById(R.id.imageLevel);
        SharedPreferences readlevel = Objects.requireNonNull(getActivity()).getSharedPreferences(preferen_brick, Context.MODE_PRIVATE);
        int read_levelbrick = readlevel.getInt("level", 1);
        Log.v("LETTURA SHARE PREFERENCES LIVELLO", "" + read_levelbrick);
        switch (read_levelbrick) {
            case 1:
                imageLevel.setImageResource(R.drawable.lv1);
                break;
            case 2:
                imageLevel.setImageResource(R.drawable.lv2);
                break;
            case 3:
                imageLevel.setImageResource(R.drawable.lv3);
                break;
            case 4:
                imageLevel.setImageResource(R.drawable.lv4);
                break;
            case 5:
                imageLevel.setImageResource(R.drawable.lv5);
                break;
            case 6:
                imageLevel.setImageResource(R.drawable.lv6);
                break;
            case 7:
                imageLevel.setImageResource(R.drawable.lv7);
                break;
            case 8:
                imageLevel.setImageResource(R.drawable.lv8);
                break;
            case 9:
                imageLevel.setImageResource(R.drawable.lv9);
                break;
            case 10:
                imageLevel.setImageResource(R.drawable.lv10);
                break;
        }


        lv1 = view.findViewById(R.id.lv1);


        SharedPreferences pref_brick = Objects.requireNonNull(getActivity()).getSharedPreferences(preferen_brick, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref_brick.edit();
        lv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLevel.setImageResource(R.drawable.lv1);
                level = 1;
                edt.putInt("level", level);
                edt.apply();
                Log.v("LIVELLO : ", "" + level);
            }

        });

        lv2 = view.findViewById(R.id.lv2);
        lv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLevel.setImageResource(R.drawable.lv2);
                level = 2;
                edt.putInt("level", level);
                edt.apply();
                Log.v("LIVELLO : ", "" + level);
            }
        });


        lv3 = view.findViewById(R.id.lv3);
        lv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLevel.setImageResource(R.drawable.lv3);
                level = 3;
                edt.putInt("level", level);
                edt.apply();
                Log.v("LIVELLO : ", "" + level);
            }
        });

        lv4 = view.findViewById(R.id.lv4);
        lv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLevel.setImageResource(R.drawable.lv4);
                level = 4;
                edt.putInt("level", level);
                edt.apply();
                Log.v("LIVELLO : ", "" + level);
            }
        });

        lv5 = view.findViewById(R.id.lv5);
        lv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLevel.setImageResource(R.drawable.lv5);
                level = 5;
                edt.putInt("level", level);
                edt.apply();
                Log.v("LIVELLO : ", "" + level);
            }
        });

        lv6 = view.findViewById(R.id.lv6);
        lv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLevel.setImageResource(R.drawable.lv6);
                level = 6;
                edt.putInt("level", level);
                edt.apply();
                Log.v("LIVELLO : ", "" + level);
            }
        });

        lv7 = view.findViewById(R.id.lv7);
        lv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLevel.setImageResource(R.drawable.lv7);
                level = 7;
                edt.putInt("level", level);
                edt.apply();
                Log.v("LIVELLO : ", "" + level);
            }
        });

        lv8 = view.findViewById(R.id.lv8);
        lv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLevel.setImageResource(R.drawable.lv8);
                level = 8;
                edt.putInt("level", level);
                edt.apply();
                Log.v("LIVELLO : ", "" + level);
            }
        });

        lv9 = view.findViewById(R.id.lv9);
        lv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLevel.setImageResource(R.drawable.lv9);
                level = 9;
                edt.putInt("level", level);
                edt.apply();
                Log.v("LIVELLO : ", "" + level);
            }
        });

        lv10 = view.findViewById(R.id.lv10);
        lv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLevel.setImageResource(R.drawable.lv10);
                level = 10;
                edt.putInt("level", level);
                edt.apply();
                Log.v("LIVELLO : ", "" + level);
            }
        });
        edt.apply();

        return view;
    }


    @Override
    public void onClick(View v) {

    }
}