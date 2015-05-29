package com.group21.thermostat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class Intervals_Adapter extends ArrayAdapter {

    int weekD;
    Context thisContext;

    public Intervals_Adapter(Context context, int resource, int weekD) {
        super(context, resource);
        thisContext = context;
        this.weekD = weekD;
    }

    @Override
    public int getCount() {
        return MainActivity.schedule.week[weekD].listDaySections.size();
    }
    static class ViewHolder {

        private TextView interval;
        private ImageButton deletebutton;

    }


    public View getView(final int position, View convertView, final ViewGroup parent)
    {

        ViewHolder mViewHolder = null;

        if(convertView == null){
            mViewHolder = new ViewHolder();
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.interval_cell, parent, false);
            mViewHolder.deletebutton = (ImageButton)convertView.findViewById(R.id.deletebutton);
            mViewHolder.interval = (TextView)convertView.findViewById(R.id.interval);
            convertView.setTag(mViewHolder);

        }else
        {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        TimeSection thisTS =  MainActivity.schedule.week[weekD].listDaySections.get(position);
        mViewHolder.interval.setText(thisTS.getHourStart()+":" + thisTS.getMinutesStart()
        + " – " + thisTS.getHourEnd() + ":" + thisTS.getMinutesEnd());
        mViewHolder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.schedule.week[weekD].listDaySections.remove(position);
                update();
            }
        });

        return convertView;
    }

    private void update() {
        this.notifyDataSetChanged();
    }

}