package com.example.anand.opencv_staticlink_experiments;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.*;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    //Load the static library
    static{ System.loadLibrary("opencv_java3"); }

    private org.opencv.android.Utils Utils;

    protected enum button_id {ORIGINAL, NEXT};
    public Mat readImage;
    public ImageView imageView;
    private boolean flag;                       //True --> Grayscale; False--> original


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get handle to the UI components
        imageView = (ImageView) findViewById(R.id.imageView);

        // Read the image and display a placeholder
        read_image();
        flag = false;
        //for now just draw the original image
        drawImage(button_id.ORIGINAL);
    }

    public void read_image(){
        try {
            // Source : http://stackoverflow.com/questions/20184215/how-to-get-mat-with-a-drawable-input-in-android-using-opencv
            readImage = Utils.loadResource(this.getApplicationContext(), R.drawable.spectrum, Imgcodecs.CV_LOAD_IMAGE_COLOR);
        } catch( Exception e){
            e.printStackTrace();
        }
    }
    public void drawImage(button_id id){
        read_image();
        Mat displayImage = readImage;

        if(id == button_id.ORIGINAL){
            //Show the original image
            displayImage = readImage;
            flag = false;
        }
        else if (id == button_id.NEXT){
            //for now just display grayscale or toggle
            if(!flag) {
                Imgproc.cvtColor(readImage, displayImage, Imgproc.COLOR_RGB2GRAY, 4);
                flag = true;
            }
            else {
                displayImage = readImage;
                flag = false;
            }
        }

        //Change it to bitmap
       // This code is copied from : http://stackoverflow.com/questions/13134682/convert-mat-to-bitmap-opencv-for-android
        Bitmap bmp = null;
        //Mat tmp = new Mat (height, width, CvType.CV_8U, new Scalar(4));
        try {
            bmp = Bitmap.createBitmap(displayImage.cols(), displayImage.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(displayImage, bmp);
            //Made some changes
        }
        catch (CvException e){
            Log.d("Exception", e.getMessage());
        }
       //End copied code

        imageView.setImageBitmap(bmp);
    }

    public void button_original_clicked(View view){
        drawImage(button_id.ORIGINAL);
    }

    public void button_next_clicked(View view){
        drawImage(button_id.NEXT);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
