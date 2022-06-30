package com.example.aplikasitempahangelanggangsukan;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aplikasitempahangelanggangsukan.Adapter.MyBookingAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class mybooking extends AppCompatActivity {

    private static final String TAG = BookingActivity.class.getSimpleName();
    ArrayList<TimeSlots> timeSlotsArrayList = new ArrayList<>();
    FirebaseFirestore dbFireStoreInstance;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView_time_slot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);
        drawerLayout = findViewById(R.id.drawer_layout);
        recyclerView_time_slot = findViewById(R.id.recyclerView_time_slot);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);
        recyclerView_time_slot.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView_time_slot.getContext(),
                new LinearLayoutManager(getApplicationContext()).getOrientation());
        recyclerView_time_slot.addItemDecoration(dividerItemDecoration);


        //get All Time Slot availability
        dbFireStoreInstance = FirebaseFirestore.getInstance();
        dbFireStoreInstance.collection("bookings")
                .get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mybooking.this, "Error getting data", Toast.LENGTH_LONG).show();
                    }
                })

                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d(TAG, "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.

                            SharedPreferences prefs = getSharedPreferences("Aplikasi_Tempahan", MODE_PRIVATE);
                            String userEmail = prefs.getString("userEmail", "");
                            Log.d("User Email", userEmail);

                            List<TimeSlots> types = documentSnapshots.toObjects(TimeSlots.class);

                            for(TimeSlots timeSlot : types){
                                if(timeSlot.getUserEmail().equals(userEmail)){
                                    timeSlotsArrayList.add(timeSlot);
                                }
                            }
                            recyclerView_time_slot.setAdapter(new MyBookingAdapter(mybooking.this,
                                    timeSlotsArrayList));
                            Log.d(TAG, "Firebase_onSuccess: " + documentSnapshots);
                        }
                    }});

    }

    public void ClickMenu(View view){
        homepage.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        homepage.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        homepage.redirectActivity(this, homepage.class);
    }

    public void ClickDashboard(View view){
        recreate();
    }

    public void  ClickAboutUs(View view){
        homepage.redirectActivity(this, myaccount.class);
    }

    public void ClickLogOut(View view){
        homepage.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        homepage.closeDrawer(drawerLayout);
    }
}