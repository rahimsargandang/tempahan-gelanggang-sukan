package com.example.aplikasitempahangelanggangsukan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class courtregister extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courtregister);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner_courttype);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(courtregister.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.courttype));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
    }
}