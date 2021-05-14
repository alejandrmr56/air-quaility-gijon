package com.alejandromartinezremis.airquailitygijon.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class NotificationJobService extends JobService {
    private static final String LOG_TAG = "NotificationJobService";
    private boolean isJobStopped = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(LOG_TAG, "Job started");

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d(LOG_TAG, "run: " + i);
                    if (isJobStopped) {
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
