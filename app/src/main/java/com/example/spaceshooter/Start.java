package com.example.spaceshooter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Start extends AppCompatActivity {

    private static int P = 0;
    private String[] Coins = new String[1];
    private String[] Score = new String[1];
    private String[] Level = new String[6];
    private File path, file;
    private TextView coins, score;

    protected static int getP() {
        return P;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooselvl);
        loadSettings("monety");
        loadSettings("wynikobecny");
        loadSettings("poziom");
        coins = (TextView) findViewById(R.id.monetyS);
        score = (TextView) findViewById(R.id.wynikS);
        coins.setText("Ilość monet: " + Coins[0]);
        score.setText("Wynik: " + Score[0]);

        Button Level1 = (Button) findViewById(R.id.poziom1);
        if ("0".equals(Level[0])) {
            Level1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Start.this, Game.class);
                    P = 1;
                    startActivity(i);
                }
            });
        }
        if ("1".equals(Level[0])) {
            Level1.setBackgroundColor(Color.parseColor("#808080"));
            Level1.setOnClickListener(null);
        }

        Button Level2 = (Button) findViewById(R.id.poziom2);
        if ("0".equals(Level[1])) {
            Level2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Start.this, Game.class);
                    P = 2;
                    startActivity(i);
                }
            });
        }
        if ("1".equals(Level[1])) {
            Level2.setBackgroundColor(Color.parseColor("#808080"));
            Level2.setOnClickListener(null);
        }

        Button Level3 = (Button) findViewById(R.id.poziom3);
        if ("0".equals(Level[2])) {
            Level3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Start.this, Game.class);
                    P = 3;
                    startActivity(i);
                }
            });
        }
        if ("1".equals(Level[2])) {
            Level3.setBackgroundColor(Color.parseColor("#808080"));
            Level3.setOnClickListener(null);
        }

        Button Level4 = (Button) findViewById(R.id.poziom4);
        if ("0".equals(Level[3])) {
            Level4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Start.this, Game.class);
                    P = 4;
                    startActivity(i);
                }
            });
        }
        if ("1".equals(Level[3])) {
            Level4.setBackgroundColor(Color.parseColor("#808080"));
            Level4.setOnClickListener(null);
        }

        Button Level5 = (Button) findViewById(R.id.poziom5);
        if ("0".equals(Level[4])) {
            Level5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Start.this, Game.class);
                    P = 5;
                    startActivity(i);
                }
            });
        }
        if ("1".equals(Level[4])) {
            Level5.setBackgroundColor(Color.parseColor("#808080"));
            Level5.setOnClickListener(null);
        }

        Button Level6 = (Button) findViewById(R.id.poziom6);
        if ("0".equals(Level[5])) {
            Level6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Start.this, Game.class);
                    P = 6;
                    startActivity(i);
                }
            });
        }
        if ("1".equals(Level[5])) {
            Level6.setBackgroundColor(Color.parseColor("#808080"));
            Level6.setOnClickListener(null);
        }

        Button Shop = (Button) findViewById(R.id.sklep);
        Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Start.this, Shop.class);
                startActivity(i);
            }
        });

        Button Back = (Button) findViewById(R.id.powrot3);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Start.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadSettings(String name) {
        path = getApplicationContext().getFilesDir();
        if ("monety".equals(name)) {
            file = new File(path, "monety.txt");
            byte[] bytes = new byte[(int) file.length()];
            try (FileInputStream in = new FileInputStream(file)) {
                if (in.read(bytes) < 1) {
                    throw new IOException("Unable to read from monety.txt - empty");
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
                    throw new IOException("Unable to read from wynikobecny.txt - empty");
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
                    throw new IOException("Unable to read from poziom.txt - empty");
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

}
