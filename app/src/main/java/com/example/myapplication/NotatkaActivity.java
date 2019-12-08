package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class NotatkaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notatka);
        Button btn1 = findViewById(R.id.menu_notatka);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(NotatkaActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        Button btn2 = findViewById(R.id.menu_zapisz_notatka);
        EditText t = findViewById(R.id.editText);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String string = sharedPref.getString("key", null);
        if (string != null) {
            t.setText(string);
        }
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText t = findViewById(R.id.editText);
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("key", t.getText().toString() );
                editor.commit();
            }
        });
    }
}
