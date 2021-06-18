package com.staj.ucak2d;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.staj.ucak2d.OyunArayuz.ekranOraniX;
import static com.staj.ucak2d.OyunArayuz.ekranOraniY;

public class Fuze {

    int x, y, width, height;
    Bitmap fuze;

    Fuze(Resources res) {
        fuze = BitmapFactory.decodeResource(res, R.drawable.fuze);

        width = fuze.getWidth();
        height = fuze.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width * ekranOraniX);
        height = (int) (height * ekranOraniY);

        fuze = Bitmap.createScaledBitmap(fuze, width, height, false);
    }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }

}
