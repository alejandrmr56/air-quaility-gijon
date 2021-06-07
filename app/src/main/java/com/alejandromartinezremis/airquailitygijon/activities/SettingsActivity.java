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
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alejandromartinezremis.airquailitygijon.R;
import com.alejandromartinezremis.airquailitygijon.service.NotificationJobService;
import com.alejandromartinezremis.airquailitygijon.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity that displays the settings of the application
 */
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

    /**
     * Loads the settings stored in the preferences
     */
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

    /**
     * Adds the listeners to the GUI elements
     */
    private void addListeners(){
        switchNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean("switchNotificationIsChecked", isChecked).apply();
            if(isChecked)
                startService();
            else
                stopService();
            setNotificationLayoutVisibilityBasedOnSwitchCheck(isChecked);
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
        checkBoxAvenidaConstitucion.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean("checkBoxAvenidaConstitucionIsChecked", isChecked).apply();
            updateService();
        });
        checkBoxAvenidaArgentina.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean("checkBoxAvenidaArgentinaIsChecked", isChecked).apply();
            updateService();
        });
        checkBoxMontevil.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean("checkBoxMontevilIsChecked", isChecked).apply();
            updateService();
        });
        checkBoxHermanosFelgueroso.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean("checkBoxHermanosFelguerosoIsChecked", isChecked).apply();
            updateService();
        });
        checkBoxAvenidaCastilla.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean("checkBoxAvenidaCastillaIsChecked", isChecked).apply();
            updateService();
        });
        checkBoxSantaBarbara.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean("checkBoxSantaBarbaraIsChecked", isChecked).apply();
            updateService();
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

    /**
     * Makes the settings related to the notifications either visible or invisible
     * @param isChecked The flag that makes the section visible or invisible
     */
    private void setNotificationLayoutVisibilityBasedOnSwitchCheck(boolean isChecked){
        LinearLayout layout = findViewById(R.id.layoutNotificationSettings);
        if(isChecked)
            layout.setVisibility(View.VISIBLE);
        else
            layout.setVisibility(View.GONE);
    }

    /**
     * Updates the notification service whenever there's an update
     * @see #stopService()
     * @see #startService()
     */
    private void updateService(){
        stopService();
        startService();
    }

    /**
     * Starts the notification service
     */
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
                throw new IllegalStateException("Unexpected value: " + spinnerFrequency.getSelectedItem());
        }

        //Prepare data for the notification service
        PersistableBundle extras = new PersistableBundle();
        List<Integer> unselectedStations = new ArrayList<>();
        if(!checkBoxAvenidaConstitucion.isChecked())
            unselectedStations.add(Utils.STATION_ID_AVDA_CONSTITUCION);
        if(!checkBoxAvenidaArgentina.isChecked())
            unselectedStations.add(Utils.STATION_ID_AVDA_ARGENTINA);
        if(!checkBoxMontevil.isChecked())
            unselectedStations.add(Utils.STATION_ID_MONTEVIL);
        if(!checkBoxHermanosFelgueroso.isChecked())
            unselectedStations.add(Utils.STATION_ID_HERMANOS_FELGUEROSO);
        if(!checkBoxAvenidaCastilla.isChecked())
            unselectedStations.add(Utils.STATION_ID_AVDA_CASTILLA);
        if(!checkBoxSantaBarbara.isChecked())
            unselectedStations.add(Utils.STATION_ID_SANTA_BARBARA);
        extras.putIntArray("selectedStations", Utils.integerListToIntArray(unselectedStations));
        extras.putBoolean("isOnlyBelowSafeLimits", spinnerAirQualityLevel.getSelectedItemPosition() == 1);

        Log.d(LOG_TAG, "Job params:" +frequency +unselectedStations +(spinnerAirQualityLevel.getSelectedItemPosition() == 1));

        ComponentName componentName = new ComponentName(this, NotificationJobService.class);
        JobInfo info = new JobInfo.Builder(NotificationJobService.JOB_ID, componentName)
                .setPersisted(true)
                .setPeriodic(frequency, 1) //flexMilis set to 1ms so that it will be clamped
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setExtras(extras)
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(LOG_TAG, "Job scheduled" +info.getIntervalMillis() +"|" +info.getFlexMillis());
        } else {
            Log.d(LOG_TAG, "Job scheduling failed");
        }
    }

    /**
     * Stops the notification service
     */
    private void stopService(){
        ((JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE)).cancel(NotificationJobService.JOB_ID);
        Log.d(LOG_TAG, "Job cancelled");
    }
}