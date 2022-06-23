package com.example.aplikasitempahangelanggangsukan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class courtregister extends AppCompatActivity implements View.OnClickListener{

    private TextView registerCourt;
    private EditText editTextCourtName, editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    private Spinner spinnercourttype;
    private String Usertype;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courtregister);

        Usertype = "2";
        mAuth = FirebaseAuth.getInstance();
        registerCourt = (Button) findViewById(R.id.btn_courtregister);
        registerCourt.setOnClickListener(this);

        spinnercourttype = (Spinner) findViewById(R.id.spinner_courttype);
        editTextCourtName = (EditText) findViewById(R.id.et_registercourt_courtname);
        editTextEmail = (EditText) findViewById(R.id.et_registercourt_email);
        editTextPassword = (EditText) findViewById(R.id.et_registercourt_password);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(courtregister.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.courttype));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercourttype.setAdapter(myAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_courtregister:
                registercser();
                break;
        }

    }

    private void registercser() {
        String fullname = editTextCourtName.getText().toString().trim();
        String courtName = editTextCourtName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String courttype = spinnercourttype.getSelectedItem().toString().trim();
        String usertype = Usertype.toString().trim();

        if(fullname.isEmpty()){
            editTextCourtName.setError("Full name is required!");
            editTextCourtName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email Address is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid Email Address!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() <8){
            editTextPassword.setError("Minimum Password length should be 8 characters!");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(fullname,email,courttype,usertype);

                            FirebaseDatabase.getInstance("https://tempahan-gelanggang-sukan-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
                                    .child(fullname)
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                               courtdetails courtsD = new courtdetails(courtName,courttype);
                                                FirebaseDatabase.getInstance("https://tempahan-gelanggang-sukan-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Courts")
                                                               .child(courtName).setValue(courtsD);

                                                Toast.makeText(courtregister.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(courtregister.this, login.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(courtregister.this, "Registration Failed! Try Again!", Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    });
                        }else{
                            //Log.d("---->",""+task.getException());
                            Toast.makeText(courtregister.this, "Registration Failed!!!!! Try Again!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

}