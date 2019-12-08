package com.example.myapplication;

import android.provider.ContactsContract;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;

public class Task {
    public int id;
    public String name;
    public int day;
    public Date start = null;
    public Date finish = null;

    public Task(int id, String name, int day, Date start, Date finish){
        this.id = id;
        this.name = name;
        this.day = day;
        this.start = start;
        this.finish = finish;
    }

    public Task(int id, String name, int day, String start, String finish){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        this.id = id;
        this.name = name;
        this.day = day;
        try {
            this.start = sdf.parse(start);
            this.finish = sdf.parse(finish);
        }
        catch (ParseException ex){
            Log.e("error", ex.getMessage());
        }
    }
}
