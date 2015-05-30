package com.group21.thermostat;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.util.ArrayList;


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

        ((TextView)findViewById(R.id.currentWeekday)).setText(thisWeekday);
        chartWork();

        ((ListView)findViewById(R.id.intervalsListView)).setAdapter(new Intervals_Adapter(this, R.id.intervalsListView,
                getIntent().getIntExtra("weekd", 0)));
    }

    private void chartWork() {


        LineChart chart = (LineChart) findViewById(R.id.chart);
        chart.setTouchEnabled(false);
        chart.setDrawGridBackground(false);
        chart.getLegend().setEnabled(false);
        chart.setDescription("");

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawLimitLinesBehindData(false);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawTopYLabelEntry(false);
        yAxis.setDrawLimitLinesBehindData(false);
        yAxis.setAxisMinValue(5);
        yAxis.setAxisMaxValue(40);
        yAxis = chart.getAxisRight();
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawTopYLabelEntry(false);
        yAxis.setDrawLimitLinesBehindData(false);

        ArrayList<TimeSection> times = MainActivity.schedule.week[getIntent().getIntExtra("weekd", 0)].listDaySections;
        ArrayList<Entry> temp_time = new ArrayList<Entry>();

        for (int i = 0 ; i < times.size()*2; i++) {
            if (i%2 == 0) {
                temp_time.add(new Entry((float)roundIt(MainActivity.schedule.getTempDay()), i));
            }
            else
                temp_time.add(new Entry((float)roundIt(MainActivity.schedule.getTempNight()), i));
        }

        LineDataSet setComp1 = new LineDataSet(temp_time, "Temperatures");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setValueTextSize(11f);
        setComp1.setValueTextColor(Color.parseColor("#03A9F4"));
        setComp1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "     " + value + " °C";
            }
        });


        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setComp1);

        ArrayList<String> time = new ArrayList<String>();
        for (int i = 0 ; i < times.size(); i++) {
            time.add(""+times.get(i).getHourStart() + ":" + times.get(i).getMinutesStart());
            time.add(""+times.get(i).getHourEnd() + ":" + times.get(i).getMinutesEnd());
        }

        LineData data = new LineData(time, dataSets);
        chart.setData(data);
        chart.invalidate(); // refresh
        chart.animateX(1000, Easing.EasingOption.EaseInCirc);
    }

    private double roundIt(double x) {
        return (double)Math.round(x * 10) / 10;
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


    public void plusButton(View v) {
        if (MainActivity.schedule.week[getIntent().getIntExtra("weekd", 0)].listDaySections.size()>=5) {
            Toast temp = Toast.makeText(this, "You already have 5 intervals!", Toast.LENGTH_SHORT);
            temp.show();
            return;
        }
        Custom_Dialog cdd=new Custom_Dialog(this, getIntent().getIntExtra("weekd", 0));
        cdd.show();
        cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ((Intervals_Adapter)((ListView) findViewById(R.id.intervalsListView)).getAdapter()).notifyDataSetChanged();
                chartWork();
            }
        });
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
