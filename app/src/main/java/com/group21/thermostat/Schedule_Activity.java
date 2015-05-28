package com.group21.thermostat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


public class Schedule_Activity extends ActionBarActivity {

    String thisWeekday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_);

        initializeUIelements();

        changeActionBarColor(new ColorDrawable(Color.parseColor("#03A9F4")));
        changeActionBarTitle("Temp change schedule");

        thisWeekday = getIntent().getStringExtra("weekday");
    }


    private void changeActionBarColor(ColorDrawable color) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(color);
    }

    private void changeActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }

    private void initializeUIelements() {
//        day = (SeekBar)findViewById(R.id.daySeekbar);
//        night =(SeekBar)findViewById(R.id.nightSeekbar);
//        dayTemp = (TextView)findViewById(R.id.dayTemp);
//        nightTemp = (TextView)findViewById(R.id.nightTemp);
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
}
