package com.alejandromartinezremis.airquailitygijon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alejandromartinezremis.airquailitygijon.R;
import com.alejandromartinezremis.airquailitygijon.service.NotificationJobService;
import com.alejandromartinezremis.airquailitygijon.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private final static String LOG_TAG = "SettingsActivity";

    private SharedPreferences preferences;

    private SwitchCompat switchNotification;
    private Spinner spinnerFrequency;
    private CheckBox checkBoxAvenidaConstitucion;
    private CheckBox checkBoxAvenidaArgentina;
    private CheckBox checkBoxMontevil;
    private CheckBox checkBoxHermanosFelgueroso;
    private CheckBox checkBoxAvenidaCastilla;
    private CheckBox checkBoxSantaBarbara;
    private Spinner spinnerAirQualityLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        loadSettings();
        addListeners();
    }

    private void loadSettings() {
        preferences = getPreferences(MODE_PRIVATE);
        switchNotification = findViewById(R.id.switchNotification);
        spinnerFrequency = findViewById(R.id.spinnerFrequency);
        checkBoxAvenidaConstitucion = findViewById(R.id.checkBoxAvenidaConstitucion);
        checkBoxAvenidaArgentina = findViewById(R.id.checkBoxAvenidaArgentina);
        checkBoxMontevil = findViewById(R.id.checkBoxMontevil);
        checkBoxHermanosFelgueroso = findViewById(R.id.checkBoxHermanosFelgueroso);
        checkBoxAvenidaCastilla = findViewById(R.id.checkBoxAvenidaCastilla);
        checkBoxSantaBarbara= findViewById(R.id.checkBoxSantaBarbara);
        spinnerAirQualityLevel = findViewById(R.id.spinnerAirQualityLevel);

        switchNotification.setChecked(preferences.getBoolean("switchNotificationIsChecked", false));
        setNotificationLayoutVisibilityBasedOnSwitchCheck(switchNotification.isChecked());
        spinnerFrequency.setSelection(preferences.getInt("spinnerFrequencySelectionPosition", 0));
        checkBoxAvenidaConstitucion.setChecked(preferences.getBoolean("checkBoxAvenidaConstitucionIsChecked", false));
        checkBoxAvenidaArgentina.setChecked(preferences.getBoolean("checkBoxAvenidaArgentinaIsChecked", false));
        checkBoxMontevil.setChecked(preferences.getBoolean("checkBoxMontevilIsChecked", false));
        checkBoxHermanosFelgueroso.setChecked(preferences.getBoolean("checkBoxHermanosFelguerosoIsChecked", false));
        checkBoxAvenidaCastilla.setChecked(preferences.getBoolean("checkBoxAvenidaCastillaIsChecked", false));
        checkBoxSantaBarbara.setChecked(preferences.getBoolean("checkBoxSantaBarbaraIsChecked", false));
        spinnerAirQualityLevel.setSelection(preferences.getInt("spinnerAirQualityLevelPosition", 0));
    }

    private void addListeners(){
        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("switchNotificationIsChecked", isChecked).apply();
                if(isChecked)
                    startService();
                else
                    stopService();
                setNotificationLayoutVisibilityBasedOnSwitchCheck(isChecked);
            }
        });
        spinnerFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preferences.edit().putInt("spinnerFrequencySelectionPosition", position).apply();
                updateService();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        checkBoxAvenidaConstitucion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("checkBoxAvenidaConstitucionIsChecked", isChecked).apply();
                updateService();
            }
        });
        checkBoxAvenidaArgentina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("checkBoxAvenidaArgentinaIsChecked", isChecked).apply();
                updateService();
            }
        });
        checkBoxMontevil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("checkBoxMontevilIsChecked", isChecked).apply();
                updateService();
            }
        });
        checkBoxHermanosFelgueroso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("checkBoxHermanosFelguerosoIsChecked", isChecked).apply();
                updateService();
            }
        });
        checkBoxAvenidaCastilla.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("checkBoxAvenidaCastillaIsChecked", isChecked).apply();
                updateService();
            }
        });
        checkBoxSantaBarbara.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("checkBoxSantaBarbaraIsChecked", isChecked).apply();
                updateService();
            }
        });
        spinnerAirQualityLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preferences.edit().putInt("spinnerAirQualityLevelPosition", position).apply();
                updateService();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setNotificationLayoutVisibilityBasedOnSwitchCheck(boolean isChecked){
        LinearLayout layout = findViewById(R.id.layoutNotificationSettings);
        if(isChecked)
            layout.setVisibility(View.VISIBLE);
        else
            layout.setVisibility(View.GONE);
    }

    private void updateService(){
        stopService();
        startService();
    }

    private void startService(){
        //Get frequency
        long frequency;
        switch (spinnerFrequency.getSelectedItemPosition()){
            case 0:
                frequency = 1000 * 60 * 60; //1 hour
                break;
            case 1:
                frequency = 1000 * 60 * 60 * 2; //2 hours
                break;
            case 2:
                frequency = 1000 * 60 * 60 * 6; //6 hours
                break;
            case 3:
                frequency = 1000 * 60 * 60 * 12; //12 hours
                break;
            case 4:
                frequency = 1000 * 60 * 60 * 24; //24 hours
                break;
            case 5://TODO:Remove this
                frequency = 1000 ; //Test value
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + (String) spinnerFrequency.getSelectedItem());
        }

        //Prepare data for the notification service
        PersistableBundle extras = new PersistableBundle();
        List<Integer> unselectedStations = new ArrayList<>();
        if(!checkBoxAvenidaConstitucion.isChecked())
            unselectedStations.add(1); //TODO: Replace by constant
        if(!checkBoxAvenidaArgentina.isChecked())
            unselectedStations.add(2); //TODO: Replace by constant
        if(!checkBoxMontevil.isChecked())
            unselectedStations.add(10); //TODO: Replace by constant
        if(!checkBoxHermanosFelgueroso.isChecked())
            unselectedStations.add(3); //TODO: Replace by constant
        if(!checkBoxAvenidaCastilla.isChecked())
            unselectedStations.add(4); //TODO: Replace by constant
        if(!checkBoxSantaBarbara.isChecked())
            unselectedStations.add(11); //TODO: Replace by constant
        extras.putIntArray("selectedStations", Utils.integerListToIntArray(unselectedStations));
        extras.putBoolean("isOnlyBelowSafeLimits", spinnerAirQualityLevel.getSelectedItemPosition() == 1);

        Log.d(LOG_TAG, "Job params:" +frequency +unselectedStations +(spinnerAirQualityLevel.getSelectedItemPosition() == 1));

        ComponentName componentName = new ComponentName(this, NotificationJobService.class);
        JobInfo info = new JobInfo.Builder(NotificationJobService.JOB_ID, componentName)
                .setPersisted(true)
                .setMinimumLatency(frequency) //TODO: replaced with setPeriodic
                .setOverrideDeadline(frequency +1000) //TODO: replaced with setPeriodic
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setExtras(extras)
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(LOG_TAG, "Job scheduled");
        } else {
            Log.d(LOG_TAG, "Job scheduling failed");
        }
    }

    private void stopService(){
        ((JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE)).cancel(NotificationJobService.JOB_ID);
        Log.d(LOG_TAG, "Job cancelled");
    }
}