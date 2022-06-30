package com.example.aplikasitempahangelanggangsukan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplikasitempahangelanggangsukan.Adapter.MyBookingAdapter;
import com.example.aplikasitempahangelanggangsukan.Adapter.OwnerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class courthomepage extends AppCompatActivity {

    private static final String TAG = courthomepage.class.getSimpleName();
    ArrayList<TimeSlots> timeSlotsArrayList = new ArrayList<>();
    RecyclerView recyclerView_bookings;
    FirebaseFirestore dbFireStoreInstance;
    private String courtName = "";
    Button logout_btn;
    static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courthomepage);


        preferences = getSharedPreferences("Aplikasi_Tempahan", MODE_PRIVATE);

        recyclerView_bookings = findViewById(R.id.recyclerView_bookings);
        logout_btn = findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences.edit().remove("userEmail").commit();
                courthomepage.this.finishAffinity();
                System.exit(0);
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);
        recyclerView_bookings.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView_bookings.getContext(),
                new LinearLayoutManager(getApplicationContext()).getOrientation());
        recyclerView_bookings.addItemDecoration(dividerItemDecoration);

        dbFireStoreInstance = FirebaseFirestore.getInstance();
        dbFireStoreInstance.collection("Courts")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        SharedPreferences prefs = getSharedPreferences("Aplikasi_Tempahan", MODE_PRIVATE);
                        String userEmail = prefs.getString("userEmail", "");
                        Log.d("User Email", userEmail);

                        for(int i = 0;i<queryDocumentSnapshots.size();i++){
                            if(queryDocumentSnapshots.getDocuments().get(i).get("email").equals(userEmail)){
                                courtName = queryDocumentSnapshots.getDocuments().get(i).get("courtname") + "";
                                break;
                            }
                        }

                        dbFireStoreInstance.collection("bookings")
                                .get()
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(courthomepage.this, "Error getting data", Toast.LENGTH_LONG).show();
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



                                            List<TimeSlots> types = documentSnapshots.toObjects(TimeSlots.class);

                                            for(TimeSlots timeSlot : types){
                                                if(timeSlot.getCourtname().equals(courtName)){
                                                    timeSlotsArrayList.add(timeSlot);
                                                }
                                            }
                                            recyclerView_bookings.setAdapter(new OwnerAdapter(courthomepage.this,
                                                    timeSlotsArrayList));
                                            Log.d(TAG, "Firebase_onSuccess: " + documentSnapshots);
                                        }
                                    }});

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(courthomepage.this, "Error getting data", Toast.LENGTH_LONG).show();
                    }
                });



    }
}