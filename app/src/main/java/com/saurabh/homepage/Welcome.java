package com.saurabh.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class Welcome extends AppCompatActivity {
    VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        video = findViewById(R.id.video);
        Button detectDisease = findViewById(R.id.detectDisease);
        Button identifyPlants = findViewById(R.id.identifyPlants);


        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video_bg);
        video.setVideoURI(uri);
        video.start();

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        detectDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent disease = new Intent(Welcome.this, MainActivity.class);
                Welcome.this.startActivity(disease);
            }
        });


        identifyPlants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Welcome.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPostResume() {
        video.resume();
        super.onPostResume();
    }

    @Override
    protected void onRestart() {
        video.start();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        video.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        video.stopPlayback();
        super.onDestroy();
    }
}