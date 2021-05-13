package com.alejandromartinezremis.airquailitygijon.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class NotificationService extends Service {
    private final IBinder binder = new LocalBinder();
    NotificationServiceAlarm alarm = new NotificationServiceAlarm();

    @Override
    public void onCreate() {//Solves exception -> https://stackoverflow.com/questions/46445265/android-8-0-java-lang-illegalstateexception-not-allowed-to-start-service-inten
        super.onCreate();
        alarm.setAlarm(this);
        startForeground(1, new Notification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarm.setAlarm(this);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        NotificationService getService() {
            return NotificationService.this;
        }
    }
}
