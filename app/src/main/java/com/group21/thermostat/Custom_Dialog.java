package com.group21.thermostat;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

/**
 * Created by diesersamat on 29/05/15.
 */
public class Custom_Dialog extends Dialog {

    private final int dayWeek;
    public Activity c;
    NumberPicker from_hours;
    NumberPicker to_hours;
    NumberPicker from_minutes;
    NumberPicker to_minutes;
    Button readyButton;

    public Custom_Dialog(Activity a, int dayWeek) {
        super(a);
        this.dayWeek = dayWeek;
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_layout);

        from_hours = (NumberPicker)findViewById(R.id.from_hours);
        to_hours= (NumberPicker)findViewById(R.id.to_hours);
        from_minutes= (NumberPicker)findViewById(R.id.from_minutes);
        to_minutes= (NumberPicker)findViewById(R.id.to_minutes);
        readyButton = (Button)findViewById(R.id.readyButton);

        from_hours.setMaxValue(23);
        to_hours.setMaxValue(23);

        from_minutes.setMaxValue(59);
        to_minutes.setMaxValue(59);

        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainActivity.schedule.add(dayWeek + 1, from_hours.getValue(), from_minutes.getValue(),
                                to_hours.getValue(), to_minutes.getValue())) {
                    closeDialog();
                }
                 else {
                   Toast temp = Toast.makeText(c.getBaseContext(), "This interval is incorrect!", Toast.LENGTH_SHORT);
                    temp.show();
                }
            }
        });

        ((Button)findViewById(R.id.dismiss)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
    }

    void closeDialog() {
        dismiss();
    }
}
