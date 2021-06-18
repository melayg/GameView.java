package com.staj.ucak2d;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.staj.ucak2d.OyunArayuz.ekranOraniX;
import static com.staj.ucak2d.OyunArayuz.ekranOraniY;

public class Ucak {
    public int speed = 10;
    public boolean wasShot = true;
    int x=0, y, width, height, ucakCounter = 1;
    Bitmap ucak1, ucak2, ucak3, ucak4;

    Ucak(Resources res) {

        ucak1 = BitmapFactory.decodeResource(res, R.drawable.ucak1);
        ucak2 = BitmapFactory.decodeResource(res, R.drawable.ucak2);
        ucak3 = BitmapFactory.decodeResource(res, R.drawable.ucak3);
        ucak4 = BitmapFactory.decodeResource(res, R.drawable.ucak4);

        width = ucak1.getWidth();
        height = ucak1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * ekranOraniX);
        height = (int) (height * ekranOraniY);

        ucak1 = Bitmap.createScaledBitmap(ucak1, width, height, false);
        ucak2 = Bitmap.createScaledBitmap(ucak2, width, height, false);
        ucak3 = Bitmap.createScaledBitmap(ucak3, width, height, false);
        ucak4 = Bitmap.createScaledBitmap(ucak4, width, height, false);

        y = -height;


    }

    Bitmap getUcak() {
        if (ucakCounter == 1) {
            ucakCounter++;
            return ucak1;
        }
        if (ucakCounter == 2) {
            ucakCounter++;
            return ucak2;
        }
        if (ucakCounter == 3) {
            ucakCounter++;
            return ucak3;
        }
        ucakCounter = 1;
        return ucak4;
    }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }

}
