package com.example.aplikasitempahangelanggangsukan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity implements View.OnClickListener {

    private TextView cregister, registerUser;
    private EditText editTextFullName, editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    private Spinner spinneroccupation;
    private String Usertype;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        cregister = (TextView) findViewById(R.id.tv_registration_registercourt);

        registerUser = (Button) findViewById(R.id.btn_registration_register);
        registerUser.setOnClickListener(this);

        Usertype = "1";

        editTextFullName = (EditText) findViewById(R.id.et_register_fullname);
        editTextEmail = (EditText) findViewById(R.id.et_register_email);
        editTextPassword = (EditText) findViewById(R.id.et_register_password);

        spinneroccupation = (Spinner) findViewById(R.id.spinner_occupation);

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
        spinneroccupation.setAdapter(myAdapter);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_registration_register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String fullname = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String occupation = spinneroccupation.getSelectedItem().toString().trim();
        String usertype = Usertype.toString().trim();

        if(fullname.isEmpty()){
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();
                            userInfo.put("fullname", editTextFullName.getText().toString());
                            userInfo.put("email", editTextEmail.getText().toString());
                            userInfo.put("occupation", spinneroccupation.getSelectedItem().toString());
                            userInfo.put("usertype", "1");

                            df.set(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(registration.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), login.class));
                                    }else{
                                        Toast.makeText(registration.this, "Registration Failed! Try Again!", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                     //       User user = new User(fullname,email,occupation,usertype);

                     //       FirebaseDatabase.getInstance("https://tempahan-gelanggang-sukan-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
                     //               .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                     //               .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                     //                   @Override
                     //                   public void onComplete(@NonNull Task<Void> task) {
//
                     //                       if(task.isSuccessful()){
                      //                          Toast.makeText(registration.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                     //                           Intent intent = new Intent(registration.this, login.class);
                      //                          startActivity(intent);
                     //                           //redirect Login
                    //                        }else{
                   //                             Toast.makeText(registration.this, "Registration Failed! Try Again!", Toast.LENGTH_LONG).show();
                   //                         }
//
                    //                    }
                      //              });
                        }else{
                            //Log.d("---->",""+task.getException());
                            Toast.makeText(registration.this, "Registration Failed!!!!! Try Again!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

}