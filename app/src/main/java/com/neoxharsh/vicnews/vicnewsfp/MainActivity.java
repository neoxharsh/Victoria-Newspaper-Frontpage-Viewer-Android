package com.neoxharsh.vicnews.vicnewsfp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends Activity {
    String currentDate = "";
    DatePickerDialog dpg;
    TextView pageURLTV;
    VicDB dbAcess;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = findViewById(R.id.getDate);
        pageURLTV = findViewById(R.id.pageURLTextView);
        imageView = findViewById(R.id.pageImageView);
        dpg = new DatePickerDialog(MainActivity.this);
        Calendar minDate = new Calendar.Builder().setDate(1848, 9, 15).build();
        Calendar maxDate = new Calendar.Builder().setDate(1952, 2, 9).build();
        dpg.getDatePicker().setMinDate(minDate.getTimeInMillis());
        dpg.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dpg.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(month) + "-" + String.valueOf(year));
                        final String dateFormat1 = String.valueOf(year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth);
                        final String dateFormat2 = String.format("%d", dayOfMonth) + "/" + String.format("%02d", month) + "/" + String.valueOf(year);
                        final ArrayList<PageEntity> pageList = new ArrayList<>();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                PageEntity[] temp = dbAcess.daoAccess().getItemWithDate(dateFormat1);
                                if (temp.length == 0) {
                                    temp = dbAcess.daoAccess().getItemWithDate(dateFormat2);
                                }
                                for (PageEntity p:temp){
                                    pageList.add(p);
                                }


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Glide.with(getApplicationContext()).load(pageList.get(0).ImageURL).into(imageView);
                                        pageURLTV.setText(pageList.get(0).ImageURL);
                                    }
                                });
                            }

                        }).start();

                    }
                });
                dpg.show();
            }
        });
        dbAcess = Room.databaseBuilder(getApplicationContext(), VicDB.class, "main_vic_db.db").fallbackToDestructiveMigration().build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.build_db: {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.db_update_pb).setVisibility(View.VISIBLE);
                            }
                        });
                        updateDB();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.db_update_pb).setVisibility(View.GONE);
                            }
                        });
                    }


                }).start();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    void updateDB() {
        final ArrayList<PageEntity> list = new ArrayList<>();

        InputStream is = getResources().openRawResource(R.raw.vicnews);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        try {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                PageEntity tempEntry = new PageEntity(tokens[0], tokens[5], tokens[4]);
                list.add(tempEntry);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dbAcess.daoAccess().insertAll(list);
                }
            }).start();

        } catch (IOException e) {

        }
    }
}
