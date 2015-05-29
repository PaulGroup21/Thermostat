package com.group21.thermostat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;


public class Program_edit_Activity extends ActionBarActivity {

    SeekBar day;
    SeekBar night;
    float currentNight = MainActivity.schedule.getTempNight();
    float currentDay = MainActivity.schedule.getTempDay();
    TextView dayTemp;
    TextView nightTemp;
    TableRow[] tableRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_edit_);

        changeActionBarColor(new ColorDrawable(Color.parseColor("#03A9F4")));
        changeActionBarTitle("Program ");

        initializeUIelements();

        day.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    temperatureChanged(progress + 5, "day");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //temperatureChanged(day.getProgress()+ 5 , "day");
        day.setMax(25);
        day.setProgress((int)currentDay - 5);
        temperatureChanged(currentDay, "day");


        night.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    temperatureChanged(progress + 5, "night");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //temperatureChanged(day.getProgress()+ 5 , "night");
        night.setMax(25);
        night.setProgress((int)currentNight - 5);
        temperatureChanged(currentNight, "night");
    }



    private void changeActionBarColor(ColorDrawable color) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(color);
    }

    private void changeActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeUIelements() {
        day = (SeekBar)findViewById(R.id.daySeekbar);
        night =(SeekBar)findViewById(R.id.nightSeekbar);
        dayTemp = (TextView)findViewById(R.id.dayTemp);
        nightTemp = (TextView)findViewById(R.id.nightTemp);
        

        tableRows = new TableRow[7];
        tableRows[0] = (TableRow)findViewById(R.id.monday);
        tableRows[1] = (TableRow)findViewById(R.id.tuesday);
        tableRows[2] = (TableRow)findViewById(R.id.wednesday);
        tableRows[3] = (TableRow)findViewById(R.id.thursday);
        tableRows[4] = (TableRow)findViewById(R.id.friday);
        tableRows[5] = (TableRow)findViewById(R.id.saturday);
        tableRows[6] = (TableRow)findViewById(R.id.sunday);

        for (TableRow b : tableRows) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.monday:
                            openSchedule("Monday", 1);
                            break;
                        case R.id.tuesday:
                            openSchedule("Tuesday", 2);
                            break;
                        case R.id.wednesday:
                            openSchedule("Wednesday", 3);
                            break;
                        case R.id.thursday:
                            openSchedule("Thursday", 4);
                            break;
                        case R.id.friday:
                            openSchedule("Friday", 5);
                            break;
                        case R.id.saturday:
                            openSchedule("Saturday", 6);
                            break;
                        case R.id.sunday:
                            openSchedule("Sunday", 0);
                            break;
                    }
                }
            });
        }
    }

    private void openSchedule(String weekday, int weekd) {
        Intent intent = new Intent(this, Schedule_Activity.class);
        intent.putExtra("weekday", weekday);
        intent.putExtra("weekd", weekd);
        startActivity(intent);
    }

    private double CelsiusToFahr(float celsius) {
        return roundIt((float)(9.0/5.0) * celsius + 32);
    }

    private double roundIt(float x) {
        return (double)Math.round(x * 10) / 10;
    }

    private void temperatureChanged(float newTemp, String dayOrNight) {
        MainActivity.schedule.setChanged(true);

        if (dayOrNight.compareTo("day") == 0) {
            currentDay = newTemp;

            dayTemp.setText(roundIt(currentDay) + " °C / " + CelsiusToFahr(currentDay) + " °F");
            MainActivity.schedule.setTempDay(currentDay);
        }

        if (dayOrNight.compareTo("night") == 0) {
            currentNight = newTemp;

            nightTemp.setText(roundIt(currentNight) + " °C / " + CelsiusToFahr(currentNight) + " °F");
            MainActivity.schedule.setTempNight(currentNight);
        }
    }

    private float getCurrentDay() {
        return currentDay;
    }

    private float getCurrentNight() {
        return currentNight;
    }

    public void plusClick(View v) {

        if (v.getId() == R.id.dayPlus) {
            float newTemp = getCurrentDay()+0.1f;

            if (4.95f >= newTemp || newTemp >= 30.1f)
                return;

            temperatureChanged(newTemp, "day");

            //if ((newTemp == Math.floor(newTemp)) && !Float.isInfinite(newTemp)) {
                day.setProgress((int)newTemp - 5);
            //}
        }

        if (v.getId() == R.id.nightPlus) {
            float newTemp = getCurrentNight()+0.1f;

            if (4.95f >= newTemp || newTemp >= 30.1f)
                return;

            temperatureChanged(newTemp, "night");

            //if ((newTemp == Math.floor(newTemp)) && !Float.isInfinite(newTemp)) {
                night.setProgress((int)newTemp - 5);
            //}
        }

    }

    public void minusClick(View v) {
        if (v.getId() == R.id.dayMinus) {
            float newTemp = getCurrentDay()-0.1f;

            if (4.95f >= newTemp || newTemp >= 30.1f)
                return;

            temperatureChanged(newTemp, "day");

            //if ((newTemp == Math.floor(newTemp)) && !Float.isInfinite(newTemp)) {
                day.setProgress((int)newTemp - 5);
            //}
        }

        if (v.getId() == R.id.nightMinus) {
            float newTemp = getCurrentNight()-0.1f;

            if (4.95f >= newTemp || newTemp >= 30.1f)
                return;

            temperatureChanged(newTemp, "night");

            //if ((newTemp == Math.floor(newTemp)) && !Float.isInfinite(newTemp)) {
                night.setProgress((int)newTemp - 5);
            //}
        }

    }

}
