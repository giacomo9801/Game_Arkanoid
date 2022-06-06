package com.example.android.arkanoid.Game.Editor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.arkanoid.R;

public class Editor extends AppCompatActivity {

    Button brickButton, powerUpButton, lifeButton, ballButton;
    Button startButton;

    //gestiamo i vari pulsanti presenti nell'editor
    //ogni pulsante viene gestito in una classe
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        brickButton = findViewById(R.id.buttonBrick);
        brickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameForOption, new FragmentBrick());
                ft.commit();
            }
        });

        startButton = findViewById(R.id.iniziaEditor);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameForOption, new FragmentSummary());
                ft.commit();
            }
        });

        powerUpButton = findViewById(R.id.buttonPup);
        powerUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameForOption, new FragmentPowerUp());
                ft.commit();
            }
        });

        lifeButton = findViewById(R.id.buttonNumeroVite);
        lifeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameForOption, new FragmentLife());
                ft.commit();
            }
        });

        ballButton = findViewById(R.id.buttonSpeedBall);
        ballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameForOption, new FragmentBall());
                ft.commit();
            }
        });
    }
}
