package com.saurabh.homepage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;


public class CamActive extends AppCompatActivity {

    private ImageView camPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_active);


        camPic = findViewById(R.id.camPic);
        Button fab = (Button) findViewById(R.id.fab);

        fab.setOnClickListener(v -> chooseProfilePicture());


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.menu:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

//                    case R.id.you:
//                        startActivity(new Intent(getApplicationContext(),Authentication.class));
//                        overridePendingTransition(0,0);
//                        return true;
                }
                return false;
            }
        });
   }

        private void chooseProfilePicture () {
            AlertDialog.Builder builder = new AlertDialog.Builder(CamActive.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.alert_dialog_profile_picture, null);
            builder.setCancelable(false);
            builder.setView(dialogView);

            ImageView imageViewADPPCamera = dialogView.findViewById(R.id.imageViewADPPCamera);
            ImageView imageViewADPPGallery = dialogView.findViewById(R.id.imageViewADPPGallery);

            final AlertDialog alertDialogProfilePicture = builder.create();
            alertDialogProfilePicture.show();

            imageViewADPPCamera.setOnClickListener(view -> {
                if (checkAndRequestPermissions()) {
                    takePictureFromCamera();
                    alertDialogProfilePicture.dismiss();
                }
            });
//
            imageViewADPPGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    takePictureFromGallery();
                    alertDialogProfilePicture.dismiss();
                }
            });
        }

        private void takePictureFromGallery () {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);
        }

        @SuppressLint("QueryPermissionsNeeded")
        private void takePictureFromCamera () {
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePicture.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePicture, 2);
            }
        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case 1:
                    if (resultCode == RESULT_OK) {
                        Uri selectedImageUri = Objects.requireNonNull(data).getData();
                        camPic.setImageURI(selectedImageUri);
                    }
                    break;
                case 2:
                    if (resultCode == RESULT_OK) {
                        Bundle bundle = Objects.requireNonNull(data).getExtras();
                        Bitmap bitmapImage = (Bitmap) bundle.get("data");
                        camPic.setImageBitmap(bitmapImage);
                    }
                    break;
            }
        }

        private boolean checkAndRequestPermissions () {
            if (Build.VERSION.SDK_INT >= 23) {
                int cameraPermission = ActivityCompat.checkSelfPermission(CamActive.this, Manifest.permission.CAMERA);
                if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(CamActive.this, new String[]{Manifest.permission.CAMERA}, 20);
                    return false;
                }
            }
            return true;
        }

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePictureFromCamera();
            } else
                Toast.makeText(CamActive.this, "Permission not Granted", Toast.LENGTH_SHORT).show();
        }
    }