package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class Enemy {

    private Bitmap mBitmap;
    private final int mX;
    private int mY;
    private final Rect mCollision;
    private final int mScreenSizeX;
    private final int mScreenSizeY;
    private final int mMaxX;
    private final int mMaxY;
    private int mHP;
    private final int mSpeed;
    private boolean ifDestroyed = false;
    private final ArrayList<Laser> mLasers;
    private final Context mContext;
    private final SoundPlayer mSoundPlayer;
    private int H = 0;
    private int ES = 0;


    public Enemy(Context context, int screenSizeX, int screenSizeY, SoundPlayer soundPlayer) {
        mScreenSizeX = screenSizeX;
        mScreenSizeY = screenSizeY;
        mSoundPlayer = soundPlayer;
        mContext = context;
        if (Game.getItem(4) == true) {
            H = -1;
        } else {
            H = 0;
        }
        if (Game.getItem(6) == true) {
            ES = -1;
        } else {
            ES = 0;
        }
        mHP = (int) ((2 + H) * Game.getDifficulty());
        Random random = new Random();
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_red_1);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * 3 / 5, mBitmap.getHeight() * 3 / 5, false);
        mSpeed = random.nextInt(3) + 2 + ES;
        mMaxX = screenSizeX - mBitmap.getWidth();
        mMaxY = screenSizeY - mBitmap.getHeight();
        mX = random.nextInt(mMaxX);
        mY = 0 - mBitmap.getHeight();
        mLasers = new ArrayList<>();
        mCollision = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
    }

    public void update() {
        mY += 3 * mSpeed;
        mCollision.left = mX;
        mCollision.top = mY;
        mCollision.right = mX + mBitmap.getWidth();
        mCollision.bottom = mY + mBitmap.getHeight();
        for (Laser l : mLasers) {
            l.update();
        }
        boolean deleting = true;
        while (deleting) {
            if (mLasers.size() != 0) {
                if (mLasers.get(0).getY() < 0) {
                    mLasers.remove(0);
                }
            }
            if (mLasers.size() == 0 || mLasers.get(0).getY() >= 0) {
                deleting = false;
            }
        }
    }

    public ArrayList<Laser> getLasers() {
        return mLasers;
    }

    public void fire() {
        mLasers.add(new Laser(mContext, mScreenSizeX, mScreenSizeY, mX, mY, mBitmap, true));
        mSoundPlayer.playLaser();
    }

    public Rect getCollision() {
        return mCollision;
    }

    public void hit() {
        if (--mHP == 0) {
            GameView.maygetItem = true;
            GameView.SCORE += 20;
            GameView.ENEMY_DESTROYED++;
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

    public boolean getDestroyed() {
        return ifDestroyed;
    }
}

