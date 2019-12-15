package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements DAO<Task> {
    private SQLiteDatabase sqLiteDatabase;
    public TaskDAO(SQLiteDatabase sqLiteDatabase){
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public void save(Task element) {
        String start = String.valueOf(element.start.getHours()) + ":" + String.valueOf(element.start.getMinutes());
        String finish = String.valueOf(element.finish.getHours()) + ":" + String.valueOf(element.finish.getMinutes());
        sqLiteDatabase.execSQL("INSERT INTO " + TaskHelper.TAB_NAME +"("+
                TaskHelper.TaskColums.NAME + ", "+
                TaskHelper.TaskColums.DAY + ", "+
                TaskHelper.TaskColums.START + ", "+
                TaskHelper.TaskColums.FINISH +") VALUES('" +
                element.name+ "', "+ element.day + ",'" +
                start+"','"+ finish+"' )");
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<Task>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ TaskHelper.TAB_NAME, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            tasks.add(new Task(cursor.getInt(cursor.getColumnIndex(TaskHelper.TaskColums.ID)),
                    cursor.getString(cursor.getColumnIndex(TaskHelper.TaskColums.NAME)),
                    cursor.getInt(cursor.getColumnIndex(TaskHelper.TaskColums.DAY)),
                    cursor.getString(cursor.getColumnIndex(TaskHelper.TaskColums.START)),
                    cursor.getString(cursor.getColumnIndex(TaskHelper.TaskColums.FINISH))));
            cursor.moveToNext();
        }
        return tasks;
    }

    @Override
    public void remove(Task element) {
        sqLiteDatabase.execSQL("DELETE FROM "+ TaskHelper.TAB_NAME + " WHERE " + TaskHelper.TaskColums.ID + " = " + element.id);
    }
}