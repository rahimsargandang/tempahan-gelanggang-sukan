package com.example.aplikasitempahangelanggangsukan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasitempahangelanggangsukan.Adapter.TimeSlotAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {

    //app cus_LzULmqcyTmnbEX
    //postman cus_LzSxlXXqfbL6hr

    private String SECRET_KEY = "sk_test_51LGdGwEyhfniARGcZ7BgMgnsQDsrw14wR1RGKjx4eoFxXFUbfBsP4yppdQ45Lb9GiIykOUkTxfD2XXWAj62cmKux00Q0L2Wyas";
    private String STRIPE_PUBLISH_KEY = "pk_test_51LGdGwEyhfniARGciJaDSbaZw2zGr1L1v85APNAS74KnDs6QsALC6nBDGIgf0O46aKeSBcYNgVsOYnBcEvSLoMn300jltPLpnH";
    PaymentSheet paymentSheet;
    String customerID;
    String ephemeralKey;
    String clientSecret;

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
    private Courts court;
    private TextView tv_rv_courtname,tv_rv_court_add;

    private void addTimeSlots(){

        TimeSlots timeSlots1 = new TimeSlots();
        timeSlots1.setTimeSlot("10:00 - 12:00");
        timeSlots1.setAvailable("true");
        timeSlotsArrayList.add(timeSlots1);


        TimeSlots timeSlots2 = new TimeSlots();
        timeSlots2.setTimeSlot("12:00 - 02:00");
        timeSlots2.setAvailable("true");
        timeSlotsArrayList.add(timeSlots2);

        TimeSlots timeSlots3 = new TimeSlots();
        timeSlots3.setTimeSlot("02:00 - 04:00");
        timeSlots3.setAvailable("true");
        timeSlotsArrayList.add(timeSlots3);

        TimeSlots timeSlots4 = new TimeSlots();
        timeSlots4.setTimeSlot("04:00 - 06:00");
        timeSlots4.setAvailable("true");
        timeSlotsArrayList.add(timeSlots4);

        TimeSlots timeSlots5 = new TimeSlots();
        timeSlots5.setTimeSlot("06:00 - 08:00");
        timeSlots5.setAvailable("true");
        timeSlotsArrayList.add(timeSlots5);

        TimeSlots timeSlots6 = new TimeSlots();
        timeSlots6.setTimeSlot("08:00 - 10:00");
        timeSlots6.setAvailable("true");
        timeSlotsArrayList.add(timeSlots6);




    }


    private void paymentFlow(){

        paymentSheet.presentWithPaymentIntent(clientSecret,
                new PaymentSheet.Configuration("Aplikasi Tempahan Gelanggang Sukan",
                        new PaymentSheet.CustomerConfiguration(
                                customerID,ephemeralKey
                        ) ));
    }

    private void getClientSecret(String customerID, String ephemeralKey) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            clientSecret = object.getString("client_secret");

                            Toast.makeText(BookingActivity.this,
                                    "Client Secret" + clientSecret, Toast.LENGTH_SHORT).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookingActivity.this,
                        "ClientSecret Error " + error.getMessage(), Toast.LENGTH_SHORT).show();

                if(error.getMessage()==null)
                    getClientSecret(customerID,ephemeralKey);

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer",customerID);
                params.put("amount","50" + "00");
                params.put("currency","MYR");
                params.put("automatic_payment_methods[enabled]","true");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(BookingActivity.this);
        requestQueue.add(stringRequest);


    }


    private void getEphemeralKey(String customerID) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            ephemeralKey = object.getString("id");

                            Toast.makeText(BookingActivity.this,
                                    "EphemeralKey" + ephemeralKey, Toast.LENGTH_SHORT).show();


                            getClientSecret(customerID,ephemeralKey);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookingActivity.this,
                        "EphemeralKey Error " + error.getMessage(), Toast.LENGTH_SHORT).show();

                if(error.getMessage()==null)
                    getEphemeralKey(customerID);

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                header.put("Stripe-Version","2020-08-27");
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer",customerID);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(BookingActivity.this);
        requestQueue.add(stringRequest);


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        dataModel = new TimeSlots();

        PaymentConfiguration.init(BookingActivity.this,STRIPE_PUBLISH_KEY);
        paymentSheet = new PaymentSheet(BookingActivity.this,paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    customerID = object.getString("id");

                    Toast.makeText(BookingActivity.this,
                            "Customer ID " + customerID, Toast.LENGTH_SHORT).show();

                    getEphemeralKey(customerID);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(BookingActivity.this);
        requestQueue.add(stringRequest);





        tv_rv_courtname = findViewById(R.id.tv_rv_courtname);
        tv_rv_court_add = findViewById(R.id.tv_rv_court_add);

        court = (Courts) getIntent().getSerializableExtra("courtObj");
        if(court!=null) {

            tv_rv_courtname.setText(court.getCourtname());
            tv_rv_court_add.setText(court.getCourtaddress());
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        save_btn = findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                builder.setTitle("Booking Confirmation");
                builder.setMessage("Are you sure you want to book this date?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        if(sport_date_edt.getText().toString().isEmpty()){
                            Toast.makeText(BookingActivity.this,"Please select Date and Time to Procced",Toast.LENGTH_LONG).show();
                        }else{

                            paymentFlow();


                        }

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
                BookingActivity.dataModel.setBookingDate(myCalendar.getTime());

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
                                    recyclerView_time_slot.setAdapter(new TimeSlotAdapter(BookingActivity.this,
                                            timeSlotsArrayList));
                                    recyclerView_time_slot.getAdapter().notifyDataSetChanged();
                                    return;
                                } else {
                                    // Convert the whole Query Snapshot to a list
                                    // of objects directly! No need to fetch each
                                    // document.
                                    List<TimeSlots> types = documentSnapshots.toObjects(TimeSlots.class);

                                    for(int i = 0;i<types.size();i++){
                                        if(types.get(i).getBookingDate().getDate() == myCalendar.getTime().getDate() &&
                                                types.get(i).getBookingDate().getMonth() == myCalendar.getTime().getMonth()){
                                            if(types.get(i).getCourtaddress()!=null && types.get(i).getCourtname()!=null) {
                                                if (types.get(i).getCourtname().equals(court.getCourtname()) &&
                                                        types.get(i).getCourtaddress().equals(court.getCourtaddress())) {
                                                    Log.d(TAG, "Date Matached");

                                                    TimeSlots timeSlots = new TimeSlots();
                                                    timeSlots.setTimeSlot(types.get(i).getTimeSlot());
                                                    timeSlots.setAvailable(types.get(i).getAvailable());
                                                    timeSlots.setBookingDate(types.get(i).getBookingDate());

                                                    parsedSlotsArrayList.add(timeSlots);
                                                }
                                            }


                                        }
                                    }


                                    for(int j = 0;j<timeSlotsArrayList.size();j++){//10-12
                                        for(int k =0;k<parsedSlotsArrayList.size();k++)//4-6,8-10
                                            if(timeSlotsArrayList.get(j).getTimeSlot().equals(parsedSlotsArrayList.get(k).getTimeSlot())){
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

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {

        if(paymentSheetResult instanceof PaymentSheetResult.Completed){
            Toast.makeText(this, "Transaction was successful", Toast.LENGTH_SHORT).show();

            SharedPreferences prefs = getSharedPreferences("Aplikasi_Tempahan", MODE_PRIVATE);
            String userEmail = prefs.getString("userEmail", "");
            Log.d("User Email", userEmail);

            dataModel.setUserEmail(userEmail);
            Map<String, Object> bookingDetails = new HashMap<>();
            bookingDetails.put("bookingDate", dataModel.getBookingDate());
            bookingDetails.put("timeSlot", dataModel.getTimeSlot());
            bookingDetails.put("courtaddress", court.getCourtaddress());
            bookingDetails.put("courtname", court.getCourtname());
            bookingDetails.put("available", dataModel.getAvailable());
            bookingDetails.put("userEmail", dataModel.getUserEmail());
            bookingDetails.put("status", "paid");

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


        }else if(paymentSheetResult instanceof PaymentSheetResult.Canceled){
            Toast.makeText(this, "Transaction was cancelled", Toast.LENGTH_SHORT).show();
        }else if(paymentSheetResult instanceof PaymentSheetResult.Failed){
            Toast.makeText(this, "Transaction was Failed", Toast.LENGTH_SHORT).show();

        }

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
