package com.example.spaceshooter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class Settings extends AppCompatActivity {

    private EditText editname;
    private String[] Settings = new String[3];
    private File path, file;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
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
        String name = Settings[0];
        editname = (EditText) findViewById(R.id.editName);
        editname.setText(name);

        Button Easy = (Button) findViewById(R.id.latwy);
        Easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings[1] = "1";
            }
        });

        Button Medium = (Button) findViewById(R.id.sredni);
        Medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings[1] = "2";
            }
        });

        Button Hard = (Button) findViewById(R.id.trudny);
        Hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings[1] = "3";
            }
        });

        Button SoundOn = (Button) findViewById(R.id.soundOn);
        SoundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings[2] = "soundOn";
            }
        });

        Button SoundOff = (Button) findViewById(R.id.soundOff);
        SoundOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings[2] = "soundOff";
            }
        });


        Button Back = (Button) findViewById(R.id.powrot1);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
                Intent i = new Intent(Settings.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void saveSettings() {
        StringBuilder text = new StringBuilder();
        String name = editname.getText().toString();
        Settings[0] = name;
        for (String s : Settings) {
            text.append(s).append(",");
        }
        try (FileOutputStream output = new FileOutputStream(file)) {
            output.write(text.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            Toast.makeText(this, "Cannot save settings to file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
