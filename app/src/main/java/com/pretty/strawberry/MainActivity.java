package com.pretty.strawberry;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ID_FOR_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermissions();
    }

    public void navigateToCemeraScreen(View view)
    {
        Intent intent = new Intent(this,CameraScreen.class);
        startActivity(intent);

    }

    private boolean checkAndRequestPermissions()
    {
        int CameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if(CameraPermission != PackageManager.PERMISSION_GRANTED)
        {
           if( ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)) {

               // Show an explantion to the user * asynchronously* --- don't block this thread
               // waiting for the user's response! After the user
               // sees the explanation , try again to request the permission.

            }
            else {
                // No Explanation needed; request the permission
               ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, REQUEST_ID_FOR_PERMISSIONS );
               return true;
           }
        }
        return false;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case REQUEST_ID_FOR_PERMISSIONS : {

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // Permission was granted, yay! Do the task

                } else {
                    // Permission denied,
                }
                return ;
            }

        }
    }
}
