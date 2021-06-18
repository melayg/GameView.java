package com.staj.ucak2d;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ArkaPlan {
    int x=0,y=0;
    Bitmap arkaplan;

    ArkaPlan(int screenX, int screenY, Resources res) {

        arkaplan = BitmapFactory.decodeResource(res, R.drawable.arkaplan);
        arkaplan = Bitmap.createScaledBitmap(arkaplan, screenX, screenY,false);

    }


}
