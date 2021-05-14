package com.alejandromartinezremis.airquailitygijon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.alejandromartinezremis.airquailitygijon.logic.AirStation;
import com.alejandromartinezremis.airquailitygijon.service.NotificationJobService;

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
            case R.id.testNotification:
                ComponentName componentName = new ComponentName(this, NotificationJobService.class);
                JobInfo info = new JobInfo.Builder(123, componentName)
                        .setPersisted(true)
                        .setMinimumLatency(5 *1000) //60 mins
                        .setOverrideDeadline(10 *1000) // 70 mins
                        //.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .build();
                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                int resultCode = scheduler.schedule(info);
                if (resultCode == JobScheduler.RESULT_SUCCESS) {
                    Log.d(LOG_TAG, "Job scheduled");
                } else {
                    Log.d(LOG_TAG, "Job scheduling failed");
                }
                break;
        }
        return true;
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
        new Communicator().execute("https://opendata.gijon.es/descargar.php?id=1&tipo=JSON");
    }

    private class Communicator extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {
            String str = "";
            BufferedReader bufferedReader = null;
            try {
                URLConnection connection = new URL(strings[0]).openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while((line = bufferedReader.readLine()) != null)
                    str += line;

                JSONObject jsonObject = new JSONObject(str);
                JSONArray jsonArray = jsonObject.getJSONObject("calidadairemediatemporales").getJSONArray("calidadairemediatemporal");
                int counter = 0;
                for(int i=0; i<jsonArray.length(); i++){
                    if(counter != 0 && airStations.get(counter-1).getEstacion() == jsonArray.getJSONObject(i).getInt("estacion"))//Just add the latest record of each station.
                        continue;
                    airStations.add(new AirStation(jsonArray.getJSONObject(i)));
                    counter++;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace(); //TODO: Handle exception
            }finally {
                if(bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
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