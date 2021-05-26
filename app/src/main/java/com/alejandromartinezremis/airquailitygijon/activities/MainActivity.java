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
    private List<AirStation> airStations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAirStationsData();
    }

    public void onImageClick(View w){
        Intent intent = new Intent(this, StationActivity.class);
        int wId = w.getId();
        if (wId == R.id.imageViewPictureAvdaConstitucion) {
            intent.putExtra("station", getAirStationById(1));
        } else if (wId == R.id.imageViewPictureAvdaArgentina) {
            intent.putExtra("station", getAirStationById(2));
        } else if (wId == R.id.imageViewPictureMontevil) {
            intent.putExtra("station", getAirStationById(10));
        } else if (wId == R.id.imageViewPictureHermanosFelgueroso) {
            intent.putExtra("station", getAirStationById(3));
        } else if (wId == R.id.imageViewPictureAvdaCastilla) {
            intent.putExtra("station", getAirStationById(4));
        } else if (wId == R.id.imageViewPictureSantaBarbara) {
            intent.putExtra("station", getAirStationById(11));
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
        int itemId = item.getItemId();
        if (itemId == R.id.menu_refresh) {
            refreshData();
        } else if (itemId == R.id.menu_info) {
            displayInfoAlertDialog(this);
        } else if (itemId == R.id.menu_about) {
            displayAboutAlertDialog(this);
        } else if (itemId == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
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
                String imageViewCircleContentDescription = "";
                switch(airStation.getEstacion()){
                    case 1:
                        viewId = R.id.imageViewCircleAvdaConstitucion;
                        imageViewCircleContentDescription += getString(R.string.station_avenida_constitucion);
                        break;
                    case 2:
                        viewId = R.id.imageViewCircleAvdaArgentina;
                        imageViewCircleContentDescription += getString(R.string.station_avenida_argentina);
                        break;
                    case 10:
                        viewId = R.id.imageViewCircleMontevil;
                        imageViewCircleContentDescription += getString(R.string.station_montevil);
                        break;
                    case 3:
                        viewId = R.id.imageViewCircleHermanosFelgueroso;
                        imageViewCircleContentDescription += getString(R.string.station_hermanos_felgueroso);
                        break;
                    case 4:
                        viewId = R.id.imageViewCircleAvdaCastilla;
                        imageViewCircleContentDescription += getString(R.string.station_avenida_castilla);
                        break;
                    case 11:
                        viewId = R.id.imageViewCircleSantaBarbara;
                        imageViewCircleContentDescription += getString(R.string.station_santa_barbara);
                        break;
                    default:
                        viewId = -1;
                }
                if(viewId == -1) continue;
                AirStation.Quality airQuality = airStation.getAirQuality();
                ImageView qualityCircle = findViewById(viewId);
                qualityCircle.setImageResource(Utils.getDrawableIdForQualityCircle(airQuality));
                imageViewCircleContentDescription += " " +Utils.formatQuality(getApplicationContext(), airQuality);
                qualityCircle.setContentDescription(imageViewCircleContentDescription);

            }
        }

        private void removeLoadingView(){
            findViewById(R.id.loadingLayout).setVisibility(View.GONE);
            findViewById(R.id.mainLayout).setVisibility(View.VISIBLE);
        }
    }
}