package com.example.myapplication;


import androidx.fragment.app.Fragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = new MenuFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutMenu, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        DataBase dataBase = new DataBase(this);

        Button addBtn = (Button)findViewById(R.id.add);
        final Fragment f2=new Form();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, f2)
                        .commit();
            }
        });

        startService(new Intent(this, TaskMonitor.class));

    };
}
