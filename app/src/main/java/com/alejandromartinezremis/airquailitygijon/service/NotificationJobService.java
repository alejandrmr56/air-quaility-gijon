package com.alejandromartinezremis.airquailitygijon.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.alejandromartinezremis.airquailitygijon.utils.Utils;
import com.alejandromartinezremis.airquailitygijon.pojos.AirStation;

import java.util.List;

public class NotificationJobService extends JobService {
    private static final String LOG_TAG = "NotificationJobService";
    private boolean isJobStopped = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(LOG_TAG, "Job started");

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AirStation> airStations = Utils.getAirStations();
                String notificationDescription = "";
                for(AirStation airStation : airStations){//TODO: Change this block of functionality based on user settings.
                    notificationDescription += getString(Utils.getStringIdForStationName(airStation.getEstacion()));
                    notificationDescription += ": " +Utils.formatQuality(getApplicationContext(), airStation.getAirQuality()) +"\n";
                    Log.d(LOG_TAG, notificationDescription);
                }

                Utils.createAndSendNotification(getApplicationContext(), "Test title", notificationDescription); //TODO: Change title and move to R

                Log.d(LOG_TAG, "Job finished");
                jobFinished(params, true);
            }
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
