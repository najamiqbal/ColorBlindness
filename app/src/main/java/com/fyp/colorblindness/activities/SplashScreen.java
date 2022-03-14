package com.fyp.colorblindness.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fyp.colorblindness.models.UserModelClass;
import com.fyp.colorblindness.genralclasses.SharedPreferenceClass;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Handle the splash screen transition.
        UserModelClass userModelClass = SharedPreferenceClass.getInstance(this).getUser();
        if (userModelClass != null) {
            switch (userModelClass.getUser_type()) {
                case "1": {
                    Intent intent = new Intent(this, doctorMainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case "2": {
                    Intent intent=new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
            }

        } else {
            Intent intent = new Intent(this, LoginSignUpActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
