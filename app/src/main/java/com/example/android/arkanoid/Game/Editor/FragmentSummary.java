package com.example.android.arkanoid.Game.Editor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.arkanoid.R;

import java.util.Objects;

public class FragmentSummary extends Fragment {

    private final static String preferen_life = "pref_lif";
    private final static String pref_ball = "pref_ball";
    private final static String preferen_brick = "pref_brick";
    private final static String pref_powerup = "pref_pwrup";
    Button avviaLivello;


    public FragmentSummary() {

    }

    //qui verrà visualizzato un riepilogo dei dati impostati dall'utente
    //sanno presenti tutti i valori presi dai fragment precedenti
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_summary, container, false); //pass the correct layout name for the fragment
        //lettura delle vite
        SharedPreferences readlifeshare = Objects.requireNonNull(getActivity()).getSharedPreferences(preferen_life, Context.MODE_PRIVATE);
        int read_life = readlifeshare.getInt("progress_life", 1);
        TextView life = (TextView) view.findViewById(R.id.textViewvitesetsummary);
        Log.v("LETTURA SHARE PREFERENCES VITE", "" + read_life);
        life.setText("" + read_life);

        //lettura velocità pallina
        SharedPreferences readspeedshare = Objects.requireNonNull(getActivity()).getSharedPreferences(pref_ball, Context.MODE_PRIVATE);
        int read_speed = readspeedshare.getInt("progress", 1);
        TextView speed = (TextView) view.findViewById(R.id.textViewspeedsetsummary);
        Log.v("LETTURA SHARE PREFERENCES VELOCITA'", "" + read_speed);
        if (read_speed == 1) {
            speed.setText(getString(R.string.velocita_bassa));
        } else if (read_speed == 2) {
            speed.setText(getString(R.string.velocita_media));
        } else if (read_speed == 3) {
            speed.setText(getString(R.string.velocita_alta));
        }

        //lettura del livello scelto
        SharedPreferences readlevelshare = Objects.requireNonNull(getActivity()).getSharedPreferences(preferen_brick, Context.MODE_PRIVATE);
        int read_level = readlevelshare.getInt("level", 1);
        TextView level = (TextView) view.findViewById(R.id.textViewlevelsetsummary);
        ImageView imglevl = (ImageView) view.findViewById(R.id.imglevelsummary);
        Log.v("LETTURA SHARE PREFERENCES LIVELLO", "" + read_level);
        level.setText("" + read_level);

        //imposto una mini-preview del livello scelto, così si facilita l'utente nel ricordarsi la scelta
        if (read_level == 1) {
            imglevl.setImageResource(R.drawable.lv1);
        } else if (read_level == 2) {
            imglevl.setImageResource(R.drawable.lv2);
        } else if (read_level == 3) {
            imglevl.setImageResource(R.drawable.lv3);
        } else if (read_level == 4) {
            imglevl.setImageResource(R.drawable.lv4);
        } else if (read_level == 5) {
            imglevl.setImageResource(R.drawable.lv5);
        } else if (read_level == 6) {
            imglevl.setImageResource(R.drawable.lv6);
        } else if (read_level == 7) {
            imglevl.setImageResource(R.drawable.lv7);
        } else if (read_level == 8) {
            imglevl.setImageResource(R.drawable.lv8);
        } else if (read_level == 9) {
            imglevl.setImageResource(R.drawable.lv9);
        } else if (read_level == 10) {
            imglevl.setImageResource(R.drawable.lv10);
        }


        //lettura dei powerup e del drop rate
        SharedPreferences readpoweruphare = Objects.requireNonNull(getActivity()).getSharedPreferences(pref_powerup, Context.MODE_PRIVATE);
        boolean read_laser = readpoweruphare.getBoolean("switch_laser", false);
        boolean read_expand = readpoweruphare.getBoolean("switch_expand", false);
        boolean read_devil = readpoweruphare.getBoolean("switch_devil", false);
        boolean read_small = readpoweruphare.getBoolean("switch_small", false);
        int read_droprate = readpoweruphare.getInt("progress_droprate", 1);

        TextView laserswitch = (TextView) view.findViewById(R.id.textViewprwlaserset);
        TextView expandswitch = (TextView) view.findViewById(R.id.textViewprwexpandset);
        TextView devilswitch = (TextView) view.findViewById(R.id.textViewprwdevilset);
        TextView smallswitch = (TextView) view.findViewById(R.id.textViewprwsmallset);
        TextView droprate = (TextView) view.findViewById(R.id.textViewdropratesetsummary);
        Log.v("LETTURA SHARE PREFERENCES LIVELLO", "" + read_level);

        //imposto colori differenti se il powerup è attivo o meno
        //rosso disattivo, giallo attivo
        if (!read_laser) {
            laserswitch.setText(getString(R.string.disattivo));
            laserswitch.setTextColor(getResources().getColor(R.color.red_400, null));
        } else {
            laserswitch.setText(getString(R.string.attivo));
            laserswitch.setTextColor(getResources().getColor(R.color.yellow_400, null));
        }

        if (!read_expand) {
            expandswitch.setText(getString(R.string.disattivo));
            expandswitch.setTextColor(getResources().getColor(R.color.red_400, null));
        } else {
            expandswitch.setText(getString(R.string.attivo));
            expandswitch.setTextColor(getResources().getColor(R.color.yellow_400, null));
        }

        if (!read_devil) {
            devilswitch.setText(getString(R.string.disattivo));
            devilswitch.setTextColor(getResources().getColor(R.color.red_400, null));
        } else {
            devilswitch.setText(getString(R.string.attivo));
            devilswitch.setTextColor(getResources().getColor(R.color.yellow_400, null));
        }

        if (!read_small) {
            smallswitch.setText(getString(R.string.disattivo));
            smallswitch.setTextColor(getResources().getColor(R.color.red_400, null));
        } else {
            smallswitch.setText(getString(R.string.attivo));
            smallswitch.setTextColor(getResources().getColor(R.color.yellow_400, null));
        }


        if (read_droprate == 1) {
            droprate.setText(getString(R.string.basso));
        } else if (read_droprate == 2) {
            droprate.setText(getString(R.string.medio));
        } else if (read_droprate == 3) {
            droprate.setText(getString(R.string.alto));
        }

        //avvio del livello personalizzato scelto dall'utente
        avviaLivello = view.findViewById(R.id.avvia_livello);
        avviaLivello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivityEditor.class);
                startActivity(intent);
            }
        });

        return view;
    }

}