package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaActivity extends AppCompatActivity {
    private List<Task> taskList = null;
    private Map<String, Integer> values = null;
    private Spinner spinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);


        final DataBase dataBase = new DataBase(this);
        final TaskDAO taskDAO = new TaskDAO(dataBase.getWritableDatabase());
        taskList = taskDAO.getAll();

        String daysArray[] = {"Niedziela", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota"};
        values = new HashMap<String, Integer>();
        values.put(daysArray[0], 0);
        values.put(daysArray[1], 1);
        values.put(daysArray[2], 2);
        values.put(daysArray[3], 3);
        values.put(daysArray[4], 4);
        values.put(daysArray[5], 5);
        values.put(daysArray[6], 6);
        spinner = (Spinner) findViewById(R.id.spinner44);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, daysArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = findViewById(R.id.spinner44);
                String value = spinner.getSelectedItem().toString();
                List<Task> tasks = new ArrayList<Task>();
                for (Task task : taskList) {
                    if (task.day == values.get(value)) {
                        tasks.add(task);
                    }
                }
                final ArrayAdapter<Task> tasksAdapter = new ArrayAdapter<Task>(getApplicationContext(), android.R.layout.simple_list_item_1, tasks){
                  @Override
                  public View getView(int position, View convertView, ViewGroup parent){
                      View view = super.getView(position, convertView, parent);
                      TextView tv = view.findViewById(android.R.id.text1);
                      tv.setTextColor(Color.BLACK);
                      return view;
                  }
                };
                final ListView listView = findViewById(R.id.listview);
                listView.setAdapter(tasksAdapter);
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                        String selectedItem = parent.getItemAtPosition(position).toString();
                        AlertDialog.Builder builder = new AlertDialog.Builder(ListaActivity.this);
                        builder.setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getApplicationContext(), "asdas", Toast.LENGTH_LONG).show();
                                taskDAO.remove((Task)parent.getItemAtPosition(position));
                                //parent.removeViewAt(position);
                                tasksAdapter.remove(tasksAdapter.getItem(position));
                                tasksAdapter.notifyDataSetChanged();
                                taskList = taskDAO.getAll();
                                //listView.invalidateViews();
                            }
                        });
                        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getApplicationContext(), "aaaas", Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.setMessage("Czy usunąć wpis?");
                        builder.setTitle("Usuń");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        //Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
            }
        });

        Fragment fragment = new MenuFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutMenu, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

    public void onBackPressed(){
        finish();
    }
}
