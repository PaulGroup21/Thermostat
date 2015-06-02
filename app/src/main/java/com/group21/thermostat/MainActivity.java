package com.group21.thermostat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    public static WeekSchedule schedule;

    SeekBar temperatureSeekbar;
    float currentTemp;
    TextView programmedTemp;
    TextView realTemp;
    TextView dayTime;
    RadioButton permanent;
    RadioButton program;
    Boolean isByProgram;
    ImageView sun;
    ImageView moon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUIelements();

        changeActionBarColor(new ColorDrawable(Color.parseColor("#03A9F4")));
        changeActionBarTitle(getWeekDay());

        program.setChecked(true);
        temperatureSeekbar.setMax(25);

        // Time update
        final MyTimer timer = new MyTimer();

        // Deserialize
        schedule = readFromFile(this);
        if (schedule == null) {
            schedule = new WeekSchedule();
        } else {
            program.setChecked(!schedule.isPermanent());
            permanent.setChecked(schedule.isPermanent());
            if (schedule.isPermanent()) {
                temperatureChanged(schedule.getTempPermanent());
            }
        }

        if (! schedule.isPermanent()) {
            if (schedule.shouldSwitchToDayTemperature(timer.getDay(), timer.getHour(),
                    timer.getMinute())) {
                temperatureSeekbar.setProgress((int) schedule.getTempDay() - 5);
                temperatureChanged(schedule.getTempDay());
                timer.switchMode();
                moon.setVisibility(View.INVISIBLE);
                sun.setVisibility(View.VISIBLE);
            } else {
                temperatureSeekbar.setProgress((int) schedule.getTempNight() - 5);
                temperatureChanged(schedule.getTempNight());
                moon.setVisibility(View.VISIBLE);
                sun.setVisibility(View.INVISIBLE);
            }
        }

        temperatureSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    temperatureChanged(progress + 5);
                }

                schedule.setTemporary(true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        program.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isByProgram = isChecked;
                schedule.setPermanent(!isByProgram);
                updateView(timer);
            }
        });


        updateView(timer);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                updateView(timer);
                timer.update();

                handler.postDelayed(this, 200);
            }
        }, 200);
    }

    protected void onDestroy() {
        saveToFile(this, schedule);
        super.onDestroy();
    }

    private void updateView(MyTimer timer) {
        if (!schedule.isPermanent()) {

            boolean shouldBeDay = schedule.shouldSwitchToDayTemperature(timer.getDay(),
                    timer.getHour(), timer.getMinute());

            if (shouldBeDay && sun.getVisibility() == View.INVISIBLE) {
                moon.setVisibility(View.INVISIBLE);
                sun.setVisibility(View.VISIBLE);
            }

            if (!shouldBeDay && moon.getVisibility() == View.INVISIBLE) {
                moon.setVisibility(View.VISIBLE);
                sun.setVisibility(View.INVISIBLE);
            }

            if (shouldBeDay && (!timer.isDay() || schedule.isChanged()
                    && !schedule.isTemporary())) {
                // switch to day
                temperatureSeekbar.setProgress((int) schedule.getTempDay() - 5);
                temperatureChanged(schedule.getTempDay());

                if (!schedule.isChanged())
                    timer.switchMode();

                schedule.setChanged(false);
                schedule.setTemporary(false);
                moon.setVisibility(View.INVISIBLE);
                sun.setVisibility(View.VISIBLE);
            }

            if (!shouldBeDay && (timer.isDay() || schedule.isChanged()
                    && !schedule.isTemporary())) {
                // switch to night
                temperatureSeekbar.setProgress((int) schedule.getTempNight() - 5);
                temperatureChanged(schedule.getTempNight());

                if (!schedule.isChanged())
                    timer.switchMode();

                schedule.setChanged(false);
                schedule.setTemporary(false);
                moon.setVisibility(View.VISIBLE);
                sun.setVisibility(View.INVISIBLE);
            }

            dayTime.setText(timer.toString() + "\nMode: "
                    + (timer.isDay() ? "Day" : "Night"));

        } else {
            dayTime.setText(timer.toString() + "\nMode: Permanent");
            moon.setVisibility(View.INVISIBLE);
            sun.setVisibility(View.INVISIBLE);
        }

        changeActionBarTitle(getWeekDay());
    }

    private void initializeUIelements() {
        temperatureSeekbar = (SeekBar)findViewById(R.id.temperatureSeekbar);
        programmedTemp =(TextView)findViewById(R.id.programmedTemp);
        realTemp =(TextView)findViewById(R.id.realTemp);
        dayTime = (TextView)findViewById(R.id.time);
        permanent = (RadioButton)findViewById(R.id.permanentRadio);
        program = (RadioButton)findViewById(R.id.programRadio);
        sun = (ImageView)findViewById(R.id.imageView1);
        moon = (ImageView)findViewById(R.id.imageView2);
    }

    public void plusClick(View v) {
        float newTemp = getCurrentSetTemperature() + 0.1f;

        if (4.95f >= newTemp || newTemp >= 30.1f)
            return;

        temperatureChanged(newTemp);

        //if (isIntegerValue(newTemp)) {
            temperatureSeekbar.setProgress((int)newTemp - 5);
        //}
    }

    public void minusClick(View v) {
        float newTemp = getCurrentSetTemperature() - 0.1f;

        if (4.95f >= newTemp || newTemp >= 30.1f)
            return;

        temperatureChanged(newTemp);

        //if (isIntegerValue(newTemp)) {
            temperatureSeekbar.setProgress((int)newTemp - 5);
        //}
    }

    // Constant with a file name
    public static String fileName = "therm.ser";

    // Serializes an object and saves it to a file
    public void saveToFile(Context context, WeekSchedule schedule) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(schedule);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Creates an object by reading it from a file
    public static WeekSchedule readFromFile(Context context) {
        WeekSchedule weekSchedule = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            weekSchedule = (WeekSchedule) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return weekSchedule;
    }

    private boolean isIntegerValue(float bd) {
        return bd == Math.floor(bd) && !Float.isInfinite(bd);
    }

    private double CelsiusToFahr(double celsius) {
        return roundIt((9.0 / 5.0) * celsius + 32);
    }

    private double roundIt(double x) {
        return (double)Math.round(x * 10) / 10;
    }

    private void temperatureChanged(float newTemp) {
        if (newTemp != schedule.getTempPermanent() && schedule.isPermanent()) {
            schedule.setTempPermanent(newTemp);
        }

        currentTemp = newTemp;

        programmedTemp.setText(roundIt(newTemp) + " °C / " + CelsiusToFahr(newTemp) + " °F");

        //TODO manage realTemp??
        realTemp.setText(roundIt(newTemp) + " °C / " + CelsiusToFahr(newTemp) + " °F");
    }

    private float getCurrentSetTemperature() {
        return currentTemp;
    }

    private void changeActionBarColor(ColorDrawable color) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(color);
    }

    private void changeActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }

    public void editProgramClick(View v) {
        Intent intent = new Intent(this, Program_edit_Activity.class);
        startActivity(intent);
    }

    private String getWeekDay() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(date.getTime());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
