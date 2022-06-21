package com.example.aplikasitempahangelanggangsukan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;

public class registration extends AppCompatActivity {

    private TextView cregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        cregister = (TextView) findViewById(R.id.tv_registration_registercourt);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner_occupation);

        cregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registration.this, courtregister.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(registration.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.occupation));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);


    }





}