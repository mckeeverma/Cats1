package com.example.marc.cats1;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.marc.cats1.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.id;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

@SuppressWarnings("deprecation")

//public class MainActivity extends Activity {
public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 99;
    public static final int MY_PERMISSIONS_SEND_SMS = 99;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 99;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 99;
    String TAG = "marc123: ";
    Boolean permission = false;
    private Camera mCamera;
    private CameraPreview mPreview;
    Button captureButton;
    static File imgFile = null;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate() event");
        checkSmsPermissions();
        boolean aaa = checkCameraHardware(this);
        mCamera = getCameraInstance();
        captureButton = (Button) findViewById(R.id.button_capture);
        //captureButton.setOnClickListener(
        //        new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                new Thread(new Runnable() {
        //                    public void run() {
        //                        Log.d(TAG, "0000 thread   button clickListener 1");
        //                        mCamera.takePicture(null, null, mPicture);
        //                        Log.d(TAG, "0000 thread  button clickListener 2");
        //                    }
        //                }).start();
        //            }
        //        }
        //);
        Log.d(TAG, "0000   main 001_____!!!");
        // Create our Preview view and set it as the content of our activity.
        CameraPreview mPreview = new CameraPreview(this, mCamera);
        Log.d(TAG, "0000   main 002");
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        Log.d(TAG, "0000   main 003");
        preview.addView(mPreview);
        Log.d(TAG, "0000   main 004");
        //captureButton.post(new Runnable() {
        //    @Override
        //    public void run() {
        //        Log.d(TAG, "0000___   button post 1");
        //        captureButton.performClick();
        //        Log.d(TAG, "0000___   button post 2");
        //        //captureButton.postDelayed(this, 60000);
        //    }
        //});
        Log.d(TAG, "0000   main 007 done");
    }

    public void captureThePicture(View view) {
        Log.d(TAG, "0000 thread  button clickListener 1");
        mCamera.takePicture(null, null, mPicture);
        Log.d(TAG, "0000 thread  button clickListener 2");
    }

    public boolean checkSmsPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
                }
                return false;
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_SEND_SMS);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_SEND_SMS);
                }
                return false;
            }
        }
        return true;
    }
    public boolean checkCameraPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }
                return false;
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
                        checkSmsPermissions();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.cat2)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private Camera.PictureCallback mPicture = new android.hardware.Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "picture taken");
            captureButton.setText("def");
            mCamera.stopPreview();
            mCamera.startPreview();
            //mCamera = getCameraInstance();

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                Log.d(TAG, "Error creating media file, check storage permissions: "); // + e.getMessage());
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

    // Check if this device has a camera
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            Log.d(TAG, "__________ device has a camera");
            return true;
        } else {
            Log.d(TAG, "__________ device does not have a camera");
            // no camera on this device
            return false;
        }
    }

    // A safe way to get an instance of the Camera object.
    public Camera getCameraInstance() {
        checkCameraPermissions();
        Camera c = null;
        try {
            c.release();
            Log.d(TAG, "__________ camera release okay (from getCameraInstance)");
        } catch (Exception e) {
            Log.d(TAG, "__________ camera release exception");
        }
        try {
            if (c != null) {
                Log.d(TAG, "__________ camera not null");
                c.release();
                Log.d(TAG, "__________ camera released");
            }
            c = Camera.open(); // attempt to get a Camera instance
            Log.d(TAG, "__________ camera open okay");
        } catch (Exception e) {
            Log.d(TAG, "__________ camera open exception");
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    public File getOutputMediaFile(int type) {
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (!isSDPresent) {
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, "card not mounted", duration);
            toast.show();

            Log.d("ERROR", "Card not mounted");
        }
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath() + "/cats006/");
        Log.d(TAG, "path directory is: " + Environment.getExternalStorageDirectory().getPath());

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {

                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
            imgFile = mediaFile;
        } else {
            return null;
        }

        return mediaFile;
    }

}
