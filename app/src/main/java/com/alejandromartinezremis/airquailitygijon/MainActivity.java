package com.alejandromartinezremis.airquailitygijon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alejandromartinezremis.airquailitygijon.logic.AirStation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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
        switch (w.getId()){
            case R.id.imageViewPictureAvdaConstitucion:
                intent.putExtra("stationName", getString(R.string.station_avenida_constitucion));
                intent.putExtra("stationPictureId", R.drawable.ic_station_avda_constitucion);
                intent.putExtra("station", getAirStationById(1)); //TODO: Risk of NPE after getAirStationByID returns null
                break;
            case R.id.imageViewPictureAvdaArgentina:
                intent.putExtra("stationName", getString(R.string.station_avenida_argentina));
                intent.putExtra("stationPictureId", R.drawable.ic_station_avda_argentina);
                intent.putExtra("station", getAirStationById(2)); //TODO: Risk of NPE after getAirStationByID returns null
                break;
            case R.id.imageViewPictureMontevil:
                intent.putExtra("stationName", getString(R.string.station_montevil));
                intent.putExtra("stationPictureId", R.drawable.ic_station_montevil);
                intent.putExtra("station", getAirStationById(10)); //TODO: Risk of NPE after getAirStationByID returns null
                break;
            case R.id.imageViewPictureHermanosFelgueroso:
                intent.putExtra("stationName", getString(R.string.station_hermanos_felgueroso));
                intent.putExtra("stationPictureId", R.drawable.ic_station_hermanos_felgueroso);
                intent.putExtra("station", getAirStationById(3)); //TODO: Risk of NPE after getAirStationByID returns null
                break;
            case R.id.imageViewPictureAvdaCastilla:
                intent.putExtra("stationName", getString(R.string.station_avenida_castilla));
                intent.putExtra("stationPictureId", R.drawable.ic_station_avda_castilla);
                intent.putExtra("station", getAirStationById(4)); //TODO: Risk of NPE after getAirStationByID returns null
                break;
            case R.id.imageViewPictureSantaBarbara:
                intent.putExtra("stationName", getString(R.string.station_santa_barbara));
                intent.putExtra("stationPictureId", R.drawable.ic_station_santa_barbara);
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
                updateData();
                break;
            case R.id.menu_info:
                displayInfoAlertDialog(this);
                break;
            case R.id.menu_about:
                displayAboutAlertDialog(this);
                break;
        }
        return true;
    }


    private void updateData(){
        throw new UnsupportedOperationException("Not implemented yet"); //TODO: Implement this
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
        new Communicator().execute("https://opendata.gijon.es/descargar.php?id=1&tipo=JSON");
    }

    private class Communicator extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String str = "";
            try {
                URLConnection connection = new URL(strings[0]).openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while((line = bufferedReader.readLine()) != null)
                    str += line;
            } catch (IOException e) {
                e.printStackTrace(); //TODO: Handle exception
            }
            return str;
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                JSONArray jsonArray = jsonObject.getJSONObject("calidadairemediatemporales").getJSONArray("calidadairemediatemporal");
                int counter = 0;
                for(int i=0; i<jsonArray.length(); i++){
                    if(counter != 0 && airStations.get(counter-1).getEstacion() == jsonArray.getJSONObject(i).getInt("estacion"))//Just add the latest record of each station.
                        continue;
                    airStations.add(new AirStation(jsonArray.getJSONObject(i)));
                    counter++;
                }
            } catch (JSONException e) {
                e.printStackTrace(); //TODO: Handle exception
            }
        }
    }
}