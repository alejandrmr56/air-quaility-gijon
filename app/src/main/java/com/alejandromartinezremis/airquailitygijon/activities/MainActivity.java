package com.alejandromartinezremis.airquailitygijon.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alejandromartinezremis.airquailitygijon.R;
import com.alejandromartinezremis.airquailitygijon.utils.Utils;
import com.alejandromartinezremis.airquailitygijon.pojos.AirStation;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";

    private List<AirStation> airStations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAirStationsData();
    }

    public void onImageClick(View w){
        Intent intent = new Intent(this, StationActivity.class);
        switch (w.getId()){
            case R.id.imageViewPictureAvdaConstitucion:
                intent.putExtra("station", getAirStationById(1)); //TODO: Risk of NPE after getAirStationByID returns null
                break;
            case R.id.imageViewPictureAvdaArgentina:
                intent.putExtra("station", getAirStationById(2)); //TODO: Risk of NPE after getAirStationByID returns null
                break;
            case R.id.imageViewPictureMontevil:
                intent.putExtra("station", getAirStationById(10)); //TODO: Risk of NPE after getAirStationByID returns null
                break;
            case R.id.imageViewPictureHermanosFelgueroso:
                intent.putExtra("station", getAirStationById(3)); //TODO: Risk of NPE after getAirStationByID returns null
                break;
            case R.id.imageViewPictureAvdaCastilla:
                intent.putExtra("station", getAirStationById(4)); //TODO: Risk of NPE after getAirStationByID returns null
                break;
            case R.id.imageViewPictureSantaBarbara:
                intent.putExtra("station", getAirStationById(11)); //TODO: Risk of NPE after getAirStationByID returns null
                break;
        }
        startActivity(intent);
    }


    /* Menu-related code below this line */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refreshData();
                break;
            case R.id.menu_info:
                displayInfoAlertDialog(this);
                break;
            case R.id.menu_about:
                displayAboutAlertDialog(this);
                break;
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void refreshData(){
        setContentView(R.layout.activity_main);
        getAirStationsData();
    }

    private void displayInfoAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AirQuailityGijon_Dialog);
        builder.setTitle(R.string.menu_info_alert_dialog_title)
                .setMessage(R.string.menu_info_alert_dialog_message)
                .setPositiveButton(R.string.ok, null)
                .create().show();
    }

    private void displayAboutAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AirQuailityGijon_Dialog);
        builder.setTitle(R.string.menu_about_alert_dialog_title)
                .setMessage(R.string.menu_about_alert_dialog_message)
                .setPositiveButton(R.string.ok, null)
                .create().show();
    }
    /* End of menu-related code */

    private AirStation getAirStationById(int airStationId){
        for(AirStation airStation : airStations)
            if(airStationId == airStation.getEstacion())
                return airStation;
        return null;
    }


    //TODO: Check exception if no Internet connection or list is empty or similar.
    private void getAirStationsData() {
        new Communicator().execute();
    }

    private class Communicator extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            airStations = Utils.getAirStations();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            displayAirQualityCircles();
            removeLoadingView();
        }

        private void displayAirQualityCircles(){
            for(AirStation airStation : airStations){
                int viewId;
                switch(airStation.getEstacion()){
                    case 1:
                        viewId = R.id.imageViewCircleAvdaConstitucion;
                        break;
                    case 2:
                        viewId = R.id.imageViewCircleAvdaArgentina;
                        break;
                    case 10:
                        viewId = R.id.imageViewCircleMontevil;
                        break;
                    case 3:
                        viewId = R.id.imageViewCircleHermanosFelgueroso;
                        break;
                    case 4:
                        viewId = R.id.imageViewCircleAvdaCastilla;
                        break;
                    case 11:
                        viewId = R.id.imageViewCircleSantaBarbara;
                        break;
                    default:
                        viewId = -1;
                }
                if(viewId == -1) continue;
                ((ImageView)findViewById(viewId)).setImageResource(Utils.getDrawableIdForQualityCircle(airStation.getAirQuality()));
            }
        }

        private void removeLoadingView(){
            findViewById(R.id.loadingLayout).setVisibility(View.GONE);
            findViewById(R.id.mainLayout).setVisibility(View.VISIBLE);
        }
    }
}