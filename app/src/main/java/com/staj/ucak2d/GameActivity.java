package com.staj.ucak2d;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    private OyunArayuz oyunArayuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point= new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        oyunArayuz = new OyunArayuz(this, point.x, point.y);

        setContentView(oyunArayuz);

    }

    @Override
    protected void onPause() {
        super.onPause();
        oyunArayuz.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        oyunArayuz.resume();
    }
}