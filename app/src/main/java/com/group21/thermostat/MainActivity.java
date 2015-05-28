package com.group21.thermostat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    SeekBar temperatureSeekbar;
    private double currentTemp;
    TextView programmedTemp;
    TextView realTemp;
    RadioButton permanent;
    RadioButton program;
    Boolean isByProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUIelements();

        changeActionBarColor(new ColorDrawable(Color.parseColor("#03A9F4")));
        Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        changeActionBarTitle(getWeekDay());



        temperatureSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                temperatureChanged(progress + 5);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        temperatureSeekbar.setMax(25);

        program.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isByProgram = isChecked;
            }
        });
    }

    private void initializeUIelements() {
        temperatureSeekbar = (SeekBar)findViewById(R.id.temperatureSeekbar);
        programmedTemp =(TextView)findViewById(R.id.programmedTemp);
        realTemp =(TextView)findViewById(R.id.realTemp);
        permanent = (RadioButton)findViewById(R.id.permanentRadio);
        program = (RadioButton)findViewById(R.id.programRadio);
    }

    public void plusClick(View v) {
        double newTemp = roundIt(getCurrentSetTemperature()+0.1);

        temperatureChanged(newTemp);

        if ((newTemp == Math.floor(newTemp)) && !Double.isInfinite(newTemp)) {
            temperatureSeekbar.setProgress((int)Math.floor(newTemp)- 5);
        }
    }

    public void minusClick(View v) {
        double newTemp = roundIt(getCurrentSetTemperature()- 0.1);

        temperatureChanged(newTemp);

        if ((newTemp == Math.floor(newTemp)) && !Double.isInfinite(newTemp)) {
            temperatureSeekbar.setProgress((int) Math.floor(newTemp) - 5);
        }

    }

    private double CelsiusToFahr(double celsius) {
        return roundIt((9/5) * celsius + 32);
    }

    private double roundIt(double x) {
        return Math.floor(x * 10) / 10;
    }

    private void temperatureChanged(double newTemp) {
        currentTemp = roundIt(newTemp);

        programmedTemp.setText(currentTemp + " °C / " + CelsiusToFahr(currentTemp) + " °F");

        //TODO manage realTemp??
        realTemp.setText(currentTemp + " °C / " + CelsiusToFahr(currentTemp) + " °F");
    }

    private double getCurrentSetTemperature() {
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
