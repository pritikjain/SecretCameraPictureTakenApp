package com.pretty.strawberry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;



import android.widget.SeekBar;
import android.widget.Toast;


import com.pretty.strawberry.Model.ServerResponse;
import com.pretty.strawberry.Service.ApiConfig;
import com.pretty.strawberry.utils.AppConfig;

import java.io.File;
import java.util.List;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.FotoapparatSwitcher;

import io.fotoapparat.facedetector.Rectangle;
import io.fotoapparat.facedetector.processor.FaceDetectorProcessor;
import io.fotoapparat.facedetector.view.RectanglesView;
//import io.fotoapparat.parameter.ScaleType;
//import io.fotoapparat.preview.Frame;
//import io.fotoapparat.preview.FrameProcessor;
//import io.fotoapparat.result.BitmapPhoto;
import io.fotoapparat.result.PendingResult;
import io.fotoapparat.result.PhotoResult;
//import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.view.CameraView;
import io.fotoapparat.parameter.LensPosition;





import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import io.fotoapparat.characteristic.LensPosition;


//import static io.fotoapparat.log.Loggers.fileLogger;
//import static io.fotoapparat.log.Loggers.logcat;
//import static io.fotoapparat.log.Loggers.loggers;
import static io.fotoapparat.parameter.selector.LensPositionSelectors.lensPosition;

public class CameraScreen extends AppCompatActivity {

    private static final String LOGGING_TAG = "Take Photo Class";
    private CameraView cameraView;
    private RectanglesView rectanglesView;
//    private FocusView focusView;
    private Fotoapparat fotoapparat;
    private Fotoapparat frontFotoapparat;
    private FotoapparatSwitcher fotoapparatSwitcher;
    //SelectorFunction<FocusMode> focusModeSelector; // Lets just hide the focus right now : at March 26
    private View capture;

    boolean activeCameraBack = false;

    private File rootDir;

//    private CameraConfiguration cameraConfiguration = CameraConfiguration
//            .builder()
//            .photoResolution(standardRatio(
//                    highestResolution()
//            ))
//            .focusMode(firstAvailable(
//                    continuousFocusPicture(),
//                    autoFocus(),
//                    fixed()
//            ))
//            .flash(firstAvailable(
//                    autoRedEye(),
//                    autoFlash(),
//                    torch(),
//                    off()
//            ))
//            .previewFpsRange(highestFps())
//            .sensorSensitivity(highestSensorSensitivity())
//            //.frameProcessor(new SampleFrameProcessor())
//            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_screen);

        cameraView = findViewById(R.id.cameraView);
        rectanglesView = findViewById(R.id.rectanglesView);

        capture = findViewById(R.id.capture);


        frontFotoapparat = createFotoapparat(LensPosition.FRONT);
        fotoapparatSwitcher = FotoapparatSwitcher.withDefault(frontFotoapparat);

        takePictureOnClick();


        cameraView.setVisibility(View.VISIBLE);


    }
    private Fotoapparat
    createFotoapparat(LensPosition position) {
        return Fotoapparat
                .with(this)
                .into(cameraView)
//                .focusMode(focusModeSelector) //// Lets just hide the focus right now : at March 26
                .lensPosition(lensPosition(position))
                .frameProcessor(
                        FaceDetectorProcessor.with(this)
                                .listener(new FaceDetectorProcessor.OnFacesDetectedListener() {
                                    @Override
                                    public void onFacesDetected(List<Rectangle> faces) {
                                        Log.d("&&&", "Detected faces: " + faces.size());

                                        rectanglesView.setRectangles(faces);
                                    }
                                })
                                .build()
                )

                .build();
    }



    private void zoomSeekBar()
    {
        SeekBar seekBar = findViewById(R.id.zoomSeekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                //frontFotoapparat(progress / (float) seekBar.getMax());
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
    }

//    private void switchCameraOnClick() {
//        View switchCameraButton = findViewById(R.id.switchCamera);
//        boolean hasFrontCamera = fotoapparat.isAvailable(front());
//        switchCameraButton.setVisibility(
//                hasFrontCamera ? View.VISIBLE : View.GONE
//        );
//
//
//
//        switchCameraButton.setVisibility(
//                hasFrontCamera ? View.VISIBLE : View.GONE
//        );
//
//        if (hasFrontCamera) {
//            switchCameraOnClick(switchCameraButton);
//        }
//
//
//    }
//    private void switchCameraOnClick(View view) {
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activeCameraBack = !activeCameraBack;
//                fotoapparat.switchTo(
//                        activeCameraBack ? back() : front(),
//                        cameraConfiguration
//                );
//            }
//        });
//    }

    private void toggleTorchOnSwitch() {
//        SwitchCompat torchSwitch = findViewById(R.id.torchSwitch);
//
//        torchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                fotoapparat.updateConfiguration(
//                        UpdateConfiguration.builder()
//                                .flash(
//                                        isChecked ? torch() : off()
//                                )
//                                .build()
//                );
//            }
//        });
    }

    private void takePictureOnClick() {
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    public  File getRootDir()
    {
        if (rootDir == null) {
            rootDir = getApplicationContext() != null ? getApplicationContext().getFilesDir() : null;
        }
        //retrun (context.getFilesDir());
        return rootDir;
    }

    private void takePicture() {
        PhotoResult photoResult = frontFotoapparat.takePicture();

//        photoResult.saveToFile(new File(
//                getExternalFilesDir("photos"),
//                "photo.jpg"
        File imageFile = new File(getRootDir(),
                "original_photo.jpg");
        photoResult.saveToFile(imageFile).whenDone(aVoid -> {
            // Here right away upload the file to server
            uploadFile(imageFile.getAbsolutePath());
            navigateToFirstScreen();
        });



        // Here right away upload the file to server



//        photoResult
//                //.toBitmap(scaled(0.25f))
//                .toBitmap()
//                .whenDone(new WhenDoneListener<BitmapPhoto>() {
//                    @Override
//                    public void whenDone(@Nullable BitmapPhoto bitmapPhoto) {
//                        if (bitmapPhoto == null) {
//                            Log.e(LOGGING_TAG, "Couldn't capture photo.");
//                            return;
//                        }
//                        ImageView imageView = findViewById(R.id.result);
//
//                        imageView.setImageBitmap(bitmapPhoto.bitmap);
//                        imageView.setRotation(-bitmapPhoto.rotationDegrees);
//                    }
//                });

    }

    public void navigateToFirstScreen() {
        Intent intent = new Intent(this, FirstScreen.class);
        
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fotoapparatSwitcher.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        fotoapparatSwitcher.stop();

    }



//    private class SampleFrameProcessor implements FrameProcessor {
//        @Override
//        public void process(@NotNull Frame frame) {
//            // Perform frame processing, if needed
//        }
//    }


    // Uploading Image/Video
    private void uploadFile(String mediaPath) {
        //progressDialog.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        //Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                   // assert serverResponse != null;
                    //Log.v("Response", serverResponse.toString());
                }
                //progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d("ABC",  t.getMessage());
            }
        });
    }


}

