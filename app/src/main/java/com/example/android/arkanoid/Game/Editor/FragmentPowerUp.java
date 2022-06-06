package com.example.android.arkanoid.Game.Editor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.arkanoid.R;

import java.util.Objects;


public class FragmentPowerUp extends Fragment {
    TextView ProgressLabel;
    SeekBar seekBardroprate;
    SwitchCompat switch_laser, switch_expand, switch_devil, switch_small;
    private final static String pref_powerup = "pref_pwrup";

    public FragmentPowerUp() {

    }

    //sarà possibile selezionare i vari powerup attraverso uno switch
    //e selezionare il drop rate con la seekbar
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_power_up, container, false);
        switch_laser = view.findViewById(R.id.switchlaser);
        switch_expand = view.findViewById(R.id.switchexpand);
        switch_devil = view.findViewById(R.id.switchdevil);
        switch_small = view.findViewById(R.id.switchsmall);
        seekBardroprate = view.findViewById(R.id.seekBardroprate);
        //vengono letti i dati se salvati o impostati i valori di default
        SharedPreferences readpref = Objects.requireNonNull(getActivity()).getSharedPreferences(pref_powerup, Context.MODE_PRIVATE);
        boolean read_switchlaser = readpref.getBoolean("switch_laser", false);
        boolean read_switchexpand = readpref.getBoolean("switch_expand", false);
        boolean read_switchdevil = readpref.getBoolean("switch_devil", false);
        boolean read_switchsmall = readpref.getBoolean("switch_small", false);
        Log.v("LETTURA SHARE PREFERENCES POWERUP SWITCH LASER ", "" + read_switchlaser);
        Log.v("LETTURA SHARE PREFERENCES POWERUP SWITCH EXPAND ", "" + read_switchexpand);
        Log.v("LETTURA SHARE PREFERENCES POWERUP SWITCH DEVIL ", "" + read_switchdevil);
        Log.v("LETTURA SHARE PREFERENCES POWERUP SWITCH SMALL ", "" + read_switchsmall);


        SharedPreferences readpref_droprate = getActivity().getSharedPreferences(pref_powerup, Context.MODE_PRIVATE);
        int read_progress_droprate = readpref_droprate.getInt("progress_droprate", 1);
        Log.v("LETTURA SHARE PREFERENCES DROP RATE", "" + read_progress_droprate);

        seekBardroprate.setOnSeekBarChangeListener(seekBarChangeListener);
        int progress = seekBardroprate.getProgress();//valore della seekbar
        ProgressLabel = view.findViewById(R.id.textViewdroprate);
        if (progress == 1) {
            ProgressLabel.setText(getString(R.string.drop_basso));
        }


        //a ogni cambiamento vengono salvati i valori
        SharedPreferences pref = Objects.requireNonNull(getActivity()).getSharedPreferences(pref_powerup, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        switch_laser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edt.putBoolean("switch_laser", switch_laser.isChecked());
                Log.v("SCRITTURA SHARE PREFERENCES POWERUP SWITCH LASER ", "" + switch_laser.isChecked());
                edt.apply();
            }
        });
        switch_laser.setChecked(read_switchlaser);
        switch_expand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edt.putBoolean("switch_expand", switch_expand.isChecked());
                Log.v("SCRITTURA SHARE PREFERENCES POWERUP SWITCH EXPAND ", "" + switch_expand.isChecked());
                edt.apply();
            }
        });
        switch_expand.setChecked(read_switchexpand);
        switch_devil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edt.putBoolean("switch_devil", switch_devil.isChecked());
                Log.v("SCRITTURA SHARE PREFERENCES POWERUP SWITCH DEVIL ", "" + switch_devil.isChecked());
                edt.apply();
            }
        });
        switch_devil.setChecked(read_switchdevil);
        switch_small.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edt.putBoolean("switch_small", switch_small.isChecked());
                Log.v("SCRITTURA SHARE PREFERENCES POWERUP SWITCH SMALL ", "" + switch_small.isChecked());
                edt.apply();
            }
        });
        switch_small.setChecked(read_switchsmall);
        edt.putInt("progress", progress);
        edt.apply();
        seekBardroprate.setProgress(read_progress_droprate);
        return view;
    }

    //viene impostata una scritta differente in base al valore scelto
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress == 1) {
                ProgressLabel.setText(getString(R.string.drop_basso));
            } else if (progress == 2) {
                ProgressLabel.setText(getString(R.string.drop_medio));
            } else if (progress == 3) {
                ProgressLabel.setText(getString(R.string.drop_alto));
            }

            SharedPreferences pref2 = getActivity().getSharedPreferences(pref_powerup, Context.MODE_PRIVATE);
            SharedPreferences.Editor edt = pref2.edit();
            edt.putInt("progress_droprate", progress);
            edt.apply();

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };


}