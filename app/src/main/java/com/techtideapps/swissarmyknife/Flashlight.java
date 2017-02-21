package com.techtideapps.swissarmyknife;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;

import java.security.Policy;

public class Flashlight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);
    }

    public boolean isFlashOn = false;
    public boolean isStrobeOn = false;
    public Camera camera;

    public void flashlight(View view) {
        if(isFlashOn == false){
            camera = Camera.open();
            camera.startPreview();
            Camera.Parameters p = camera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
        }
        if(isFlashOn == true){
            camera.stopPreview();
            camera.release();
        }

        if(isFlashOn == false){
            isFlashOn = true;
        } else{
            isFlashOn = false;
        }
    }
    StrobeThread thread;
    public void strobe(View view) throws InterruptedException {
        SeekBar bar = (SeekBar) findViewById(R.id.seekBar2);
        if(isFlashOn == true){
            camera.stopPreview();
            camera.release();
        }
        isFlashOn = false;
        //Should be in a new thread
        if(isStrobeOn == false){
            thread = new StrobeThread(bar, false);
            thread.start();
            isStrobeOn = true;
        } else{
            thread.end = true;
            isStrobeOn = false;
        }
    }
}

class StrobeThread extends Thread{
    SeekBar bar;
    boolean end = false;
    StrobeThread(SeekBar bar2, boolean end2){
        bar = bar2;
        end = end2;
    }
    public void run(){
        Camera camera;
        camera = Camera.open();
        camera.startPreview();
        Camera.Parameters parameters = camera.getParameters();
        while(!end){
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            try {
                Thread.sleep(1000 / (bar.getProgress() + 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            try {
                Thread.sleep(1000 / (bar.getProgress() + 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        camera.stopPreview();
        camera.release();
    }
}