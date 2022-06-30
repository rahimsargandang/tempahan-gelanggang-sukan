package com.example.aplikasitempahangelanggangsukan;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Space;
<<<<<<< HEAD
import android.widget.TextView;
=======
>>>>>>> origin/main
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasitempahangelanggangsukan.Adapter.TimeSlotAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {

    private static final String TAG = BookingActivity.class.getSimpleName();
    FirebaseFirestore dbFireStoreInstance;
    DrawerLayout drawerLayout;
    EditText sport_date_edt;
    final Calendar myCalendar= Calendar.getInstance();
    RecyclerView recyclerView_time_slot;
    ArrayList<TimeSlots> timeSlotsArrayList = new ArrayList<>();
    ArrayList<TimeSlots> parsedSlotsArrayList = new ArrayList<>();
    Button save_btn;
    public static TimeSlots dataModel;
<<<<<<< HEAD
    private Courts court;
    private TextView tv_rv_courtname,tv_rv_court_add;
=======
>>>>>>> origin/main

    private void addTimeSlots(){

        TimeSlots timeSlots1 = new TimeSlots();
        timeSlots1.setTimeSpan("10:00 - 12:00");
        timeSlots1.setAvailable("true");
        timeSlotsArrayList.add(timeSlots1);


        TimeSlots timeSlots2 = new TimeSlots();
        timeSlots2.setTimeSpan("12:00 - 02:00");
        timeSlots2.setAvailable("true");
        timeSlotsArrayList.add(timeSlots2);

        TimeSlots timeSlots3 = new TimeSlots();
        timeSlots3.setTimeSpan("02:00 - 04:00");
        timeSlots3.setAvailable("true");
        timeSlotsArrayList.add(timeSlots3);

        TimeSlots timeSlots4 = new TimeSlots();
        timeSlots4.setTimeSpan("04:00 - 06:00");
        timeSlots4.setAvailable("true");
        timeSlotsArrayList.add(timeSlots4);

        TimeSlots timeSlots5 = new TimeSlots();
        timeSlots5.setTimeSpan("06:00 - 08:00");
        timeSlots5.setAvailable("true");
        timeSlotsArrayList.add(timeSlots5);

        TimeSlots timeSlots6 = new TimeSlots();
        timeSlots6.setTimeSpan("08:00 - 10:00");
        timeSlots6.setAvailable("true");
        timeSlotsArrayList.add(timeSlots6);




    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        dataModel = new TimeSlots();

<<<<<<< HEAD
        tv_rv_courtname = findViewById(R.id.tv_rv_courtname);
        tv_rv_court_add = findViewById(R.id.tv_rv_court_add);

        court = (Courts) getIntent().getSerializableExtra("courtObj");
        if(court!=null) {

            tv_rv_courtname.setText(court.getCourtname());
            tv_rv_court_add.setText(court.getCoutaddress());
        }
=======
>>>>>>> origin/main

        drawerLayout = findViewById(R.id.drawer_layout);
        save_btn = findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sport_date_edt.getText().toString().isEmpty()){
                    Toast.makeText(BookingActivity.this,"Please select Date and Time to Procced",Toast.LENGTH_LONG).show();
                }else{
                    SharedPreferences prefs = getSharedPreferences("Aplikasi_Tempahan", MODE_PRIVATE);
                    String userEmail = prefs.getString("userEmail", "");
                    Log.d("User Email", userEmail);

                    dataModel.setUserEmail(userEmail);
                    Map<String, Object> bookingDetails = new HashMap<>();
                    bookingDetails.put("sportDate", dataModel.getSportDate());
                    bookingDetails.put("timeSpan", dataModel.getTimeSpan());
<<<<<<< HEAD
                    bookingDetails.put("coutaddress", court.getCoutaddress());
                    bookingDetails.put("courtname", court.getCourtname());
=======
>>>>>>> origin/main
                    bookingDetails.put("available", dataModel.getAvailable());
                    bookingDetails.put("userEmail", dataModel.getUserEmail());

                    dbFireStoreInstance = FirebaseFirestore.getInstance();
                    dbFireStoreInstance.collection("bookings")
                            .document()
                            .set(bookingDetails)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: ");
                                    Toast.makeText(BookingActivity.this, "Data saved successfully", Toast.LENGTH_LONG).show();
                                    dataModel = null;
                                    onBackPressed();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                            Toast.makeText(BookingActivity.this, "" + e, Toast.LENGTH_LONG).show();

                        }
                    });
                }



            }
        });


        sport_date_edt = findViewById(R.id.sport_date_edt);
        recyclerView_time_slot = findViewById(R.id.recyclerView_time_slot);

        recyclerView_time_slot.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(BookingActivity.this,3);
        recyclerView_time_slot.setLayoutManager(gridLayoutManager);
        recyclerView_time_slot.addItemDecoration(new GridSpacingItemDecoration(3,30,false));





        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                String myFormat="MM/dd/yy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                sport_date_edt.setText(dateFormat.format(myCalendar.getTime()));
                BookingActivity.dataModel.setSportDate(myCalendar.getTime());

                parsedSlotsArrayList.clear();
                timeSlotsArrayList.clear();
                addTimeSlots();


                //get All Time Slot availability
                dbFireStoreInstance = FirebaseFirestore.getInstance();
                dbFireStoreInstance.collection("bookings")
                        .get()
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(BookingActivity.this, "Error getting data", Toast.LENGTH_LONG).show();
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

                                    for(int i = 0;i<types.size();i++){
                                        if(types.get(i).getSportDate().getDate() == myCalendar.getTime().getDate() &&
                                                types.get(i).getSportDate().getMonth() == myCalendar.getTime().getMonth()){
<<<<<<< HEAD
                                            if(types.get(i).getCoutaddress()!=null && types.get(i).getCourtname()!=null) {
                                                if (types.get(i).getCourtname().equals(court.getCourtname()) &&
                                                        types.get(i).getCoutaddress().equals(court.getCoutaddress())) {
                                                    Log.d(TAG, "Date Matached");

                                                    TimeSlots timeSlots = new TimeSlots();
                                                    timeSlots.setTimeSpan(types.get(i).getTimeSpan());
                                                    timeSlots.setAvailable(types.get(i).getAvailable());
                                                    timeSlots.setSportDate(types.get(i).getSportDate());

                                                    parsedSlotsArrayList.add(timeSlots);
                                                }
                                            }
=======
                                            Log.d(TAG, "Date Matached");

                                            TimeSlots timeSlots = new TimeSlots();
                                            timeSlots.setTimeSpan(types.get(i).getTimeSpan());
                                            timeSlots.setAvailable(types.get(i).getAvailable());
                                            timeSlots.setSportDate(types.get(i).getSportDate());

                                            parsedSlotsArrayList.add(timeSlots);
>>>>>>> origin/main


                                        }
                                    }


                                    for(int j = 0;j<timeSlotsArrayList.size();j++){//10-12
                                        for(int k =0;k<parsedSlotsArrayList.size();k++)//4-6,8-10
                                            if(timeSlotsArrayList.get(j).getTimeSpan().equals(parsedSlotsArrayList.get(k).getTimeSpan())){
                                                timeSlotsArrayList.get(j).setAvailable(parsedSlotsArrayList.get(k).getAvailable());
                                            }
                                    }
                                    recyclerView_time_slot.setAdapter(new TimeSlotAdapter(BookingActivity.this,
                                            timeSlotsArrayList));
                                    recyclerView_time_slot.getAdapter().notifyDataSetChanged();
                                    Log.d(TAG, "Firebase_onSuccess: " + documentSnapshots);
                                }
                            }});
            }
        };


        sport_date_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(BookingActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });





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
