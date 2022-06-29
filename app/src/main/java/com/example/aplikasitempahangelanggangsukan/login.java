package com.example.aplikasitempahangelanggangsukan;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity implements View.OnClickListener {

    private Button Login;
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = (Button) findViewById(R.id.btn_login);
        Login.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.et_email_login);
        editTextPassword = (EditText)  findViewById(R.id.et_password_login);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                userLogin();
                break;
        }
    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter valid email address!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 8){
            editTextPassword.setError("Minimum password length is 8 characters!");
            editTextPassword.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String userkey;

                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    userkey = user.getUid();

                    if (user.isEmailVerified()){

                        DocumentReference df = fStore.collection("Users").document(userkey);
                        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                documentSnapshot.getData();
                                String ut = documentSnapshot.getString("usertype");
                                Log.d(TAG,ut); 
                                if (ut.equals("1")){

                                    SharedPreferences.Editor editor = getSharedPreferences("Aplikasi_Tempahan", MODE_PRIVATE).edit();
                                    editor.putString("userEmail", user.getEmail());
                                    editor.apply();

                                    startActivity(new Intent(login.this, homepage.class));  

                                }else {
                                    startActivity(new Intent(login.this, courthomepage.class));
                                }
                //            String ut = documentSnapshot.getString("usertype");
                //            Log.d(TAG,ut);
                //            if (ut.equals("1")){
                //                startActivity(new Intent(login.this, homepage.class));
                //            }else {
                //                startActivity(new Intent(login.this, courthomepage.class));
                //            }
                            }
                        });

            //    FirebaseDatabase.getInstance("https://tempahan-gelanggang-sukan-default-rtdb.asia-southeast1.firebasedatabase.app/")
            //                    .getReference().child("Users").child(userkey).addListenerForSingleValueEvent(new ValueEventListener() {
            //                @Override
            //                public void onDataChange(@NonNull DataSnapshot snapshot) {
            //
            //                    snapshot.getChildren();{
            //                    //String value = (String) snapshot.child("usertype").getValue(String.class);
            //                    String ut = String.valueOf(snapshot.child("usertype").getValue());
            //                        Log.d(TAG,ut);
            //                        if(ut.equals("1")){
            //                            startActivity(new Intent(login.this, homepage.class));
            //                        }else{
            //                            startActivity(new Intent(login.this, courthomepage.class));
            //                        }
            //                    }
            //
            //
            //                }
            //
            //                @Override
            //                public void onCancelled(@NonNull DatabaseError error) {
            //
            //                }
            //            });
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(login.this, "Check your email to verify account.", Toast.LENGTH_LONG).show();
                    }


                }else{
                    Toast.makeText(login.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }

            }
        });

    }



}