package com.matteolobello.palazzovenezia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.matteolobello.palazzovenezia.R;
import com.matteolobello.palazzovenezia.data.asset.AssetPaintingsGenerator;
import com.matteolobello.palazzovenezia.data.preference.PreferenceHandler;

public class LaunchScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        Class classToLaunch = HomeActivity.class;

        PreferenceHandler preferenceHandler = PreferenceHandler.get();

        String booleanStringValue = preferenceHandler.getValue(this, PreferenceHandler.LAUNCH_INTRODUCTION_KEY);
        if (booleanStringValue == null) {
            preferenceHandler.setValue(this, PreferenceHandler.LAUNCH_INTRODUCTION_KEY, "false");

            classToLaunch = IntroductionActivity.class;
        }

        Intent intent = new Intent(this, classToLaunch);
        if (classToLaunch.getName().equals(HomeActivity.class.getName())) {
            intent.putParcelableArrayListExtra("all_paintings",
                    AssetPaintingsGenerator.generatePaintings(getApplicationContext()));
        }

        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
}