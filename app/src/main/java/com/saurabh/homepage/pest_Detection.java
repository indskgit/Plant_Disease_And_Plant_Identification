package com.saurabh.homepage;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.saurabh.homepage.ml.DiseaseDetection;
import com.saurabh.homepage.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class pest_Detection extends AppCompatActivity {
    TextView result, demoTxt, classified, clickHere;
    ImageView imageView;
    Button picture;
    int imageSize = 224;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_detection);


        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);
        picture = findViewById(R.id.button);

        demoTxt = findViewById(R.id.demoText);
        classified = findViewById(R.id.classified);
        clickHere = findViewById(R.id.click_here);

        demoTxt.setVisibility(View.VISIBLE);
        clickHere.setVisibility(View.GONE);
        classified.setVisibility(View.GONE);
        result.setVisibility(View.GONE);


        picture.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,1);
                }
                else{
                    //request camera permission
                    requestPermissions(new String[]{Manifest.permission.CAMERA},100);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(),image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image,dimension,dimension);


            imageView.setImageBitmap(image);

            demoTxt.setVisibility(View.GONE);
            clickHere.setVisibility(View.VISIBLE);
            classified.setVisibility(View.VISIBLE);
            result.setVisibility(View.VISIBLE);

            image = Bitmap.createScaledBitmap(image,imageSize,imageSize,false);
            classifyImage(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void classifyImage(Bitmap image) {
        try {
            Model model = Model.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224,224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            //get 1d array of 224*224 pixels in image
            int[] intValue = new int[imageSize*imageSize];
            image.getPixels(intValue,0,image.getWidth() ,0,0,image.getWidth(),image.getHeight());

            // Iterate Over pixel and extract R,G,B value , add to bytebuffer
            int pixel = 0;
            for (int i = 0; i<imageSize;i++){
                for (int j=0;j<imageSize;j++){
                    int val = intValue[pixel++];//RGB
                    byteBuffer.putFloat(((val >> 16)& 0xFF)*(1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8)& 0xFF)*(1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF)*(1.f / 255.f));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);
            //run model interface
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidence = outputFeature0.getFloatArray();
            //find index of class with biggest confidence
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0;i<confidence.length;i++){
                if (confidence[i] > maxConfidence){
                    maxConfidence = confidence[i];
                    maxPos = i;
                }
            }
            String[] classes = {"rice leaf roller","rice leaf caterpillar","paddy stem maggot","asiatic rice borer","yellow rice borer","rice gall midge","Rice Stemfly","brown plant hopper","white backed plant hopper","small brown plant hopper"};
            result.setText(classes[maxPos]);
            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //to search disease on internet
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q="+result.getText())));
                }
            });

            model.close();
        }catch (IOException e){
            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
        }
    }
}