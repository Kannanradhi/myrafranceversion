package com.isteer.service.utility;

import android.content.Context;
import android.support.annotation.NonNull;

import com.isteer.service.model.WorkLogData;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationUtils {
    private Context c;

    public ValidationUtils(Context c) {
        this.c = c;
    }

    public Boolean isTextEmpty(String a) {
        return (a == null || a.equals(" ") || a.length() == 0);
    }

    public Boolean isValidWorkLog(@NonNull WorkLogData wl) {

        return (!isTextEmpty(wl.getEnd_time()) && !isTextEmpty(wl.getStart_time()) &&
                !isTextEmpty(wl.getService_type()) && !isTextEmpty(wl.getServiced_by()) && !isTextEmpty(wl.getVisited_date()) &&
                !isTextEmpty(wl.getProduct_name()) && !isTextEmpty(wl.getReplace_suggestion()) && !isTextEmpty("" + wl.getStatus())) ;
    }

    public boolean isValidTimeSelection(@NonNull WorkLogData wl) {
        return validTime(wl.getStart_time(), wl.getEnd_time());
    }


    public Integer integerFormat(String i) {
        if (i == null || i.equals(" ") || i.length() == 0 || i.contains(".")) {
            return 0;
        } else {
            return Integer.parseInt(i);
        }


    }

    public Double doubleFormat(String i) {
        if (i == null || i.equals(" ") || i.length() == 0) {
            return 0.;
        } else {
            return Double.parseDouble(i);
        }


    }

    public String doubleFormater(double d) {
        return new DecimalFormat("##.00").format(d);
    }

    public static String dateFormater(String date, String readformat, String writeFormat) {
        String f = "";
        try {
            SimpleDateFormat w = new SimpleDateFormat(writeFormat);
            SimpleDateFormat r = new SimpleDateFormat(readformat);
            f = w.format(r.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return f;
    }

    private Date getValidTime(String time) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("hh:mm");
        return sf.parse(time);
    }


    public boolean validTime(String from, String to) {

        try {
            return getValidTime(from).getTime() < getValidTime(to).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }

}
