package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;

public class TaskHelper {
    public static final String TAB_NAME = "Task";

    public static class TaskColums{
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DAY = "day";
        public static final String START = "start";
        public static final String FINISH = "finish";
    }

    public static void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE " + TAB_NAME + "(" +
                TaskColums.ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                TaskColums.NAME +" TEXT,"+
                TaskColums.DAY + " INTEGER,"+
                TaskColums.START + " TEXT,"+
                TaskColums.FINISH + " TEXT);");
    }
}
