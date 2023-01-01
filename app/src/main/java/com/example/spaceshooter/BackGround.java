package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BackGround {

    int x = 0, y = 0;
    Bitmap background;

    BackGround(int P, Context context, int screenSizeX, int screenSizeY) {

        if (P == 1) {
            background = BitmapFactory.decodeResource(context.getResources(), R.drawable.k1);
            background = Bitmap.createScaledBitmap(background, screenSizeX, screenSizeY, false);
        }
        if (P == 2) {
            background = BitmapFactory.decodeResource(context.getResources(), R.drawable.k2);
            background = Bitmap.createScaledBitmap(background, screenSizeX, screenSizeY, false);
        }
        if (P == 3) {
            background = BitmapFactory.decodeResource(context.getResources(), R.drawable.k3);
            background = Bitmap.createScaledBitmap(background, screenSizeX, screenSizeY, false);
        }
        if (P == 4) {
            background = BitmapFactory.decodeResource(context.getResources(), R.drawable.k4);
            background = Bitmap.createScaledBitmap(background, screenSizeX, screenSizeY, false);
        }
        if (P == 5) {
            background = BitmapFactory.decodeResource(context.getResources(), R.drawable.k5);
            background = Bitmap.createScaledBitmap(background, screenSizeX, screenSizeY, false);
        }
        if (P == 6) {
            background = BitmapFactory.decodeResource(context.getResources(), R.drawable.k6);
            background = Bitmap.createScaledBitmap(background, screenSizeX, screenSizeY, false);
        }
    }
}
