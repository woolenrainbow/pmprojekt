package com.example.myapplication;

import android.app.TimePickerDialog;
import android.content.Context;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class Form extends Fragment {
    EditText timeFrom = null;
    EditText timeTo = null;
    Map<String, Integer> values = null;
    Spinner spinner = null;
    EditText taskInput = null;

    public Form() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_form,container,false);
        //EditText timeFrom = view.findViewById(R.id.editText3);//findViewById(R.id.editText1);
        timeFrom = view.findViewById(R.id.editText3);
        timeFrom.setInputType(InputType.TYPE_NULL);
        timeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog picker;
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String _sHour = sHour < 10 ? "0" + sHour : String.valueOf(sHour);
                                String _sMinute = sMinute < 10 ? "0" + sMinute : String.valueOf(sMinute);
                                timeFrom.setText(_sHour + ":" + _sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        timeTo = view.findViewById(R.id.editText4);
        timeTo.setInputType(InputType.TYPE_NULL);
        timeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog picker;
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String _sHour = sHour < 10 ? "0" + sHour : String.valueOf(sHour);
                                String _sMinute = sMinute < 10 ? "0" + sMinute : String.valueOf(sMinute);
                                timeTo.setText(_sHour + ":" + _sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        //String daysArray[] = {"Sunday","Monday","Tuesday", "Wednesday","Thursday","Friday", "Saturday"};
        String daysArray[] = {getResources().getString(R.string.niedziela), getResources().getString(R.string.poniedzialek), getResources().getString(R.string.wtorek), getResources().getString(R.string.sroda), getResources().getString(R.string.czwartek), getResources().getString(R.string.piatek), getResources().getString(R.string.sobota)};
        values = new HashMap<String, Integer>();
        values.put(daysArray[0], 0);
        values.put(daysArray[1], 1);
        values.put(daysArray[2], 2);
        values.put(daysArray[3], 3);
        values.put(daysArray[4], 4);
        values.put(daysArray[5], 5);
        values.put(daysArray[6], 6);
        spinner = (Spinner)view.findViewById(R.id.spinner2);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, daysArray);
         spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

        taskInput = view.findViewById(R.id.editText2);
        Button button = view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task, day, from, to;

                task = taskInput.getText().toString();
                day = spinner.getSelectedItem().toString();
                from = timeFrom.getText().toString();
                to = timeTo.getText().toString();

                if(task != "" && day != "" && from != "" && to != ""){
                    Task newTask = new Task(0, task, values.get(day), from, to);
                    if(newTask.validObject()){
                        DataBase dataBase = new DataBase(getActivity());
                        TaskDAO taskDAO = new TaskDAO(dataBase.getWritableDatabase());
                        taskDAO.save(newTask);
                        Toast.makeText(getActivity(), getResources().getString(R.string.dodano_wpis), Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().beginTransaction().remove(Form.this).commit();
                        //getActivity().onBackPressed();
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.wypelnij_form), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.wypelnij_form2), Toast.LENGTH_LONG).show();
                }
            }
        });

        Button cancel = view.findViewById(R.id.button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(Form.this).commit();
            }
        });

        return view;
    }

}
