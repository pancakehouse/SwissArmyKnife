package com.techtideapps.swissarmyknife;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Flashlight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);
    }

    public boolean isFlashOn = false;
    public Camera camera = Camera.open();

    public void flashlight(View view) {
        if(isFlashOn == false){
            camera.startPreview();
            Camera.Parameters p = camera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
        }
        if(isFlashOn == true){
            camera.stopPreview();
            camera.release();
        }
    }

}
