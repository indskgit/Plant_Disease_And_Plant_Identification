package com.saurabh.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;
import java.util.Objects;

public class Authentication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);


        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        TextView welcome = findViewById(R.id.welcome);

        //To Change Bg Accordingly Using Calender Method

        Calendar cal = Calendar.getInstance();

        int time = cal.get(Calendar.HOUR_OF_DAY);

        if (time > 0 && time <12){
            //constraintLayout.setBackground(getDrawable(R.drawable.background name)) predefined
            constraintLayout.setBackground(getDrawable(R.drawable.good_morning_img));
            welcome.setText("Good Morning\n Welcome To My Culture");
        }
        else if (time >=12 && time <16){
            welcome.setText("Have A Good Day\n Welcome To My Culture");
            //
        }
        else if (time>=16 && time<21){
            welcome.setText("Good Evening\n Welcome To My Culture");
            //
        }
        else if (time>=21 && time <24){
            constraintLayout.setBackground(getDrawable(R.drawable.good_night_img));
            welcome.setText("Good Evening\n Welcome To My Culture");
        }

//        bottomNavigationView.findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setSelectedItemId(R.id.you);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()){
//
//                    case R.id.menu:
//                        startActivity(new Intent(getApplicationContext(),CamActive.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.you:
//                        return true;
//                }
//                return false;
//            }
//        });

//        bottomNavigationView.findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setSelectedItemId(R.id.you);
//
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                switch (item.getItemId()){
//
//                    case R.id.menu:
//                        startActivity(new Intent(getApplicationContext(),CamActive.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.you:
//                        return true;
//                }
//                return false;
//            }
//        });
    }
}