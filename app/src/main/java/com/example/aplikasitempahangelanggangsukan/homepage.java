package com.example.aplikasitempahangelanggangsukan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasitempahangelanggangsukan.Adapter.courtListAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class homepage extends AppCompatActivity {

    private static final String TAG = homepage.class.getSimpleName();
    DrawerLayout drawerLayout;
    FirebaseFirestore dbFireStoreInstance;
    RecyclerView recyclerView;
    courtListAdapter courtListAdapter;
    ArrayList<Courts> courtsArrayList;
    FirebaseFirestore db;
    static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        preferences = getSharedPreferences("Aplikasi_Tempahan", MODE_PRIVATE);
        drawerLayout = findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.recyclerView_homepage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        courtsArrayList = new ArrayList<Courts>();

        EventChangeListener();


    }

    public void OnListItemClicked(Courts court){

        Intent intent = new Intent(homepage.this,BookingActivity.class);
        intent.putExtra("courtObj", court);
        startActivity(intent);
        closeDrawer(drawerLayout);
    }

    private void EventChangeListener() {

        dbFireStoreInstance = FirebaseFirestore.getInstance();
        dbFireStoreInstance.collection("Courts")
                .get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(homepage.this, "Error getting data", Toast.LENGTH_LONG).show();
                    }
                })

                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d(TAG, "onSuccess: LIST EMPTY");
                            return;
                        } else {

                            List<Courts> types = documentSnapshots.toObjects(Courts.class);
                            courtsArrayList.addAll(types);



                            recyclerView.setAdapter(new courtListAdapter(homepage.this,homepage.this, courtsArrayList));

                            Log.d(TAG, "Firebase_onSuccess: " + documentSnapshots);
                        }
                    }});

//        db.collection("Courts").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                if(error != null){
//                    Log.e("Firestore error",error.getMessage());
//                    return;
//                }
//
//
//
//                for (DocumentChange dc : value.getDocumentChanges()){
//
//                    if (dc.getType() == DocumentChange.Type.ADDED){
//
//                        courtsArrayList.add(dc.getDocument().toObject(Courts.class));
//
//                    }
//
//
//
//                }
//
//            }
//        });

    }


    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(@NonNull DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(@NonNull DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        recreate();
    }

    public void ClickDashboard(View view){
        //redirect to activity
        redirectActivity(this, mybooking.class);
    }

    public void ClickAboutUs(View view){
        redirectActivity(this, myaccount.class);
    }

    public void ClickLogOut(View view){
        logout(this);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                preferences.edit().remove("userEmail").commit();
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }


}