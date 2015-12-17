package co.navdeep.weatherapp.utils;

import android.content.Context;

import co.navdeep.weatherapp.R;

/**
 * Created by Navdeep Sekhon on 12/15/2015.
 * www.navdeep.co
 */
public class Wind {
    private static String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};

    public static String getFormattedWind(Context context, double windSpeed, double windDirection){
        int windFormat;
        if(Utilities.isPreferredUnitMetric())
            windFormat = R.string.format_wind_kmh;
        else{
            windSpeed = .621371192237334f * windSpeed;
            windFormat = R.string.format_wind_mph;
        }

        String directionString = convertDegreesToDirectionString(windDirection);

        return context.getString(windFormat, windSpeed, directionString);
    }

    public static String convertDegreesToDirectionString(double windDirection){
        if(windDirection < 0)
            return "Unknown";
        return directions[ (int)Math.round(((windDirection % 360) / 45))%8 ];
    }
}
