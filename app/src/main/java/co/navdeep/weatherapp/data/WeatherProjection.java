package co.navdeep.weatherapp.data;

import java.util.HashMap;

/**
 * Created by Navdeep Sekhon on 8/18/2015.
 * www.navdeep.co
 */
public class WeatherProjection {
    public static final String[] FORECAST_COLUMNS = {
            WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry._ID,
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,
            WeatherContract.LocationEntry.COLUMN_COORD_LAT,
            WeatherContract.LocationEntry.COLUMN_COORD_LONG,
            WeatherContract.WeatherEntry.COLUMN_HUMIDITY,
            WeatherContract.WeatherEntry.COLUMN_WIND_SPEED,
            WeatherContract.WeatherEntry.COLUMN_PRESSURE,
            WeatherContract.WeatherEntry.COLUMN_DEGREES
    };


    private static HashMap<String, Integer> columnIndexes = new HashMap<>();
    static{
        columnIndexes.put(WeatherContract.WeatherEntry._ID, 0);
        columnIndexes.put(WeatherContract.WeatherEntry.COLUMN_DATE, 1);
        columnIndexes.put(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC, 2);
        columnIndexes.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, 3);
        columnIndexes.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, 4);
        columnIndexes.put(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING, 5);
        columnIndexes.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, 6);
        columnIndexes.put(WeatherContract.LocationEntry.COLUMN_COORD_LAT, 7);
        columnIndexes.put(WeatherContract.LocationEntry.COLUMN_COORD_LONG, 8);
        columnIndexes.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, 9);
        columnIndexes.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, 10);
        columnIndexes.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, 11);
        columnIndexes.put(WeatherContract.WeatherEntry.COLUMN_DEGREES, 12);
    }

    public static int getColumnIndex(String columnName){
        try{
            return columnIndexes.get(columnName);
        } catch(Exception e){
            return -1;
        }
    }
}
