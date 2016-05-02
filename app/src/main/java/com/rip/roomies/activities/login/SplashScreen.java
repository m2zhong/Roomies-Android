package com.rip.roomies.activities.login;

import android.content.Intent;
import android.os.Bundle;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;

public class SplashScreen extends GenericActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // No timer -- temporary bypass
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
