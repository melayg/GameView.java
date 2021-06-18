package com.staj.ucak2d;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.staj.ucak2d.OyunArayuz.ekranOraniX;
import static com.staj.ucak2d.OyunArayuz.ekranOraniY;

public class Savar {
    int toAtes = 0;
    boolean isGoingUp = false;
    int x, y, width, height, wingCounter = 0, atesCounter = 1;
    Bitmap savar1, savar2, ates1, ates2, ates3, ates4, ates5, oldu;
    private OyunArayuz oyunArayuz;

    Savar(OyunArayuz oyunArayuz, int screenY, Resources res) {
        this.oyunArayuz = oyunArayuz;
        savar1 = BitmapFactory.decodeResource(res, R.drawable.uc);
        savar2 = BitmapFactory.decodeResource(res, R.drawable.uc2);

        width = savar1.getWidth();
        height = savar1.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width * ekranOraniX);
        height = (int) (height * ekranOraniY);

        savar1 = Bitmap.createScaledBitmap(savar1, width, height, false);
        savar2 = Bitmap.createScaledBitmap(savar2, width, height, false);

        ates1 = BitmapFactory.decodeResource(res, R.drawable.ates1);
        ates2 = BitmapFactory.decodeResource(res, R.drawable.ates2);
        ates3 = BitmapFactory.decodeResource(res, R.drawable.ates3);
        ates4 = BitmapFactory.decodeResource(res, R.drawable.ates4);
        ates5 = BitmapFactory.decodeResource(res, R.drawable.ates5);

        ates1 = Bitmap.createScaledBitmap(ates1, width, height, false);
        ates2 = Bitmap.createScaledBitmap(ates2, width, height, false);
        ates3 = Bitmap.createScaledBitmap(ates3, width, height, false);
        ates4 = Bitmap.createScaledBitmap(ates4, width, height, false);
        ates5 = Bitmap.createScaledBitmap(ates5, width, height, false);

        oldu = BitmapFactory.decodeResource(res, R.drawable.oldu);
        oldu = Bitmap.createScaledBitmap(oldu, width, height, false);


        y = screenY / 2;
        x = (int) (64 * ekranOraniX);

    }

    Bitmap getSavar() {

        if (toAtes != 0) {

            if (atesCounter == 1) {
                atesCounter++;
                return ates1;
            }
            if (atesCounter == 2) {
                atesCounter++;
                return ates2;
            }
            if (atesCounter == 3) {
                atesCounter++;
                return ates3;

            }
            if (atesCounter == 4) {
                atesCounter++;
                return ates4;
            }

            atesCounter = 1;
            toAtes--;
            oyunArayuz.newFuze();
            return ates5;

        }

        if (wingCounter == 0) {
            wingCounter++;
            return savar1;
        }

        wingCounter--;
        return savar2;

    }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }

    Bitmap getOldu() {
        return oldu;
    }

}
