package com.staj.ucak2d;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OyunArayuz extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean Basla, Bitir = false;
    private int screenX, screenY, score = 0;
    public static float ekranOraniX, ekranOraniY;
    private Paint paint;
    private Ucak[] ucaklar;
    private Ucak2[]  ucaklar2;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private List<Fuze> fuzeler;
    private int ses;
    private Savar savar;
    private GameActivity activity;
    private ArkaPlan arkaPlan1, arkaPlan2;


    public OyunArayuz(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME).build();

            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();

        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        ses = soundPool.load(activity, R.raw.ates, 1);

        this.screenX = screenX;
        this.screenY = screenY;
        ekranOraniX = 1920f / screenX;
        ekranOraniY = 1080f / screenY;


        arkaPlan1 = new ArkaPlan(screenX, screenY, getResources());
        arkaPlan2 = new ArkaPlan(screenX, screenY, getResources());

        savar = new Savar(this, screenY, getResources());

        fuzeler = new ArrayList<>();

        arkaPlan2.x = screenX;
        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        ucaklar = new Ucak[4];
        ucaklar2 = new Ucak2[4];

        for (int i = 0; i < 4; i++) {

            Ucak ucak = new Ucak(getResources());
            ucaklar[i] = ucak;

        }
        for (int i = 0; i < 4; i++) {

            Ucak2 ucak = new Ucak2(getResources());
            ucaklar2[i] = ucak;

        }
        random = new Random();


    }

    @Override
    public void run() {
        while (Basla) {
            update();
            draw();
            sleep();

        }

    }

    private void update() {

        arkaPlan1.x -= 10 * ekranOraniX;
        arkaPlan2.x -= 10 * ekranOraniX;

        if (arkaPlan1.x + arkaPlan1.arkaplan.getWidth() < 0) {
            arkaPlan1.x = screenX;
        }
        if (arkaPlan2.x + arkaPlan2.arkaplan.getWidth() < 0) {
            arkaPlan2.x = screenX;
        }
        if (savar.isGoingUp)
            savar.y -= 30 * ekranOraniY;
        else
            savar.y += 30 * ekranOraniY;

        if (savar.y < 0)
            savar.y = 0;

        if (savar.y >= screenY - savar.height)
            savar.y = screenY - savar.height;

        List<Fuze> trash = new ArrayList<>();

        for (Fuze fuze : fuzeler) {

            if (fuze.x > screenX)
                trash.add(fuze);

            fuze.x += 50 * ekranOraniX;

            for (Ucak ucak : ucaklar) {

                if (Rect.intersects(ucak.getCollisionShape(), fuze.getCollisionShape())) {
                    score++;
                    ucak.x = -500;
                    fuze.x = screenX + 500;
                    ucak.wasShot = true;
                }

            }
            for (Ucak2 ucak : ucaklar2) {

                if (Rect.intersects(ucak.getCollisionShape(), fuze.getCollisionShape())) {
                    score++;
                    ucak.x = -500;
                    fuze.x = screenX + 500;
                    ucak.wasShot = true;
                }

            }

        }

        for (Fuze fuze : trash)
            fuzeler.remove(fuze);

        for (Ucak ucak : ucaklar) {
            ucak.x -= ucak.speed;

            if (ucak.x + ucak.width < 0) {

                if (!ucak.wasShot) {
                    Bitir = true;
                    return;
                }

                int bound = (int) (20 * ekranOraniX);
                ucak.speed = random.nextInt(bound);

                if (ucak.speed < 7 * ekranOraniX)
                    ucak.speed = (int) (7 * ekranOraniX);

                ucak.x = screenX;
                ucak.y = random.nextInt(screenY - ucak.height);
                ucak.wasShot = false;

            }

            if (Rect.intersects(ucak.getCollisionShape(), savar.getCollisionShape())) {

                Bitir = true;
                return;


            }

        }
        for (Ucak2 ucak : ucaklar2) {
            ucak.x -= ucak.speed;

            if (ucak.x + ucak.width < 0) {

                if (!ucak.wasShot) {
                    Bitir = true;
                    return;
                }

                int bound = (int) (20 * ekranOraniX);
                ucak.speed = random.nextInt(bound);

                if (ucak.speed < 7 * ekranOraniX)
                    ucak.speed = (int) (7 * ekranOraniX);

                ucak.x = screenX;
                ucak.y = random.nextInt(screenY - ucak.height);
                ucak.wasShot = false;

            }

            if (Rect.intersects(ucak.getCollisionShape(), savar.getCollisionShape())) {

                Bitir = true;
                return;


            }

        }

    }

    private void draw() {

        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(arkaPlan1.arkaplan, arkaPlan1.x, arkaPlan1.y, paint);
            canvas.drawBitmap(arkaPlan2.arkaplan, arkaPlan2.x, arkaPlan2.y, paint);

            for (Ucak ucak : ucaklar)
                canvas.drawBitmap(ucak.getUcak(), ucak.x, ucak.y, paint);
            for (Ucak2 ucak : ucaklar2)
                canvas.drawBitmap(ucak.getUcak(), ucak.x, ucak.y, paint);

            canvas.drawText(score + "", screenX / 2f, 164, paint);

            if (Bitir) {
                Basla = false;
                canvas.drawBitmap(savar.getOldu(), savar.x, savar.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfEnSkor();
                waitBeforeExiting();

                return;
            }

            canvas.drawBitmap(savar.getSavar(), savar.x, savar.y, paint);

            for (Fuze fuze : fuzeler) {
                canvas.drawBitmap(fuze.fuze, fuze.x, fuze.y, paint);
            }
            getHolder().unlockCanvasAndPost(canvas);


        }

    }

    private void waitBeforeExiting() {

        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void saveIfEnSkor() {

        if (prefs.getInt("enskor", 0) < score) {

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("enskor", score);
            editor.apply();
        }
    }
    private void sleep() {
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void resume() {

        Basla = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            Basla = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    savar.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                savar.isGoingUp = false;
                if (event.getX() > screenX / 2)
                    savar.toAtes++;
                break;
        }
        return true;
    }
    public void newFuze() {

        if (!prefs.getBoolean("isMute", false))
            soundPool.play(ses, 1, 1, 0, 0, 1);
        Fuze fuze = new Fuze(getResources());
        fuze.x = savar.x + savar.width;
        fuze.y = savar.y + (savar.height / 2);
        fuzeler.add(fuze);
    }
}
