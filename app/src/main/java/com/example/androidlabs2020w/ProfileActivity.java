package com.example.androidlabs2020w;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ProfileActivity = "PROFILE_ACTIVITY";

    ImageButton mImageButton = null;

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        mImageButton = findViewById(R.id.imageButton4);
        mImageButton.setOnClickListener(v -> dispatchTakePictureIntent()   );

        EditText emailEditText = findViewById(R.id.editText6);
        Intent fromMain = getIntent();
        String email = fromMain.getStringExtra("EMAIL");
        emailEditText.setText(email);


        Log.e(ProfileActivity, "In function: onCreate()");
//You will have to create a static String variable called ACTIVITY_NAME, and set it equal to the Activity’s name. In this case:

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }

        Log.e(ProfileActivity, "In function: onActivityResult()");

//You will have to create a static String variable called ACTIVITY_NAME, and set it equal to the Activity’s name. In this case:
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ProfileActivity, "In function: onPause()");
//You will have to create a static String variable called ACTIVITY_NAME, and set it equal to the Activity’s name. In this case:

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ProfileActivity, "In function: onStart()");
//You will have to create a static String variable called ACTIVITY_NAME, and set it equal to the Activity’s name. In this case:
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ProfileActivity, "In function: onDestroy()");
//You will have to create a static String variable called ACTIVITY_NAME, and set it equal to the Activity’s name. In this case:
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ProfileActivity, "In function: onResume()");
//You will have to create a static String variable called ACTIVITY_NAME, and set it equal to the Activity’s name. In this case:
    }


}
