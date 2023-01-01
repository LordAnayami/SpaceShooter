package com.example.spaceshooter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ScoreTable extends AppCompatActivity {

    private String[] Zapis = new String[44];
    private final String[][] Wyniki = new String[11][4];
    private File path, file;
    private TableLayout table, tablecreate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores);
        path = getApplicationContext().getFilesDir();
        file = new File(path, "wyniki.txt");
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
        Zapis = contents.split(",");
        if (Zapis.length != 0) {
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 4; j++) {
                    int z = i * 4 + j;
                    Wyniki[i][j] = Zapis[z];
                }
            }
        }
        String[] column = {"NICK", "WYNIK", "DATA"};
        String[] row = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        int rl = row.length;
        int cl = column.length;
        table = findViewById(R.id.table);
        tablecreate = createTableLayout(row, column, rl, cl);
        table.addView(tablecreate);
        Button Back = (Button) findViewById(R.id.powrot2);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScoreTable.this, MainActivity.class);
                startActivity(i);

            }
        });
    }

    private TableLayout createTableLayout(String[] rv, String[] cv, int rowCount, int columnCount) {
        // 1) Create a tableLayout and its params
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setShrinkAllColumns(true);
        GradientDrawable cellBackground = new GradientDrawable();
        cellBackground.setStroke(5, Color.WHITE);
        cellBackground.setColor(Color.TRANSPARENT);
        // 2) create tableRow params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;
        for (int i = 0; i < rowCount + 1; i++) {
            // 3) create tableRow
            TableRow tableRow = new TableRow(this);
            for (int j = 0; j < columnCount + 1; j++) {
                // 4) create textView
                TextView textView = new TextView(this);
                textView.setBackground(cellBackground);
                textView.setTextSize(20);
                textView.setTextColor(Color.parseColor("#FFCBF4"));
                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                textView.setTypeface(boldTypeface);
                textView.setGravity(Gravity.CENTER);
                if (i == 0 && j == 0) {
                    textView.setText("NR");
                } else if (i == 0) {
                    Log.d("TAAG", "set Column Headers");
                    textView.setText(cv[j - 1]);
                } else if (j == 0) {
                    Log.d("TAAG", "Set Row Headers");
                    textView.setText(rv[i - 1]);
                } else {
                    String dana = Wyniki[i][j];
                    textView.setText(dana);
                }
                // 5) add textView to tableRow
                tableRow.addView(textView, tableRowParams);
            }
            // 6) add tableRow to tableLayout
            tableLayout.addView(tableRow, tableLayoutParams);
        }
        return tableLayout;
    }
}
