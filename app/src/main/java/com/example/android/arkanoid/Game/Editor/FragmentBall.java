package com.example.android.arkanoid.Game.Editor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.arkanoid.R;

import java.util.Objects;


public class FragmentBall extends Fragment {
    TextView ProgressLabelspeed;
    SeekBar seekBarspeed;
    private final static String pref_ball = "pref_ball";

    public FragmentBall() {
    }

    //utilizziamo una seekbar per far scegliere all'utente la velocità della pallina
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ball, container, false);
        SharedPreferences readpref = Objects.requireNonNull(getActivity()).getSharedPreferences(pref_ball,Context.MODE_PRIVATE);
        int read_progress_ball = readpref.getInt("progress", 1);
        Log.v("LETTURA SHARE PREFERENCES VELOCITA' PALLA",""+ read_progress_ball);

        seekBarspeed = view.findViewById(R.id.seekBarball);
        seekBarspeed.setOnSeekBarChangeListener(seekBarChangeListener);

        int progress = seekBarspeed.getProgress();//valore della seekbar
        ProgressLabelspeed = view.findViewById(R.id.textViewspeed);
        if(progress==1){
            ProgressLabelspeed.setText(getString(R.string.velocita_bassa));
        }
        seekBarspeed.setProgress(read_progress_ball);

        return view;
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener(){
        //in base al valore selezionato verrà impostata una scritta differente
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress==1){
                ProgressLabelspeed.setText(getString(R.string.velocita_bassa));
            }else if(progress==2){
                ProgressLabelspeed.setText(getString(R.string.velocita_media));
            }else if(progress==3){
                ProgressLabelspeed.setText(getString(R.string.velocita_alta));
            }
            SharedPreferences pref = Objects.requireNonNull(getActivity()).getSharedPreferences(pref_ball,Context.MODE_PRIVATE);
            SharedPreferences.Editor edt = pref.edit();
            edt.putInt("progress", progress);
            Log.v("SCRITTURA SHARE PREFERENCES VELOCITA' PALLA",""+ progress);
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