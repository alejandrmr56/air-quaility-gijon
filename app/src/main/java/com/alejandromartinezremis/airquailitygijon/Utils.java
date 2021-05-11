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
}
