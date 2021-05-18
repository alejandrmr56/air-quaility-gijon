package com.alejandromartinezremis.airquailitygijon.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.alejandromartinezremis.airquailitygijon.R;
import com.alejandromartinezremis.airquailitygijon.pojos.AirStation;
import com.alejandromartinezremis.airquailitygijon.pojos.AirStation.Quality;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public final class Utils {
    public final static String STATIONS_URL = "https://opendata.gijon.es/descargar.php?id=1&tipo=JSON";
    private static final String CHANNEL_ID = "CHANNEL_ID";

    private Utils() {}

    public static int getDrawableIdForQualityCircle(Quality quality){
        switch(quality){
            case VERY_GOOD:
                return R.drawable.ic_circle_very_good;
            case GOOD:
                return R.drawable.ic_circle_good;
            case BAD:
                return R.drawable.ic_circle_bad;
            case VERY_BAD:
                return R.drawable.ic_circle_very_bad;
            default:
                return R.drawable.ic_circle_unknown;
        }
    }

    public static int getDrawableIdForStationPicture(int stationId){
        switch (stationId){
            case 1: //TODO: Replace by constant
                return R.drawable.ic_station_avda_constitucion;
            case 2: //TODO: Replace by constant
                return R.drawable.ic_station_avda_argentina;
            case 10: //TODO: Replace by constant
                return R.drawable.ic_station_montevil;
            case 3: //TODO: Replace by constant
                return R.drawable.ic_station_hermanos_felgueroso;
            case 4: //TODO: Replace by constant
                return R.drawable.ic_station_avda_castilla;
            case 11: //TODO: Replace by constant
                return R.drawable.ic_station_santa_barbara;
            default:
                return R.drawable.ic_station_unknown;
        }
    }

    public static int getStringIdForStationName(int stationId){
        switch (stationId){
            case 1: //TODO: Replace by constant
                return R.string.station_avenida_constitucion;
            case 2: //TODO: Replace by constant
                return R.string.station_avenida_argentina;
            case 10: //TODO: Replace by constant
                return R.string.station_montevil;
            case 3: //TODO: Replace by constant
                return R.string.station_hermanos_felgueroso;
            case 4: //TODO: Replace by constant
                return R.string.station_avenida_castilla;
            case 11: //TODO: Replace by constant
                return R.string.station_santa_barbara;
            default:
                return R.string.station_unknown;
        }
    }

    public static String formatQuality(Context context, AirStation.Quality quality){
        switch (quality){
            case VERY_GOOD:
                return context.getString(R.string.air_quality_very_good);
            case GOOD:
                return context.getString(R.string.air_quality_good);
            case BAD:
                return context.getString(R.string.air_quality_bad);
            case VERY_BAD:
                return context.getString(R.string.air_quality_very_bad);
            default:
                return context.getString(R.string.air_quality_unknown);
        }
    }

    public static String formatDate(String date){ //YYYY_MM_DD_hh_mm
        if(date == null) return "";

        TimeZone userTimeZone = Calendar.getInstance().getTimeZone();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        try{
            String year = date.substring(0, 4);
            String month = date.substring(5, 7);
            String day = date.substring(8, 10);
            String hour = date.substring(11, 13);
            String minute = date.substring(14, 16);

            Date dateObject = new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateObject);
            calendar.add(Calendar.HOUR_OF_DAY, 2);//UTC+1 and an extra hour to show the end time of the measurements.
            if(TimeZone.getTimeZone("Europe/Madrid").inDaylightTime(dateObject))
                calendar.add(Calendar.HOUR_OF_DAY, 1);
            dateObject = calendar.getTime();

            return (dateObject.getDate() < 10 ? "0"+dateObject.getDate() : dateObject.getDate()) +"/"
                    +((dateObject.getMonth()+1) < 10 ? "0"+(dateObject.getMonth()+1) : (dateObject.getMonth()+1)) +"/"
                    +(dateObject.getYear()+1900) +" "
                    +(dateObject.getHours() < 10 ? "0"+dateObject.getHours() : dateObject.getHours()) +":"
                    +(dateObject.getMinutes() < 10 ? "0"+dateObject.getMinutes() : dateObject.getMinutes());
        }catch(IndexOutOfBoundsException | NumberFormatException e){
            return date;
        }finally {
            TimeZone.setDefault(userTimeZone);
        }
    }

    public static void createAndSendNotification(Context context, String title, String description){
        createNotificationChannel(context);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, createNotification(context, title, description));
    }

    private static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "[Channel Name]";//getString(R.string.channel_name); //TODO: Use R
            String description = "[Channel Description";// getString(R.string.channel_description); //TODO: Use R
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private static Notification createNotification(Context context, String title, String description){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_stat_cloud)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(description))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();
    }

    public static List<AirStation> getAirStations(){
        String str = "";
        BufferedReader bufferedReader = null;
        List<AirStation> airStations = new ArrayList<>();
        try {
            URLConnection connection = new URL(STATIONS_URL).openConnection();
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

        return airStations;
    }

    public static int[] integerListToIntArray(List<Integer> integerList){
        int[] intArray = new int[integerList.size()];

        int i = 0;
        for (Integer integer : integerList)
            intArray[i++] = integer;

        return intArray;
    }
}
