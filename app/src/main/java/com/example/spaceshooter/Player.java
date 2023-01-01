package com.example.spaceshooter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class Player {

    private Bitmap mBitmap;
    private int mX;
    private int mY;
    private int mSpeed = 0;
    private final int mMaxX;
    private final int mMinX;
    private final int mMaxY;
    private final int mMinY;
    private final int mMargin = 16;
    private boolean mIsSteerLeft, mIsSteerRight, mIsSteerUp, mIsSteerDown;
    private final Rect mCollision;
    private final ArrayList<Laser> mLasers;
    private final SoundPlayer mSoundPlayer;
    private final Context mContext;
    private final int mScreenSizeX;
    private final int mScreenSizeY;

    public Player(Context context, int screenSizeX, int screenSizeY, SoundPlayer soundPlayer) {
        mScreenSizeX = screenSizeX;
        mScreenSizeY = screenSizeY;
        mContext = context;
        if (Game.getItem(3) == true) {
            mSpeed = 2;
        } else {
            mSpeed = 1;
        }
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship_1_blue);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * 3 / 5, mBitmap.getHeight() * 3 / 5, false);
        mMaxX = screenSizeX - mBitmap.getWidth();
        mMaxY = screenSizeY - mBitmap.getHeight();
        mMinX = 0;
        mMinY = 0;
        mX = screenSizeX / 2 - mBitmap.getWidth() / 2;
        mY = screenSizeY - mBitmap.getHeight() - mMargin;
        mLasers = new ArrayList<>();
        mSoundPlayer = soundPlayer;
        mCollision = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
    }

    public void update() {
        if (mIsSteerLeft) {
            mX -= 10 * mSpeed;
            if (mX < mMinX) {
                mX = mMinX;
            }
        } else if (mIsSteerRight) {
            mX += 10 * mSpeed;
            if (mX > mMaxX) {
                mX = mMaxX;
            }
        }
        if (mIsSteerUp) {
            mY -= 10 * mSpeed;
            if (mY < mMinY) {
                mY = mMinY;
            }
        } else if (mIsSteerDown) {
            mY += 10 * mSpeed;
            if (mY > mMaxY) {
                mY = mMaxY;
            }
        }
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
        mLasers.add(new Laser(mContext, mScreenSizeX, mScreenSizeY, mX, mY, mBitmap, false));
        mSoundPlayer.playLaser();
    }

    public Rect getCollision() {
        return mCollision;
    }

    public void playcrash() {
        mSoundPlayer.playExplode();
    }

    public void steerRight() {
        mIsSteerLeft = false;
        mIsSteerRight = true;

    }

    public void steerLeft() {
        mIsSteerRight = false;
        mIsSteerLeft = true;
    }

    public void stay() {
        mIsSteerRight = false;
        mIsSteerLeft = false;
        mIsSteerDown = false;
        mIsSteerUp = false;
    }

    public void steerUp() {
        mIsSteerDown = false;
        mIsSteerUp = true;
    }

    public void steerDown() {
        mIsSteerDown = true;
        mIsSteerUp = false;
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

