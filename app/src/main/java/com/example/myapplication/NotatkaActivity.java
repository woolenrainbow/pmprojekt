package com.example.myapplication;

import android.content.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class NotatkaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notatka);

        Fragment fragment = new MenuFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutMenu, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

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
        registerReceiver(mMessageReceiver,new IntentFilter("com.example.myapplication.REC_INCOMING"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("mes");
            Toast.makeText(NotatkaActivity.this,message,Toast.LENGTH_LONG).show();
        }
    };
  
    public void onBackPressed(){
        finish();
    }

}
