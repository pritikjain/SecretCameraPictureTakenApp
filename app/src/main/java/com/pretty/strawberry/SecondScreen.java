package com.pretty.strawberry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondScreen  extends BasePictureTakingScreen
{

    @BindView(R.id.start_second_button)
    Button secondButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

//        secondButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onTakePicture();
//            }
//        });

    }

    @Override
    public void onImageCapture(@NonNull File imageFile)
    {
        super.onImageCapture(imageFile);

        // Here right away upload the file to server
        uploadFile(imageFile.getAbsolutePath());

        // Move to next screen
        Intent intent = new Intent(this,SecondScreen.class);
        startActivity(intent);
    }

    public void navigateToNextScreen(View view)
    {
        onTakePicture();
    }
}
