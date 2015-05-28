package com.group21.thermostat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;


public class Program_edit_Activity extends ActionBarActivity {

    SeekBar day;
    SeekBar night;
    float currentNight;
    float currentDay;
    TextView dayTemp;
    TextView nightTemp;
    TextView[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_edit_);

        changeActionBarColor(new ColorDrawable(Color.parseColor("#03A9F4")));
        changeActionBarTitle("Program edit");

        initializeUIelements();

        day.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                temperatureChanged(progress + 5, "day");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        day.setMax(25);
        temperatureChanged(day.getProgress()+ 5 , "day");
        night.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                temperatureChanged(progress + 5, "night");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        temperatureChanged(day.getProgress()+ 5 , "night");
        night.setMax(25);
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
        buttons = new TextView[7];
        buttons[0] = (TextView)findViewById(R.id.mondayEDIT);
        buttons[1] = (TextView)findViewById(R.id.tuesdayEDIT);
        buttons[2] = (TextView)findViewById(R.id.wednesdayEDIT);
        buttons[3] = (TextView)findViewById(R.id.thursdayEDIT);
        buttons[4] = (TextView)findViewById(R.id.fridayEDIT);
        buttons[5] = (TextView)findViewById(R.id.saturdayEDIT);
        buttons[6] = (TextView)findViewById(R.id.sundayEDIT);

        for (TextView b : buttons) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.mondayEDIT:
                            openScheduleEdit("Monday");
                            break;
                        case R.id.tuesdayEDIT:
                            openScheduleEdit("Tuesday");
                            break;
                        case R.id.wednesdayEDIT:
                            openScheduleEdit("Wednesday");
                            break;
                        case R.id.thursdayEDIT:
                            openScheduleEdit("Thursday");
                            break;
                        case R.id.fridayEDIT:
                            openScheduleEdit("Friday");
                            break;
                        case R.id.saturdayEDIT:
                            openScheduleEdit("Saturday");
                            break;
                        case R.id.sundayEDIT:
                            openScheduleEdit("Sunday");
                            break;
                    }
                }
            });
        }
    }

    private void openScheduleEdit(String weekday) {
        Intent intent = new Intent(this, Schedule_Activity.class);
        intent.putExtra("weekday", weekday);
        startActivity(intent);
    }

    private double CelsiusToFahr(float celsius) {
        return roundIt((9/5) * celsius + 32);
    }

    private double roundIt(float x) {
        return Math.floor(x * 10) / 10;
    }

    private void temperatureChanged(float newTemp, String dayOrNight) {
        if (dayOrNight.compareTo("day") == 0) {
            currentDay = newTemp;

            dayTemp.setText(roundIt(currentDay) + " °C / " + CelsiusToFahr(currentDay) + " °F");
        }

        if (dayOrNight.compareTo("night") == 0) {
            currentNight = newTemp;

            nightTemp.setText(roundIt(currentNight) + " °C / " + CelsiusToFahr(currentNight) + " °F");
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

            temperatureChanged(newTemp, "day");

            if ((newTemp == Math.floor(newTemp)) && !Float.isInfinite(newTemp)) {
                day.setProgress((int)newTemp - 5);
            }
        }

        if (v.getId() == R.id.nightPlus) {
            float newTemp = getCurrentNight()+0.1f;

            temperatureChanged(newTemp, "night");

            if ((newTemp == Math.floor(newTemp)) && !Float.isInfinite(newTemp)) {
                night.setProgress((int)newTemp - 5);
            }
        }

    }

    public void minusClick(View v) {
        if (v.getId() == R.id.dayMinus) {
            float newTemp = getCurrentDay()-0.1f;

            temperatureChanged(newTemp, "day");

            if ((newTemp == Math.floor(newTemp)) && !Float.isInfinite(newTemp)) {
                day.setProgress((int)newTemp - 5);
            }
        }

        if (v.getId() == R.id.nightMinus) {
            float newTemp = getCurrentNight()-0.1f;

            temperatureChanged(newTemp, "night");

            if ((newTemp == Math.floor(newTemp)) && !Float.isInfinite(newTemp)) {
                night.setProgress((int)newTemp - 5);
            }
        }

    }

}
