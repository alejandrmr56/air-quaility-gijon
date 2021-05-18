package com.alejandromartinezremis.airquailitygijon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alejandromartinezremis.airquailitygijon.R;

public class SettingsActivity extends AppCompatActivity {
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
                setNotificationLayoutVisibilityBasedOnSwitchCheck(isChecked);
            }
        });
        spinnerFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preferences.edit().putInt("spinnerFrequencySelectionPosition", position).apply();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        checkBoxAvenidaConstitucion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("checkBoxAvenidaConstitucionIsChecked", isChecked).apply();
            }
        });
        checkBoxAvenidaArgentina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("checkBoxAvenidaArgentinaIsChecked", isChecked).apply();
            }
        });
        checkBoxMontevil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("checkBoxMontevilIsChecked", isChecked).apply();
            }
        });
        checkBoxHermanosFelgueroso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("checkBoxHermanosFelguerosoIsChecked", isChecked).apply();
            }
        });
        checkBoxAvenidaCastilla.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("checkBoxAvenidaCastillaIsChecked", isChecked).apply();
            }
        });
        checkBoxSantaBarbara.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean("checkBoxSantaBarbaraIsChecked", isChecked).apply();
            }
        });
        spinnerAirQualityLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preferences.edit().putInt("spinnerAirQualityLevelPosition", position).apply();
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
}