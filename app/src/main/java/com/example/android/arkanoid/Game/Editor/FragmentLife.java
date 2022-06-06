package com.example.android.arkanoid.Game.Editor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.arkanoid.R;

import java.util.Objects;

public class FragmentLife extends Fragment {
    TextView tvProgressLabel;
    SeekBar seekBar;
    private final static String preferen_life = "pref_lif";

    public FragmentLife() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_life, container, false);
        SharedPreferences readpref = Objects.requireNonNull(getActivity()).getSharedPreferences(preferen_life, Context.MODE_PRIVATE);
        int read_progress_life = readpref.getInt("progress_life", 1);
        Log.v("LETTURA SHARE PREFERENCES VITE", "" + read_progress_life);


        seekBar = view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        int progress = seekBar.getProgress();
        tvProgressLabel = view.findViewById(R.id.textView1);
        tvProgressLabel.setText(getString(R.string.vite) + " " + progress);

        seekBar.setProgress(read_progress_life);
        return view;
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //viene aggiornata a ogni movimento della barra
            tvProgressLabel.setText(getString(R.string.vite) + " " + progress);
            SharedPreferences pref_life = Objects.requireNonNull(getActivity()).getSharedPreferences(preferen_life, Context.MODE_PRIVATE);
            SharedPreferences.Editor edt = pref_life.edit();
            edt.putInt("progress_life", progress);
            Log.v("SCRITTURA SHARE PREFERENCES VELOCITA' PALLA", "" + progress);
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


