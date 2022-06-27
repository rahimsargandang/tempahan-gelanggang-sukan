package com.example.aplikasitempahangelanggangsukan;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

public class mybooking extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);
        drawerLayout = findViewById(R.id.drawer_layout);
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