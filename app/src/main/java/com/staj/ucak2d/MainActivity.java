package com.staj.ucak2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean isMute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        TextView enSkorTxt = findViewById(R.id.enSkorTxt);
        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        enSkorTxt.setText("En Yüksek Skor: " + prefs.getInt("enskor", 0));

        isMute = prefs.getBoolean("isMute", false);
        final ImageView volumaCtrl = findViewById(R.id.volumeCtrl);

        if(isMute)
            volumaCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
        else
            volumaCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);

        volumaCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMute = !isMute;
                if(isMute) {
                    volumaCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
                } else
                    volumaCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMute", isMute);
                editor.apply();
            }
        });
        }
    }
