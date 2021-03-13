package com.example.imma;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class Utils {
    public static ColorDrawable[] vibrantLightColorList={
            new ColorDrawable(Color.parseColor("#ffeead")),
            new ColorDrawable(Color.parseColor("#93cfb3")),
            new ColorDrawable(Color.parseColor("#fd7a7a")),
            new ColorDrawable(Color.parseColor("#faca5f")),
            new ColorDrawable(Color.parseColor("#1ba798")),
            new ColorDrawable(Color.parseColor("#6aa9ae")),
            new ColorDrawable(Color.parseColor("#ffbf27")),
            new ColorDrawable(Color.parseColor("#d93947")),
    };
    public static ColorDrawable getRandomDrawableColor(){
        int yxz=new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[yxz];
    }
    public static String DateToTimeFormat(String oldstringDate){
    PrettyTime prettyTime= new PrettyTime(new Locale(getCountry()));
    String isTime= null;
    try {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date =simpleDateFormat.parse(oldstringDate);
        isTime = prettyTime.format(date);
    }
    catch (ParseException e){
        e.printStackTrace();
    }
    return isTime;
}
    public static String DateFormat(String oldstringDate){
        String newDate;
        SimpleDateFormat dateFormat= new SimpleDateFormat("E, d MM yyy", new Locale(getCountry()));
        try {
            Date date = new SimpleDateFormat("yyy-MMM-dd'T'HH:mm:ss'Z'").parse(oldstringDate);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            newDate=oldstringDate;
        }
        catch (ParseException e){
            e.printStackTrace();
            newDate=oldstringDate;
        }
        return newDate;
}
    public static String getCountry(){
        Locale locale=Locale.getDefault();
        String country=locale.getCountry();
        return country.toLowerCase();
    }
}
