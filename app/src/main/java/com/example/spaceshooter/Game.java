package com.example.spaceshooter;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

public class Game extends AppCompatActivity {

    private static String[] Item = new String[6];
    private static double Difficulty = 1;
    private static boolean sound = false;
    private static String playerName = "";
    private static int newcoins;
    private static int newresult;
    private static boolean isWin = false;
    private GameView mGameView;
    private ImageView down, up, right, left, stay;
    private String[] Level = new String[6];
    private String[] Coins = new String[1];
    private String[] Score = new String[1];
    private File path;
    private File file;
    private final String filename = "przedmiot.txt";
    private final String name = "current items";
    private String[] Settings = new String[3];
    private int coins;
    private int resultint;
    private String[] Save = new String[44];
    private final String[][] TableScores = new String[11][4];

    public static void setCoins(int m) {
        newcoins = m;
    }

    public static void setScore(int w) {
        newresult = w;
    }

    public static void setisWin() {
        isWin = true;
    }

    protected static boolean getItem(int p) {
        return "1".equals(Item[p - 1]);
    }

    protected static double getDifficulty() {
        return Difficulty;
    }

    protected static boolean getSound() {
        return sound;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isWin = false;
        loadSettings("monety");
        loadSettings("wynikobecny");
        loadSettings("poziom");
        startCheck();
        settingsLoad();
        settingCheck();
        path = getApplicationContext().getFilesDir();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.game);
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        mGameView = (GameView) findViewById(R.id.gameView);
        down = (ImageView) findViewById(R.id.downView);
        up = (ImageView) findViewById(R.id.upView);
        left = (ImageView) findViewById(R.id.leftView);
        right = (ImageView) findViewById(R.id.rightView);
        stay = (ImageView) findViewById(R.id.stayView);

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameView.down = true;
                GameView.up = false;
                GameView.left = false;
                GameView.right = false;
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameView.up = true;
                GameView.down = false;
                GameView.left = false;
                GameView.right = false;
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameView.left = true;
                GameView.right = false;
                GameView.down = false;
                GameView.up = false;
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameView.right = true;
                GameView.left = false;
                GameView.down = false;
                GameView.up = false;
            }
        });

        stay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameView.stay = true;
                GameView.left = false;
                GameView.right = false;
                GameView.down = false;
                GameView.up = false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGameView.pause();
    }

    private void loadSettings(String name) {
        path = getApplicationContext().getFilesDir();

        if ("monety".equals(name)) {
            file = new File(path, "monety.txt");
            byte[] bytes = new byte[(int) file.length()];
            try (FileInputStream in = new FileInputStream(file)) {
                if (in.read(bytes) < 1) {
                    throw new IOException("Unable to read from monety.txt, it was empty");
                }
            } catch (IOException e) {
                Toast.makeText(this, "Unable to read from monety.txt", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            String contents = new String(bytes);
            contents = contents.replaceAll("\n", ""); // strip the newline
            Coins = contents.split(",");
        }

        if ("wynikobecny".equals(name)) {
            file = new File(path, "wynikobecny.txt");
            byte[] bytes = new byte[(int) file.length()];
            try (FileInputStream in = new FileInputStream(file)) {
                if (in.read(bytes) < 1) {
                    throw new IOException("Unable to read from wynikobecny.txt, it was empty");
                }
            } catch (IOException e) {
                Toast.makeText(this, "Unable to read from wynikobecny.txt", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            String contents = new String(bytes);
            contents = contents.replaceAll("\n", ""); // strip the newline
            Score = contents.split(",");
        }

        if ("poziom".equals(name)) {
            file = new File(path, "poziom.txt");
            byte[] bytes = new byte[(int) file.length()];
            try (FileInputStream in = new FileInputStream(file)) {
                if (in.read(bytes) < 1) {
                    throw new IOException("Unable to read from poziom.txt, it was empty");
                }
            } catch (IOException e) {
                Toast.makeText(this, "Unable to read from poziom.txt", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            String contents = new String(bytes);
            contents = contents.replaceAll("\n", ""); // strip the newline
            Level = contents.split(",");
        }
    }

    private void saveSettings(String name) {
        File path, file;
        StringBuilder text = new StringBuilder();

        if ("monety".equals(name)) {
            path = getApplicationContext().getFilesDir();
            file = new File(path, "monety.txt");
            String s = Coins[0];
            text.append(s).append(",");

            try (FileOutputStream output = new FileOutputStream(file)) {
                output.write(text.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                Toast.makeText(this, "Cannot save monety.txt", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else if ("wynikobecny".equals(name)) {
            path = getApplicationContext().getFilesDir();
            file = new File(path, "wynikobecny.txt");
            String s = Score[0];
            text.append(s).append(",");
            try (FileOutputStream output = new FileOutputStream(file)) {
                output.write(text.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                Toast.makeText(this, "Cannot save wynikobecny.txt", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else if ("poziom".equals(name)) {
            path = getApplicationContext().getFilesDir();
            file = new File(path, "poziom.txt");
            for (String s : Level) {
                text.append(s).append(",");
            }
            try (FileOutputStream output = new FileOutputStream(file)) {
                output.write(text.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                Toast.makeText(this, "Cannot save poziom.txt ", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    public void settingCheck() {
        playerName = Settings[0];
        if ("1".equals(Settings[1])) {
            Difficulty = 1;
        } else if ("2f".equals(Settings[1])) {
            Difficulty = 1.5;
        } else if ("3".equals(Settings[1])) {
            Difficulty = 2;
        }
        if ("soundOn".equals(Settings[2])) {
            sound = true;
        } else if ("soundOff".equals(Settings[2])) {
            sound = false;
        }
    }

    private void startCheck() {
        path = getApplicationContext().getFilesDir();
        file = new File(path, filename);
        byte[] bytes = new byte[(int) file.length()];
        try (
                FileInputStream in = new FileInputStream(file)) {
            if (in.read(bytes) < 1) {
                throw new IOException("Unable to read from " + name + " - empty");
            }
        } catch (IOException e) {
            Toast.makeText(this, "Unable to read from " + name, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        String contents = new String(bytes);
        contents = contents.replaceAll("\n", ""); // strip the newline
        Item = contents.split(",");
    }

    private void settingsLoad() {
        path = getApplicationContext().getFilesDir();
        file = new File(path, "ustawienia.txt");
        byte[] bytes = new byte[(int) file.length()];
        try (FileInputStream in = new FileInputStream(file)) {
            if (in.read(bytes) < 1) {
                throw new IOException("Unable to read from ustawienia.txt - empty");
            }
        } catch (IOException e) {
            Toast.makeText(this, "Unable to read from ustawienia.txt", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        String contents = new String(bytes);
        contents = contents.replaceAll("\n", ""); // strip the newline
        Settings = contents.split(",");
    }

    public void wingame() {
        coins = Integer.parseInt(Coins[0]);
        coins += newcoins;
        Coins[0] = Integer.toString(coins);
        resultint = Integer.parseInt(Score[0]);
        resultint += newresult;
        Score[0] = Integer.toString(resultint);
        Level[Start.getP() - 1] = "1";
        if (Start.getP() != 6) {
            Level[Start.getP()] = "0";
        }
        saveSettings("monety");
        saveSettings("wynikobecny");
        saveSettings("poziom");
        if (Start.getP() == 6) {
            lastLvl();
        }
    }

    private void lastLvl() {
        path = getApplicationContext().getFilesDir();
        file = new File(path, "wyniki.txt");
        int NumberT = 0;
        byte[] bytes = new byte[(int) file.length()];
        try (FileInputStream in = new FileInputStream(file)) {
            if (in.read(bytes) < 1) {
                throw new IOException("Unable to read from wyniki.txt - empty");
            }
        } catch (IOException e) {
            Toast.makeText(this, "Unable to read from wyniki.txt", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        String contents = new String(bytes);
        String line = null;
        contents = contents.replaceAll("\n", ""); // strip the newline
        Save = contents.split(",");
        if (Save.length != 0) {
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 4; j++) {
                    int z = i * 4 + j;
                    TableScores[i][j] = Save[z];
                }
            }
        }
        String last = TableScores[10][2];
        NumberT = Integer.parseInt(last);
        boolean newScoreB = true;
        boolean doSaveB = false;
        String oldName;
        String newName;
        String oldScore;
        String newScore;
        String oldDate;
        String newDate;


        for (int i = 10; i > 0; i--) {
            if (i == 10) {
                if (NumberT < resultint) {
                    TableScores[i][1] = playerName;
                    TableScores[i][2] = Integer.toString(resultint);
                    Calendar c = Calendar.getInstance();
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH);
                    int year = c.get(Calendar.YEAR);
                    String date = day + "." + (month + 1) + "." + year;
                    TableScores[i][3] = date;
                    doSaveB = true;
                } else {
                    newScoreB = false;
                }
            }
            if (newScoreB = false) {
                break;
            }
            if (i < 10) {
                last = TableScores[i][2];
                NumberT = Integer.parseInt(last);
                last = TableScores[i + 1][2];
                int NumberT2 = Integer.parseInt(last);
                if (NumberT < NumberT2) {
                    oldName = TableScores[i][1];
                    newName = TableScores[i + 1][1];
                    oldScore = TableScores[i][2];
                    newScore = TableScores[i + 1][2];
                    oldDate = TableScores[i][3];
                    newDate = TableScores[i + 1][3];

                    TableScores[i][1] = newName;
                    TableScores[i][2] = newScore;
                    TableScores[i][3] = newDate;
                    TableScores[i + 1][1] = oldName;
                    TableScores[i + 1][2] = oldScore;
                    TableScores[i + 1][3] = oldDate;

                } else {
                    newScoreB = false;
                }
            }
        }

        if (doSaveB == true) {
            path = getApplicationContext().getFilesDir();
            file = new File(path, "wyniki.txt");
            StringBuilder text = new StringBuilder();
            String S = "";

            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 4; j++) {
                    int z = i * 4 + j;
                    Save[z] = TableScores[i][j];
                }
            }
            for (String s : Save) {
                text.append(s).append(",");
            }
            try (FileOutputStream output = new FileOutputStream(file)) {
                output.write(text.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                Toast.makeText(this, "Cannot save new level to file", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        Intent i = new Intent(Game.this, Start.class);
        startActivity(i);
        if (isWin == true) {
            wingame();
        }
        super.onDestroy();
    }
}
