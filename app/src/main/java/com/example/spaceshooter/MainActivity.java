package com.example.spaceshooter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {
    private final String[] Level = new String[6];
    private final String[] Items = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFiles();
        Button Nowa = (Button) findViewById(R.id.Nowa);
        Nowa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings("monety");
                saveSettings("poziom");
                saveSettings("wynikobecny");
                saveSettings("przedmiot");
                Intent i = new Intent(MainActivity.this, Start.class);
                startActivity(i);
            }
        });
        Button Start = (Button) findViewById(R.id.Start);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Start.class);
                startActivity(i);
            }
        });
        Button Scores = (Button) findViewById(R.id.Wyniki);
        Scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ScoreTable.class);
                startActivity(i);
            }
        });

        Button Settings = (Button) findViewById(R.id.Ustawienia);
        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, com.example.spaceshooter.Settings.class);
                startActivity(i);
            }
        });

        Button Close = (Button) findViewById(R.id.Zamknij);
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    private void initFiles() {
        File path = getApplicationContext().getFilesDir();
        initFile(path, "ustawienia.txt", "settings file");
        initFile(path, "wyniki.txt", "high scores file");
        initFile(path, "monety.txt", "gold");
        initFile(path, "poziom.txt", "level");
        initFile(path, "wynikobecny.txt", "current score");
        initFile(path, "przedmiot.txt", "current items");

    }

    private void initFile(File path, String filename, String name) {
        File file = new File(path, filename);
        if (!file.exists() || file.length() < 1) {
            try {
                if (!file.isFile()) {
                    if (!file.createNewFile() || !file.canWrite()) {
                        throw new IOException("Unable to write to " + name);
                    }
                }
                InputStream input = getAssets().open(filename);
                int size = input.available();
                byte[] buffer = new byte[size];
                if (input.read(buffer) < 1) {
                    throw new IOException("Unable to initialize " + name + "- empty file");
                }
                try (FileOutputStream output = new FileOutputStream(file)) {
                    output.write(buffer);
                }
            } catch (IOException e) {
                Toast.makeText(this, "Unable to initialize " + name, Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    private void saveSettings(String name) {
        File path, file;
        StringBuilder text = new StringBuilder();
        if ("monety".equals(name)) {
            path = getApplicationContext().getFilesDir();
            file = new File(path, "monety.txt");
            String s = "0";
            text.append(s).append(",");
            try (FileOutputStream output = new FileOutputStream(file)) {
                output.write(text.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                Toast.makeText(this, "Cannot save coins to file", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else if ("wynikobecny".equals(name)) {
            path = getApplicationContext().getFilesDir();
            file = new File(path, "wynikobecny.txt");
            String s = "0";
            text.append(s).append(",");

            try (FileOutputStream output = new FileOutputStream(file)) {
                output.write(text.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                Toast.makeText(this, "Cannot save score to file", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else if ("przedmiot".equals(name)) {
            path = getApplicationContext().getFilesDir();
            file = new File(path, "przedmiot.txt");
            Items[0] = "0";
            Items[1] = "0";
            Items[2] = "0";
            Items[3] = "0";
            Items[4] = "0";
            Items[5] = "0";
            for (String s : Items) {
                text.append(s).append(",");
            }
            try (FileOutputStream output = new FileOutputStream(file)) {
                output.write(text.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                Toast.makeText(this, "Cannot save items to file", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else if ("poziom".equals(name)) {
            path = getApplicationContext().getFilesDir();
            file = new File(path, "poziom.txt");
            Level[0] = "0";
            for (int i = 1; i < 6; i++) {
                Level[i] = "1";
            }
            for (String s : Level) {
                text.append(s).append(",");
            }
            try (FileOutputStream output = new FileOutputStream(file)) {
                output.write(text.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                Toast.makeText(this, "Cannot save level to file", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}



