package com.example.spaceshooter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Meteor {

    public boolean ifDestroyed = false;
    private Bitmap mBitmap;
    private final int mX;
    private int mY;
    private final int mMaxX;
    private final int mMinX;
    private final int mMaxY;
    private final int mMinY;
    private final int mSpeed;
    private final Rect mCollision;
    private final int mScreenSizeX;
    private final int mScreenSizeY;
    private int mHP;
    private final SoundPlayer mSoundPlayer;

    public Meteor(Context context, int screenSizeX, int screenSizeY, SoundPlayer soundPlayer) {
        mScreenSizeX = screenSizeX;
        mScreenSizeY = screenSizeY;
        mSoundPlayer = soundPlayer;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.meteor_1);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * 3 / 5, mBitmap.getHeight() * 3 / 5, false);
        mMaxX = screenSizeX - mBitmap.getWidth();
        mMaxY = screenSizeY - mBitmap.getHeight();
        mMinX = 0;
        mMinY = 0;
        mHP = (int) ((3) * Game.getDifficulty());
        Random random = new Random();
        mSpeed = random.nextInt(3) + 1;
        mX = random.nextInt(mMaxX);
        mY = 0 - mBitmap.getHeight();
        mCollision = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
    }

    public void update() {
        mY += 3 * mSpeed;
        mCollision.left = mX;
        mCollision.top = mY;
        mCollision.right = mX + mBitmap.getWidth();
        mCollision.bottom = mY + mBitmap.getHeight();
    }

    public Rect getCollision() {
        return mCollision;
    }

    public void hit() {
        if (--mHP == 0) {
            GameView.maygetItem = true;
            GameView.SCORE += 10;
            GameView.METEOR_DESTROYED++;
            ifDestroyed = true;
            mSoundPlayer.playCrash();
        } else {
            mSoundPlayer.playExplode();
        }
    }

    public void destroy() {
        mY = mScreenSizeY + 1;
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
