package co.navdeep.weatherapp.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import co.navdeep.weatherapp.data.LocationRow;
import co.navdeep.weatherapp.data.WeatherContract;
import co.navdeep.weatherapp.data.WeatherProjection;
import co.navdeep.weatherapp.data.WeatherRow;
import co.navdeep.weatherapp.data.WeatherRows;

/**
 * Created by Navdeep Sekhon on 7/12/2015.
 * www.navdeep.co
 */
public class DbUtilities {

    public static long insertLocationRowInDb(LocationRow location, Context activity){
        String[] selectionArgs = {location.getLocationSetting()};
        Cursor c = activity.getContentResolver().query(WeatherContract.LocationEntry.CONTENT_URI, null, WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING + " = ?", selectionArgs, null);
        if(c.moveToFirst()) {
            int locationIdIndex = c.getColumnIndex(WeatherContract.LocationEntry._ID);
            return c.getLong(locationIdIndex);
        }
        Uri uri = activity.getContentResolver().insert(WeatherContract.LocationEntry.CONTENT_URI, location.getContentValues());
        c.close();
        return ContentUris.parseId(uri);
    }

    public static long insertWeatherRowInDb(WeatherRow weather, Context activity){
        Uri uri = activity.getContentResolver().insert(WeatherContract.WeatherEntry.CONTENT_URI, weather.getContentValues());
        return ContentUris.parseId(uri);
    }

    public static void insertWeatherRows(WeatherRows rows, Context activity){
        String[] currTime = {Long.toString(System.currentTimeMillis())};
        activity.getContentResolver().delete(WeatherContract.WeatherEntry.CONTENT_URI, WeatherContract.WeatherEntry.COLUMN_DATE + "< ?", currTime);
        activity.getContentResolver().bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI, rows.getContentValues());
    }

    public static ArrayList<WeatherRow> cursorRowToWeatherRows(Cursor c) {
        ArrayList<WeatherRow> rows = new ArrayList<>();
        if ( c.moveToFirst() ) {
            do {
                WeatherRow row = new WeatherRow();
                row.setHumidity(c.getInt(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_HUMIDITY)));
                row.setLocationId(c.getLong(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_LOC_KEY)));
                row.setWeatherId(c.getInt(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID)));
                row.setWindSpeed(c.getDouble(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED)));
                row.setDesc(c.getString(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC)));
                row.setWindDirection(c.getDouble(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DEGREES)));
                row.setDate(c.getLong(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DATE)));
                row.setMax(c.getLong(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP)));
                row.setMin(c.getLong(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP)));
                row.setPressure(c.getDouble(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_PRESSURE)));
                rows.add(row);
            } while (c.moveToNext());
        }

        return rows;
    }

    public static WeatherRow cursorRowToWeatherRow(Cursor c) {
        WeatherRow row = new WeatherRow();

        row.setHumidity(c.getInt(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_HUMIDITY)));
//        row.setLocationId(c.getLong(c.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_LOC_KEY)));
        row.setWeatherId(c.getInt(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID)));
        row.setWindSpeed(c.getDouble(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED)));
        row.setDesc(c.getString(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC)));
        row.setWindDirection(c.getDouble(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DEGREES)));
        row.setDate(c.getLong(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DATE)));
        row.setMax(c.getLong(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP)));
        row.setMin(c.getLong(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP)));
        row.setPressure(c.getDouble(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_PRESSURE)));
        return row;
    }
}
