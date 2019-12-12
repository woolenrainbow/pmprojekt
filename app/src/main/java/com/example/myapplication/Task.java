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
            Date _start = sdf.parse(start);
            Date _finish = sdf.parse(finish);
            if(!_start.before(_finish)){
                throw new ParseException("start after finish",0);
            }
            this.start = _start;
            this.finish = _finish;
        }
        catch (ParseException ex){
            Log.e("error", ex.getMessage());
        }
    }

    public boolean validObject(){
        if(start != null && finish != null)
            return true;
        return false;
    }
}
