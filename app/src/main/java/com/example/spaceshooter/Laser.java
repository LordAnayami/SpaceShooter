package com.example.spaceshooter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Laser {
    private Bitmap mBitmap;
    private final int mX;
    private int mY;
    private final Rect mCollision;
    private final int mScreenSizeX;
    private final int mScreenSizeY;
    private final boolean mIsEnemy;

    public Laser(Context context, int screenSizeX, int screenSizeY, int spaceShipX, int spaceShipY, Bitmap spaceShip, boolean isEnemy) {
        mScreenSizeX = screenSizeX;
        mScreenSizeY = screenSizeY;
        mIsEnemy = isEnemy;
        if (isEnemy == false) {
            mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_1);
        }
        if (isEnemy == true) {
            mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_2);
        }
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * 3 / 5, mBitmap.getHeight() * 3 / 5, false);
        mX = spaceShipX + spaceShip.getWidth() / 2 - mBitmap.getWidth() / 2;
        if (mIsEnemy) {
            mY = spaceShipY + mBitmap.getHeight();
        } else {
            mY = spaceShipY - mBitmap.getHeight();
        }
        mCollision = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
    }

    public void update() {
        if (mIsEnemy) {
            mY += mBitmap.getHeight() / 3;
            mCollision.left = mX;
            mCollision.top = mY;
            mCollision.right = mX + mBitmap.getWidth();
            mCollision.bottom = mY + mBitmap.getHeight();
        } else {
            mY -= mBitmap.getHeight() / 3;
            mCollision.left = mX;
            mCollision.top = mY;
            mCollision.right = mX + mBitmap.getWidth();
            mCollision.bottom = mY + mBitmap.getHeight();
        }

    }

    public Rect getCollision() {
        return mCollision;
    }

    public void destroy() {
        if (mIsEnemy) {
            mY = mScreenSizeY + 50;
        } else {
            mY = 0 - mBitmap.getHeight();
        }
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

    public void updateY() {
        mY += mBitmap.getHeight() / 3;
        mCollision.left = mX;
        mCollision.top = mY;
        mCollision.right = mX + mBitmap.getWidth();
        mCollision.bottom = mY + mBitmap.getHeight();
    }
}
