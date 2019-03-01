package com.isteer.service.utility;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePicker extends TextInputEditText {

    private TimePickerDialog d;
    private TimePicker a;
    private Context t;
    private Boolean f = false;
    final String HOUR_24 = "HH:mm";
    final String HOUR_12 = "hh:mm";


    public TimePicker(Context context) {
        super(context);
        this.t = context;
        a = this;
        setFocusable(false);
        setClickable(true);


    }

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(false);
        setClickable(true);

    }

    public TimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(false);
        setClickable(true);
    }


    public void setClickListener(final TimePicker v) {


        final Calendar c = Calendar.getInstance();
        final Date m = c.getTime();


        d = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

                SimpleDateFormat sf = new SimpleDateFormat("HH:mm");

                try {
                    v.setText(sf.format(sf.parse(hourOfDay + ":" + minute)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
        d.show();
    }

    public static class TimeOnclickListener implements OnClickListener {
        Context c;

        public TimeOnclickListener(Context c) {
            this.c = c;
        }

        @Override
        public void onClick(View v) {
            new TimePicker(c).setClickListener((TimePicker) v);
        }
    }

    public String getTimeText() {
        return super.getText().toString();
    }

    public void setAllowPast(boolean allow) {
        f = allow;
    }


}




