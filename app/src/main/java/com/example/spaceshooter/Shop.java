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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Shop extends AppCompatActivity {
    private String[] Coins = new String[1];
    private String[] Items = new String[6];
    private String[] ItemsSk = new String[6];
    private File path, file;
    private TextView coins;
    private int coinssk = 0;
    private final int color = Color.parseColor("#FF6200EE");
    private final int color2 = Color.parseColor("#FF018786");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        loadSettings("monety");
        loadSettings("przedmiot");
        coins = (TextView) findViewById(R.id.monetySk);
        coins.setText("Ilość monet: " + Coins[0]);
        coinssk = Integer.parseInt(Coins[0]);
        Button Item1 = (Button) findViewById(R.id.przedmiot1);

        if ("0".equals(Items[0])) {
            Item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("0".equals(ItemsSk[0])) {
                        if (coinssk >= 10) {
                            coinssk -= 10;
                            ItemsSk[0] = "1";
                            Item1.setBackgroundColor(color2);
                        }
                    } else {
                        coinssk += 10;
                        ItemsSk[0] = "0";
                        Item1.setBackgroundColor(color);
                    }
                    coins.setText("Ilość monet: " + coinssk);
                }
            });
        }
        if ("1".equals(Items[0])) {
            Item1.setBackgroundColor(Color.parseColor("#808080"));
            Item1.setOnClickListener(null);
        }

        Button Item2 = (Button) findViewById(R.id.przedmiot2);
        if ("0".equals(Items[1])) {
            Item2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("0".equals(ItemsSk[1])) {
                        if (coinssk >= 15) {
                            coinssk -= 15;
                            ItemsSk[1] = "1";
                            Item2.setBackgroundColor(color2);
                        }
                    } else {
                        coinssk += 15;
                        ItemsSk[1] = "0";
                        Item2.setBackgroundColor(color);
                    }
                    coins.setText("Ilość monet: " + coinssk);
                }
            });
        }
        if ("1".equals(Items[1])) {
            Item2.setBackgroundColor(Color.parseColor("#808080"));
            Item2.setOnClickListener(null);
        }

        Button Item3 = (Button) findViewById(R.id.przedmiot3);
        if ("0".equals(Items[2])) {
            Item3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("0".equals(ItemsSk[2])) {
                        if (coinssk >= 20) {
                            coinssk -= 20;
                            ItemsSk[2] = "1";
                            Item3.setBackgroundColor(color2);
                        }
                    } else {
                        coinssk += 20;
                        ItemsSk[2] = "0";
                        Item3.setBackgroundColor(color);
                    }
                    coins.setText("Ilość monet: " + coinssk);
                }
            });
        }
        if ("1".equals(Items[2])) {
            Item3.setBackgroundColor(Color.parseColor("#808080"));
            Item3.setOnClickListener(null);
        }

        Button Item4 = (Button) findViewById(R.id.przedmiot4);
        if ("0".equals(Items[3])) {
            Item4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("0".equals(ItemsSk[3])) {
                        if (coinssk >= 25) {
                            coinssk -= 25;
                            ItemsSk[3] = "1";
                            Item4.setBackgroundColor(color2);
                        }
                    } else {
                        coinssk += 25;
                        ItemsSk[3] = "0";
                        Item4.setBackgroundColor(color);
                    }
                    coins.setText("Ilość monet: " + coinssk);
                }
            });
        }
        if ("1".equals(Items[3])) {
            Item4.setBackgroundColor(Color.parseColor("#808080"));
            Item4.setOnClickListener(null);
        }

        Button Item5 = (Button) findViewById(R.id.przedmiot5);
        if ("0".equals(Items[4])) {
            Item5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("0".equals(ItemsSk[4])) {
                        if (coinssk >= 30) {
                            coinssk -= 30;
                            ItemsSk[4] = "1";
                            Item5.setBackgroundColor(color2);
                        }
                    } else {
                        coinssk += 30;
                        ItemsSk[4] = "0";
                        Item5.setBackgroundColor(color);
                    }
                    coins.setText("Ilość monet: " + coinssk);
                }
            });
        }
        if ("1".equals(Items[4])) {
            Item5.setBackgroundColor(Color.parseColor("#808080"));
            Item5.setOnClickListener(null);
        }

        Button Item6 = (Button) findViewById(R.id.przedmiot6);
        if ("0".equals(Items[5])) {
            Item6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("0".equals(ItemsSk[5])) {
                        if (coinssk >= 35) {
                            coinssk -= 35;
                            ItemsSk[5] = "1";
                            Item6.setBackgroundColor(color2);
                        }
                    } else {
                        coinssk += 35;
                        ItemsSk[5] = "0";
                        Item6.setBackgroundColor(color);
                    }
                    coins.setText("Ilość monet: " + coinssk);
                }
            });
        }
        if ("1".equals(Items[5])) {
            Item6.setBackgroundColor(Color.parseColor("#808080"));
            Item6.setOnClickListener(null);
        }

        Button Back = (Button) findViewById(R.id.powrot4);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Coins[0] = String.valueOf(coinssk);
                saveSettings("monety");
                saveSettings("przedmiot");
                Intent i = new Intent(Shop.this, Start.class);
                startActivity(i);
            }
        });
    }

    private void loadSettings(String name) {
        path = getApplicationContext().getFilesDir();
        if (name == "monety") {
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
        if (name == "przedmiot") {
            file = new File(path, "przedmiot.txt");
            byte[] bytes = new byte[(int) file.length()];
            try (FileInputStream in = new FileInputStream(file)) {
                if (in.read(bytes) < 1) {
                    throw new IOException("Unable to read from przedmiot.txt");
                }
            } catch (IOException e) {
                Toast.makeText(this, "Unable to read from przedmiot.txt - empty", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            String contents = new String(bytes);

            contents = contents.replaceAll("\n", ""); // strip the newline
            Items = contents.split(",");
            ItemsSk = contents.split(",");
        }
    }

    private void saveSettings(String name) {
        path = getApplicationContext().getFilesDir();

        if (name == "monety") {
            file = new File(path, "monety.txt");
            StringBuilder text = new StringBuilder();
            for (String s : Coins) {
                text.append(s).append(",");
                s = "";
            }
            try (FileOutputStream output = new FileOutputStream(file)) {
                output.write(text.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                Toast.makeText(this, "Cannot save coins to file", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

        if (name == "przedmiot") {
            file = new File(path, "przedmiot.txt");
            StringBuilder text = new StringBuilder();
            for (String s : ItemsSk) {
                text.append(s).append(",");
                s = "";
            }
            try (FileOutputStream output = new FileOutputStream(file)) {
                output.write(text.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                Toast.makeText(this, "Cannot save items to file", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}
