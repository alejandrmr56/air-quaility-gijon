package com.alejandromartinezremis.airquailitygijon;

import com.alejandromartinezremis.airquailitygijon.logic.AirStation.Quality;

public final class Utils {
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
            case 1:
                return R.drawable.ic_station_avda_constitucion;
            case 2:
                return R.drawable.ic_station_avda_argentina;
            case 10:
                return R.drawable.ic_station_montevil;
            case 3:
                return R.drawable.ic_station_hermanos_felgueroso;
            case 4:
                return R.drawable.ic_station_avda_castilla;
            case 11:
                return R.drawable.ic_station_santa_barbara;
            default:
                return R.drawable.ic_station_unknown;
        }
    }

    public static int getStringIdForStationName(int stationId){
        switch (stationId){
            case 1:
                return R.string.station_avenida_constitucion;
            case 2:
                return R.string.station_avenida_argentina;
            case 10:
                return R.string.station_montevil;
            case 3:
                return R.string.station_hermanos_felgueroso;
            case 4:
                return R.string.station_avenida_castilla;
            case 11:
                return R.string.station_santa_barbara;
            default:
                return R.string.station_unknown;
        }
    }
}
