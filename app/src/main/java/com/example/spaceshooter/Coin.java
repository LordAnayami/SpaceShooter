package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Coin {


    private Bitmap mBitmap;
    private final int mX;
    private int mY;
    private final Rect mCollision;
    private final int mScreenSizeX;
    private final int mScreenSizeY;
    private final int mMaxX;
    private final int mMaxY;
    private final int mSpeed;


    private final SoundPlayer mSoundPlayer;


    public Coin(Context context, int screenSizeX, int screenSizeY, SoundPlayer soundPlayer, int X, int Y) {
        mScreenSizeX = screenSizeX;
        mScreenSizeY = screenSizeY;
        mSoundPlayer = soundPlayer;
        Random random = new Random();
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin1);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * 3 / 5, mBitmap.getHeight() * 3 / 5, false);
        mSpeed = random.nextInt(2) + 1;
        mMaxX = screenSizeX - mBitmap.getWidth();
        mMaxY = screenSizeY - mBitmap.getHeight();
        mX = X;
        mY = Y;


        mCollision = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
    }

    public void update() {
        mY += 7 * mSpeed;
        mCollision.left = mX;
        mCollision.top = mY;
        mCollision.right = mX + mBitmap.getWidth();
        mCollision.bottom = mY + mBitmap.getHeight();
    }

    public Rect getCollision() {
        return mCollision;
    }

    public void destroy() {
        mY = mScreenSizeY + 1;
        GameView.SCORE += 5;
    }

    public void playSound() {
        mSoundPlayer.playItem();
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }
}

