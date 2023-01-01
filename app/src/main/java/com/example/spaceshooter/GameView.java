package com.example.spaceshooter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    public static boolean up = false;
    public static boolean down = false;
    public static boolean right = false;
    public static boolean left = false;
    public static boolean stay = false;
    public static int SCORE = 0;
    public static int METEOR_DESTROYED = 0;
    public static int ENEMY_DESTROYED = 0;
    public static boolean maygetItem = false;
    private int Level = 0;
    private boolean enemyActive = false;
    private boolean enemy2Active = false;
    private boolean enemy3Active = false;
    private boolean meteorActive = false;
    private int meteorNumber, enemyNumber, enemy2Number, enemy3Number;
    private int meteorsExists, enemyExists, enemy2Exists, enemy3Exists, itemsExists;
    private boolean deleting;
    private boolean win = false;
    private Thread mGameThread;
    private volatile boolean mIsPlaying;
    private Player mPlayer;
    private Paint mPaint;
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private ArrayList<Laser> mdellatedLasers;
    private ArrayList<Laser> mEnemyLasers;
    private ArrayList<Laser> mEnemy2Lasers;
    private ArrayList<Laser> mEnemy3Lasers;
    private ArrayList<Meteor> mMeteors;
    private ArrayList<Enemy> mEnemies;
    private ArrayList<Enemy2> mEnemies2;
    private ArrayList<Enemy3> mEnemies3;
    private ArrayList<Coin> mCoin;
    private ArrayList<Magazine> mMagazine;
    private ArrayList<Heart> mHeart;
    private int mScreenSizeX, mScreenSizeY;
    private int mCounter = 0;
    private SoundPlayer mSoundPlayer;
    private volatile boolean mIsGameOver;
    private int mayItem = 0;
    private boolean magazineEnabled = false;
    private int magazineAmmount = 0;
    private int additionalHP = 0;
    private int HP = 3;
    private int enemysize;
    private double enemyLaserSpeed = 1;
    private int moneyCount = 0;
    private BackGround background1, background2;

    public GameView(Context context) {
        super(context);
        initView(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        Level = Start.getP();
        if (Level != 3) {
            enemyActive = true;
            enemyNumber = 20 * Level / 2;
        }
        if (Level != 1 && Level != 3 && Level != 5) {
            enemy2Active = true;
            enemy2Number = 18 * Level / 2;
        }
        if (Level > 4) {
            enemy3Active = true;
            enemy3Number = 12 * Level / 2;
        }
        if (Level > 2) {
            meteorActive = true;
            meteorNumber = 16 * Level / 2;
        }
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        mScreenSizeX = point.x;
        mScreenSizeY = point.y;
        mSoundPlayer = new SoundPlayer(context);
        mPaint = new Paint();
        mSurfaceHolder = getHolder();
        reset();
    }

    void reset() {
        background1 = new BackGround(Start.getP(), getContext(), mScreenSizeX, mScreenSizeY);
        background2 = new BackGround(Start.getP(), getContext(), mScreenSizeX, mScreenSizeY);
        background2.y = mScreenSizeY;
        SCORE = 0;
        METEOR_DESTROYED = 0;
        ENEMY_DESTROYED = 0;
        moneyCount = 0;
        maygetItem = false;
        mPlayer = new Player(getContext(), mScreenSizeX, mScreenSizeY, mSoundPlayer);
        mEnemyLasers = new ArrayList<>();
        mEnemy2Lasers = new ArrayList<>();
        mEnemy3Lasers = new ArrayList<>();
        mdellatedLasers = new ArrayList<>();
        mMeteors = new ArrayList<>();
        mEnemies = new ArrayList<>();
        mEnemies2 = new ArrayList<>();
        mEnemies3 = new ArrayList<>();
        mCoin = new ArrayList<>();
        mMagazine = new ArrayList<>();
        mHeart = new ArrayList<>();

        mIsGameOver = false;
        if (Game.getItem(1) == true) {
            additionalHP = 1;
            HP += additionalHP;
        } else {
            additionalHP = 0;
        }
        if (Game.getItem(5) == true) {
            enemyLaserSpeed = 1.2;
        } else {
            enemyLaserSpeed = 1;
        }
    }

    @Override
    public void run() {
        while (mIsPlaying) {
            if (!mIsGameOver) {
                update();
                draw();
                control();
            }
        }
        Log.d("GameThread", "Run stopped");
    }

    public void update() {

        if (stay == true) {
            mPlayer.stay();
        }
        if (up == true) {
            down = false;
            stay = false;
            mPlayer.steerUp();
        }
        if (down == true) {
            up = false;
            stay = false;
            mPlayer.steerDown();
        }
        if (left == true) {
            right = false;
            stay = false;
            mPlayer.steerLeft();
        }
        if (right == true) {
            left = false;
            stay = false;
            mPlayer.steerRight();
        }
        background1.y += 2;
        background2.y += 2;

        if (background1.y > mScreenSizeY) {
            background1.y = -mScreenSizeY;
        }
        if (background2.y > mScreenSizeY) {
            background2.y = -mScreenSizeY;
        }
        mPlayer.update();
        if (mCounter % 200 == 0 && magazineEnabled == false) {
            mPlayer.fire();
        } else if (mCounter % 50 == 0 && magazineEnabled == true) {
            mPlayer.fire();
            --magazineAmmount;
            if (magazineAmmount == 0) {
                magazineEnabled = false;
            }
        }

        if (meteorActive == true) {
            for (Meteor m : mMeteors) {
                m.update();

                if (Rect.intersects(m.getCollision(), mPlayer.getCollision())) {
                    getItem(m.getX(), m.getY());
                    m.destroy();
                    --HP;
                    mPlayer.playcrash();
                    --enemysize;
                    if (HP == 0) {
                        mIsGameOver = true;
                    }
                }
                for (Laser l : mPlayer.getLasers()) {
                    if (Rect.intersects(m.getCollision(), l.getCollision())) {
                        m.hit();
                        --enemysize;
                        l.destroy();
                        if (m.ifDestroyed == true) {
                            getItem(m.getX(), m.getY());
                            m.destroy();
                        }
                    }
                }
            }
            deleting = true;
            while (deleting) {
                if (mMeteors.size() != 0) {
                    if (mMeteors.get(0).getY() > mScreenSizeY) {
                        mMeteors.remove(0);
                    }
                }
                if (mMeteors.size() == 0 || mMeteors.get(0).getY() <= mScreenSizeY) {
                    deleting = false;
                }
            }
            if (mCounter % 1000 == 0 && meteorNumber > 0 && enemysize < 10) {
                mMeteors.add(new Meteor(getContext(), mScreenSizeX, mScreenSizeY, mSoundPlayer));
                meteorNumber--;
                ++enemysize;
            }
            meteorsExists = mMeteors.size();
        }
        if (enemyActive == true) {
            for (Enemy e : mEnemies) {
                e.update();
                mEnemyLasers.addAll(e.getLasers());
                if (mCounter % (1000 * enemyLaserSpeed) == 0) {
                    e.fire();
                }
                if (Rect.intersects(e.getCollision(), mPlayer.getCollision())) {
                    getItem(e.getX(), e.getY());
                    e.destroy();
                    mPlayer.playcrash();
                    --HP;
                    --enemysize;
                    if (HP == 0) {
                        mIsGameOver = true;
                    }

                }
                for (Laser l : mPlayer.getLasers()) {
                    if (Rect.intersects(e.getCollision(), l.getCollision())) {
                        e.hit();
                        --enemysize;
                        l.destroy();
                        if (e.getDestroyed() == true) {
                            getItem(e.getX(), e.getY());
                            e.destroy();
                        }
                    }
                }

                for (Laser l : mEnemyLasers) {
                    if (l.getY() > mScreenSizeY) {
                        l.destroy();
                    }
                    if (Rect.intersects(mPlayer.getCollision(), l.getCollision())) {
                        --HP;
                        mPlayer.playcrash();
                        mEnemyLasers.remove(l);
                        l.destroy();
                        if (HP == 0) {
                            mIsGameOver = true;
                        }
                        break;
                    }
                }
            }
            deleting = true;
            while (deleting) {
                if (mEnemies.size() != 0) {
                    if (mEnemies.get(0).getY() > mScreenSizeY) {
                        mdellatedLasers.addAll(mEnemies.get(0).getLasers());
                        mEnemies.remove(0);
                    }
                }
                if (mEnemies.size() == 0 || mEnemies.get(0).getY() <= mScreenSizeY) {
                    deleting = false;
                }
            }
            if (mCounter % 2000 == 0 && enemyNumber > 0 && enemysize < 10) {
                mEnemies.add(new Enemy(getContext(), mScreenSizeX, mScreenSizeY, mSoundPlayer));
                enemyNumber--;
                ++enemysize;
            }
            enemyExists = mEnemies.size();
        }
        if (enemy2Active == true) {
            for (Enemy2 e2 : mEnemies2) {
                e2.update();
                mEnemy2Lasers.addAll(e2.getLasers());
                if (mCounter % (1300 * enemyLaserSpeed) == 0) {
                    e2.fire();
                }
                if (Rect.intersects(e2.getCollision(), mPlayer.getCollision())) {
                    getItem(e2.getX(), e2.getY());
                    e2.destroy();
                    mPlayer.playcrash();
                    --enemysize;
                    --HP;
                    if (HP == 0) {
                        mIsGameOver = true;
                    }
                }

                for (Laser l : mPlayer.getLasers()) {
                    if (Rect.intersects(e2.getCollision(), l.getCollision())) {
                        e2.hit();
                        --enemysize;
                        l.destroy();
                        if (e2.getDestroyed() == true) {
                            getItem(e2.getX(), e2.getY());
                            e2.destroy();
                        }
                    }
                }
                for (Laser l : mEnemy2Lasers) {
                    if (l.getY() > mScreenSizeY) {
                        l.destroy();
                    }
                    if (Rect.intersects(mPlayer.getCollision(), l.getCollision())) {
                        --HP;
                        mPlayer.playcrash();
                        mEnemy2Lasers.remove(l);
                        l.destroy();
                        if (HP == 0) {
                            mIsGameOver = true;
                        }
                        break;
                    }
                }
            }
            deleting = true;
            while (deleting) {
                if (mEnemies2.size() != 0) {
                    if (mEnemies2.get(0).getY() > mScreenSizeY) {
                        mdellatedLasers.addAll(mEnemies2.get(0).getLasers());
                        mEnemies2.remove(0);
                    }
                }
                if (mEnemies2.size() == 0 || mEnemies2.get(0).getY() <= mScreenSizeY) {
                    deleting = false;
                }
            }
            if (mCounter % 2500 == 0 && enemy2Number > 0 && enemysize < 10) {
                mEnemies2.add(new Enemy2(getContext(), mScreenSizeX, mScreenSizeY, mSoundPlayer));
                enemy2Number--;
                ++enemysize;
            }
            enemy2Exists = mEnemies2.size();
        }
        if (enemy3Active == true) {
            for (Enemy3 e3 : mEnemies3) {
                e3.update();
                mEnemy3Lasers.addAll(e3.getLasers());
                if (mCounter % (1500 * enemyLaserSpeed) == 0) {
                    e3.fire();
                }
                if (Rect.intersects(e3.getCollision(), mPlayer.getCollision())) {
                    getItem(e3.getX(), e3.getY());
                    e3.destroy();
                    mPlayer.playcrash();
                    --enemysize;
                    --HP;
                    if (HP == 0) {
                        mIsGameOver = true;
                    }
                }
                for (Laser l : mPlayer.getLasers()) {
                    if (Rect.intersects(e3.getCollision(), l.getCollision())) {
                        e3.hit();
                        --enemysize;
                        l.destroy();
                        if (e3.getDestroyed() == true) {
                            getItem(e3.getX(), e3.getY());
                            e3.destroy();
                        }
                    }
                }
                for (Laser l : mEnemy2Lasers) {
                    if (l.getY() > mScreenSizeY) {
                        l.destroy();
                    }
                    if (Rect.intersects(mPlayer.getCollision(), l.getCollision())) {
                        --HP;
                        mPlayer.playcrash();
                        mEnemy2Lasers.remove(l);
                        l.destroy();
                        if (HP == 0) {
                            mIsGameOver = true;
                        }
                    }
                }
            }
            deleting = true;
            while (deleting) {
                if (mEnemies3.size() != 0) {
                    if (mEnemies3.get(0).getY() > mScreenSizeY) {
                        mdellatedLasers.addAll(mEnemies3.get(0).getLasers());
                        mEnemies3.remove(0);
                    }
                }
                if (mEnemies3.size() == 0 || mEnemies3.get(0).getY() <= mScreenSizeY) {
                    deleting = false;
                }
            }
            if (mCounter % 3000 == 0 && enemy3Number > 0 && enemysize < 10) {
                mEnemies3.add(new Enemy3(getContext(), mScreenSizeX, mScreenSizeY, mSoundPlayer));
                enemy3Number--;
                ++enemysize;
            }
            enemy3Exists = mEnemies3.size();
        }
        for (Coin c : mCoin) {
            c.update();
            if (Rect.intersects(c.getCollision(), mPlayer.getCollision())) {
                ++moneyCount;
                c.playSound();
                c.destroy();
            }
        }
        deleting = true;
        while (deleting) {
            if (mCoin.size() != 0) {
                if (mCoin.get(0).getY() > mScreenSizeY) {
                    mCoin.remove(0);
                }
            }
            if (mCoin.size() == 0 || mCoin.get(0).getY() <= mScreenSizeY) {
                deleting = false;
            }
        }
        itemsExists = mCoin.size();
        for (Magazine m : mMagazine) {
            m.update();
            if (Rect.intersects(m.getCollision(), mPlayer.getCollision())) {
                m.playSound();
                magazineEnabled = true;
                m.destroy();
                if (Game.getItem(2) == true) {
                    magazineAmmount = 12;
                } else {
                    magazineAmmount = 10;
                }
            }
        }
        deleting = true;
        while (deleting) {
            if (mMagazine.size() != 0) {
                if (mMagazine.get(0).getY() > mScreenSizeY) {
                    mMagazine.remove(0);
                }
            }
            if (mMagazine.size() == 0 || mMagazine.get(0).getY() <= mScreenSizeY) {
                deleting = false;
            }
        }
        itemsExists += mMagazine.size();

        for (Heart h : mHeart) {
            h.update();
            if (Rect.intersects(h.getCollision(), mPlayer.getCollision())) {
                h.playSound();
                if (HP < 3 + additionalHP) {
                    HP++;
                }
                h.destroy();
            }
        }
        deleting = true;
        while (deleting) {
            if (mHeart.size() != 0) {
                if (mHeart.get(0).getY() > mScreenSizeY) {
                    mHeart.remove(0);
                }
            }
            if (mHeart.size() == 0 || mHeart.get(0).getY() <= mScreenSizeY) {
                deleting = false;
            }
        }
        itemsExists += mHeart.size();
        for (int i = 0; i < mdellatedLasers.size(); i++) {
            int S = mdellatedLasers.size();
            mdellatedLasers.get(i).updateY();
            if (S > 0 && Rect.intersects(mPlayer.getCollision(), mdellatedLasers.get(i).getCollision())) {
                --HP;
                mPlayer.playcrash();
                mdellatedLasers.get(i).destroy();
                mdellatedLasers.remove(i);
                if (HP == 0) {
                    mIsGameOver = true;
                }
            } else if (S > 0 && mdellatedLasers.get(i).getY() > mScreenSizeY) {
                mdellatedLasers.remove(i);
            }
        }
        if ((meteorsExists + enemyExists + enemy2Exists + enemy3Exists + itemsExists + meteorNumber + enemyNumber + enemy2Number + enemy3Number) == 0) {
            win = true;
            mIsGameOver = true;
        }
    }

    public void draw() {
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.BLACK);
            mCanvas.drawBitmap(background1.background, background1.x, background1.y, mPaint);
            mCanvas.drawBitmap(background2.background, background2.x, background2.y, mPaint);
            mCanvas.drawBitmap(mPlayer.getBitmap(), mPlayer.getX(), mPlayer.getY(), mPaint);
            for (Coin c : mCoin) {
                mCanvas.drawBitmap(c.getBitmap(), c.getX(), c.getY(), mPaint);
            }
            for (Magazine m : mMagazine) {
                mCanvas.drawBitmap(m.getBitmap(), m.getX(), m.getY(), mPaint);
            }
            for (Heart h : mHeart) {
                mCanvas.drawBitmap(h.getBitmap(), h.getX(), h.getY(), mPaint);
            }
            for (Meteor m : mMeteors) {
                mCanvas.drawBitmap(m.getBitmap(), m.getX(), m.getY(), mPaint);
            }
            for (Enemy e : mEnemies) {
                mCanvas.drawBitmap(e.getBitmap(), e.getX(), e.getY(), mPaint);
            }
            for (Enemy2 e2 : mEnemies2) {
                mCanvas.drawBitmap(e2.getBitmap(), e2.getX(), e2.getY(), mPaint);
            }
            for (Enemy3 e3 : mEnemies3) {
                mCanvas.drawBitmap(e3.getBitmap(), e3.getX(), e3.getY(), mPaint);
            }
            for (Laser l : mPlayer.getLasers()) {
                mCanvas.drawBitmap(l.getBitmap(), l.getX(), l.getY(), mPaint);
            }
            for (Laser l : mEnemyLasers) {
                mCanvas.drawBitmap(l.getBitmap(), l.getX(), l.getY(), mPaint);
            }
            mEnemyLasers.clear();
            for (Laser l : mEnemy2Lasers) {
                mCanvas.drawBitmap(l.getBitmap(), l.getX(), l.getY(), mPaint);
            }
            mEnemy2Lasers.clear();
            for (Laser l : mEnemy3Lasers) {
                mCanvas.drawBitmap(l.getBitmap(), l.getX(), l.getY(), mPaint);
            }
            mEnemy3Lasers.clear();
            for (Laser l : mdellatedLasers) {
                mCanvas.drawBitmap(l.getBitmap(), l.getX(), l.getY(), mPaint);
            }
            drawScore();
            drawHP();
            if (mIsGameOver) {
                drawGameOver();
            }
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    void drawScore() {
        Paint score = new Paint();
        score.setTextSize(60);
        score.setColor(Color.WHITE);
        mCanvas.drawText("Wynik : " + SCORE, 0, 60, score);
    }

    void drawHP() {
        Paint score = new Paint();
        score.setTextSize(60);
        score.setColor(Color.WHITE);
        mCanvas.drawText("HP : " + HP, mScreenSizeX - 200, 60, score);
    }

    public void getItem(int x, int y) {
        if (maygetItem == true) {
            Random random = new Random();
            mayItem = random.nextInt(10);
            if (mayItem > 2 && mayItem < 6) {
                mCoin.add(new Coin(getContext(), mScreenSizeX, mScreenSizeY, mSoundPlayer, x, y));
                maygetItem = false;
            }
            if (mayItem > 5 && mayItem < 8) {
                mMagazine.add(new Magazine(getContext(), mScreenSizeX, mScreenSizeY, mSoundPlayer, x, y));
                maygetItem = true;
            }
            if (mayItem > 8) {
                mHeart.add(new Heart(getContext(), mScreenSizeX, mScreenSizeY, mSoundPlayer, x, y));
            }
        }
    }

    void drawGameOver() {
        moneyCount = moneyCount + ENEMY_DESTROYED / Level;
        Paint gameOver = new Paint();
        gameOver.setTextSize(100);
        gameOver.setTextAlign(Paint.Align.CENTER);
        gameOver.setColor(Color.WHITE);
        if (win == false) {
            mCanvas.drawText("Przegrana", mScreenSizeX / 2, mScreenSizeY / 2, gameOver);
        }
        if (win == true) {
            mCanvas.drawText("Wygrana", mScreenSizeX / 2, mScreenSizeY / 2, gameOver);

            Game.setCoins(moneyCount);
            Game.setScore(SCORE);
            Game.setisWin();
        }
        Paint highScore = new Paint();
        highScore.setTextSize(60);
        highScore.setTextAlign(Paint.Align.CENTER);
        highScore.setColor(Color.WHITE);
        mCanvas.drawText("Wynik: " + SCORE, mScreenSizeX / 2, (mScreenSizeY / 2) + 100, highScore);
        Paint enemyDestroyed = new Paint();
        enemyDestroyed.setTextSize(60);
        enemyDestroyed.setTextAlign(Paint.Align.CENTER);
        enemyDestroyed.setColor(Color.WHITE);
        mCanvas.drawText("Zniszczono Statków: " + ENEMY_DESTROYED, mScreenSizeX / 2, (mScreenSizeY / 2) + 200, enemyDestroyed);
        Paint meteorDestroyed = new Paint();
        meteorDestroyed.setTextSize(60);
        meteorDestroyed.setTextAlign(Paint.Align.CENTER);
        meteorDestroyed.setColor(Color.WHITE);
        mCanvas.drawText("Zniszczono Meteorytów: " + METEOR_DESTROYED, mScreenSizeX / 2, (mScreenSizeY / 2) + 300, meteorDestroyed);
        if (win == true) {
            Paint moneyGet = new Paint();
            moneyGet.setTextSize(60);
            moneyGet.setTextAlign(Paint.Align.CENTER);
            moneyGet.setColor(Color.WHITE);
            mCanvas.drawText("Zdobyto Monet: " + moneyCount, mScreenSizeX / 2, (mScreenSizeY / 2) + 400, moneyGet);
        }
    }

    public void control() {
        try {
            if (mCounter == 10000) {
                mCounter = 0;
            }
            Thread.sleep(20);
            mCounter += 20;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        Log.d("GameThread", "Main");
        mIsPlaying = false;
        try {
            mGameThread.join();
            mSoundPlayer.pause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        mIsPlaying = true;
        mSoundPlayer.resume();
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mIsGameOver) {
                    ((Activity) getContext()).finish();
                    getContext().startActivity(new Intent(getContext(), Start.class));
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}

