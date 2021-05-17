package com.alejandromartinezremis.airquailitygijon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.alejandromartinezremis.airquailitygijon.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SwitchCompat switchCompat = findViewById(R.id.switchNotification);
        handleSwitchLogic(switchCompat.isChecked());
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleSwitchLogic(isChecked);
            }
        });
    }

    private void handleSwitchLogic(boolean isChecked){
        LinearLayout layout = findViewById(R.id.layoutNotificationSettings);
        if(isChecked)
            layout.setVisibility(View.VISIBLE);
        else
            layout.setVisibility(View.GONE);

    }
}