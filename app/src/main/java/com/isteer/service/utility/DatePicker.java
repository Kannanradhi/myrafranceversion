package com.isteer.service.utility;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePicker extends TextInputEditText {

    private DatePickerDialog d;
    private DatePicker a;
    private Context t;
    private Boolean f = false;
    final String US_FORMATE = "yyyy/mm/dd";
    final String IN_FORMATE = "dd/mm/yy";
    final String NUMBER = "yyyymmdd";


    public DatePicker(Context context) {
        super(context);
        this.t = context;
        a = this;
        setFocusable(false);
        setClickable(true);


    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(false);
        setClickable(true);

    }

    public DatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(false);
        setClickable(true);
    }


    public void setClickListener(final DatePicker v) {


        final Calendar c = Calendar.getInstance();


        final Date m = c.getTime();


        d = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {

                try {
                    Date of = new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(m));
                    Date df = new SimpleDateFormat("dd/MM/yyyy").parse((dayOfMonth) + "/" + (month + 1) + "/" + year);
                    if (df.getTime() > of.getTime()) {
                        Toast.makeText(getContext(), "Future date not allowed", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (f) {
                        if (df.getTime() <= of.getTime()) {
                            v.setText((dayOfMonth) + "/" + (month + 1) + "/" + year);
                        }

                    }else{
                        if (df.getTime() == of.getTime()) {
                            v.setText((dayOfMonth) + "/" + (month + 1) + "/" + year);
                        }else{
                            Toast.makeText(getContext(), "Past date not allowed", Toast.LENGTH_SHORT).show();

                        }


                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));


        d.show();
    }

    public static class CustomOnclickListener implements OnClickListener {
        Context c;

        public CustomOnclickListener(Context c) {
            this.c = c;
        }

        @Override
        public void onClick(View v) {
            new DatePicker(c).setClickListener((DatePicker) v);
        }
    }

    public String getDateText() {
        return super.getText().toString();
    }

    public void setAllowPast(boolean allow) {
        f = allow;
    }


}




