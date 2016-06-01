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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        read_image();
        imageView = (ImageView) findViewById(R.id.imageView);
        //for now just draw the original image
        drawImage(button_id.ORIGINAL);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void read_image(){
        String im_name = "spectrum.jpg";

        File imgfile = new File(im_name);
        readImage = Imgcodecs.imread(imgfile.getAbsolutePath());
    }
    public void drawImage(button_id id){
        Mat displayImage = readImage;

        if(id == button_id.ORIGINAL){
            //do nothing for now. This is just a placeholder
        }
        else if (id == button_id.NEXT){
            //for now just display greyscale
            Imgproc.cvtColor(readImage,displayImage,Imgproc.COLOR_RGB2GRAY,4);
        }

        //Change it to bitmap
       // This code is copied from : http://stackoverflow.com/questions/13134682/convert-mat-to-bitmap-opencv-for-android
        Bitmap bmp = null;
        //Mat tmp = new Mat (height, width, CvType.CV_8U, new Scalar(4));
        try {
            //Imgproc.cvtColor(seedsImage, tmp, Imgproc.COLOR_RGB2BGRA);
            //Imgproc.cvtColor(seedsImage, tmp, Imgproc.COLOR_GRAY2RGBA, 4);
            bmp = Bitmap.createBitmap(readImage.cols(), readImage.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(readImage, bmp);
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
