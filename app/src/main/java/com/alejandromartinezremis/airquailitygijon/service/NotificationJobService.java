package com.alejandromartinezremis.airquailitygijon.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.alejandromartinezremis.airquailitygijon.R;
import com.alejandromartinezremis.airquailitygijon.utils.Utils;
import com.alejandromartinezremis.airquailitygijon.pojos.AirStation;

import java.util.List;


/**
 * Service in charge of fetching the data and notifying the user
 */
public class NotificationJobService extends JobService {
    public static final int JOB_ID = 0;
    private static final String LOG_TAG = "NotificationJobService";
    private boolean isJobStopped = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(LOG_TAG, "Job started");

        new Thread(() -> {
            //Get extras
            int[] unselectedStations = params.getExtras().getIntArray("selectedStations");
            boolean isOnlyBelowSafeLimits = params.getExtras().getBoolean("isOnlyBelowSafeLimits");

            //Fetch data
            List<AirStation> airStations = Utils.getAirStations();

            //Remove stations not selected by the user
            for (int unselectedStation : unselectedStations)
                for (AirStation airStation : airStations)
                    if (unselectedStation == airStation.getEstacion()) {
                        airStations.remove(airStation);
                        break;
                    }

            //Build notification text
            String notificationDescription = "";
            for(AirStation airStation : airStations){
                if(isOnlyBelowSafeLimits && (airStation.getAirQuality().equals(AirStation.Quality.GOOD) || airStation.getAirQuality().equals(AirStation.Quality.VERY_GOOD))) //Ignore stations that have good quality if user is not interested
                    continue;
                notificationDescription += getString(Utils.getStringIdForStationName(airStation.getEstacion()));
                notificationDescription += ": " +Utils.formatQuality(getApplicationContext(), airStation.getAirQuality()) +"\n";
                Log.d(LOG_TAG, notificationDescription);
            }

            //Send notification
            if(!notificationDescription.equals(""))//Don't send notification if no station met the user settings
                Utils.createAndSendNotification(getApplicationContext(), getString(R.string.notification_title), notificationDescription);

            Log.d(LOG_TAG, "Job finished");
            jobFinished(params, false);
        }).start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(LOG_TAG, "Job cancelled before completion");
        isJobStopped = true;
        return true;
    }
}
