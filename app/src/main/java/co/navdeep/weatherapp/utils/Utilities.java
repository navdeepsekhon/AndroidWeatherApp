package co.navdeep.weatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import co.navdeep.weatherapp.R;

/**
 * Created by Navdeep on 6/13/2015.
 * http://www.navdeep.co
 */
public class Utilities {
    //user prefs set on app launch
    private static String PREF_UNITS = "metric";
    private static String PREF_LOCATION = "19711";
    private static boolean PREF_NOTIFY = true;

    public static long convertCelsiusToFahrenheit(long c){
        return (long)(((9*c)/5) +32);
    }

    public static long convertTempToUserPreferred(long c){
        return isPreferredUnitMetric() ? c : convertCelsiusToFahrenheit(c);
    }

    public static boolean isPreferredUnitMetric(){
        return PREF_UNITS.equalsIgnoreCase("metric");
    }

    public static String getDayOfTheWeek(long time){
        Calendar today = Calendar.getInstance();
        clearCalendarTimes(today);

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        clearCalendarTimes(tomorrow);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        clearCalendarTimes(cal);

        if(cal.get(Calendar.DAY_OF_YEAR) ==today.get(Calendar.DAY_OF_YEAR))
            return "Today";
        else if(cal.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR))
            return "Tomorrow";

        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEEE");
        return shortenedDateFormat.format(time);
    }

    public static String getMonthAndDate(long time){
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("MMMM dd");
        return shortenedDateFormat.format(time);
    }
    public static String getReadableDateString(long time, boolean twoPane){

        Calendar today = Calendar.getInstance();
        clearCalendarTimes(today);

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        clearCalendarTimes(tomorrow);

        Calendar dayAfterTomorrow = Calendar.getInstance();
        dayAfterTomorrow.add(Calendar.DAY_OF_YEAR, 2);
        clearCalendarTimes(dayAfterTomorrow);

        Calendar nextWeek = Calendar.getInstance();
        nextWeek.add(Calendar.DAY_OF_YEAR, 7);
        clearCalendarTimes(nextWeek);

        if(time >= nextWeek.getTimeInMillis()){
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        } else if(time >= dayAfterTomorrow.getTimeInMillis()){
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEEE");
            return shortenedDateFormat.format(time);
        } else if( time >= tomorrow.getTimeInMillis()){
            return "Tomorrow";
        } else if( time >= today.getTimeInMillis()){
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("MMM dd");
            String date = "Today";
            if(!twoPane)
                date += ", " + shortenedDateFormat.format(time);
            return  date;
        }

        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
        return shortenedDateFormat.format(time);
    }

    public static String getMaxMinStringInUserPreferredUnits(long max, long min){

        return Utilities.convertTempToUserPreferred(max) + "/" + Utilities.convertTempToUserPreferred(min);
    }

    public static String getPreferredLocation(){
        return PREF_LOCATION;
    }

    public static String getPreferredUnits(){
        return PREF_UNITS;
    }

    public static boolean doNotify(){
        return PREF_NOTIFY;
    }

    public static void setUserPreferences(Context caller){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(caller);
        String units = pref.getString(caller.getString(R.string.pref_units_key), caller.getString(R.string.pref_units_default));
        String location = pref.getString(caller.getString(R.string.pref_location_key), caller.getString(R.string.pref_location_default));
        boolean notifyDefault = "true".equals(caller.getString(R.string.pref_notify_default));
        boolean notify = pref.getBoolean(caller.getString(R.string.pref_notify_key), notifyDefault);

        Utilities.PREF_UNITS = units;
        Utilities.PREF_LOCATION = location;
        Utilities.PREF_NOTIFY = notify;
    }
    public static String httpGet(String urlString){
        String responseAsString = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(urlString);
            Log.d("httpGet", "Calling:" + urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream in = urlConnection.getInputStream();
            responseAsString = IOUtils.toString(in);
            Log.d("httpGet", responseAsString);
            return responseAsString;
        } catch(Exception e){
            Log.e("httpGet", "Error", e);
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
        return "";
    }
    public static int getWeatherIconIdFromDesc(String desc){
        if(desc != null){
            if(desc.toLowerCase().contains("rain"))
                return R.drawable.rain;
            else if(desc.toLowerCase().contains("thunderstorm"))
                return R.drawable.thunderstorm;
            else if(desc.toLowerCase().contains("snow"))
                return R.drawable.snow;
            else if(desc.toLowerCase().contains("cloud"))
                return R.drawable.clouds;
        }

        return R.drawable.sun;

    }

    private static Calendar clearCalendarTimes(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        return c;
    }
}
