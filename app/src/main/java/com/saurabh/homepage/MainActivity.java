package com.saurabh.homepage;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.*;
import android.view.MenuItem;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId())
                    {
                        case R.id.menu:
                            startActivity(new Intent(getApplicationContext(), menupage.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.home:
                            return true;


                    case R.id.you:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                    return false;
                }
            });


        ImageView treeLogo = findViewById(R.id.treeLogo);
        TextView plant_Disease = findViewById(R.id.plant_Disease);
        TextView pest_Detection = findViewById(R.id.pest_Detection);


        treeLogo.setOnClickListener(v -> Toast.makeText(MainActivity.this, "I am your Friend", Toast.LENGTH_SHORT).show());

        plant_Disease.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CamActive.class);
            startActivity(i);
        });


        pest_Detection.setOnClickListener(v -> {
            Intent pest = new Intent(MainActivity.this, CamActive.class);
            startActivity(pest);
        });

    }


}