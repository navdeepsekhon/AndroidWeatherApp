package co.navdeep.weatherapp.utils;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.GregorianCalendar;

import co.navdeep.weatherapp.data.LocationRow;
import co.navdeep.weatherapp.data.WeatherRow;
import co.navdeep.weatherapp.data.WeatherRows;

/**
 * Created by Navdeep on 6/8/2015.
 */
public class WeatherJSONProcessing {

    public static void processWeatherJSONAndInsertDataIntoDb(String json, Context caller, String locationSetting){
        if(json == null)
            return;

        String[] weatherList = null;
        JSONArray daysList = getDayListFromOpenWeatherResponseJSON(json);
        LocationRow location = getLocationInfoFromWeatherResponse(json);
        location.setLocationSetting(locationSetting);
        long locationId = DbUtilities.insertLocationRowInDb(location, caller);
        WeatherRows weatherRows = new WeatherRows(locationId);


        GregorianCalendar cal = new GregorianCalendar();


        for(int i = 0; i < daysList.length(); i++){
            try {
                JSONObject day = daysList.getJSONObject(i);
                WeatherRow weather = getWeatherRowFromDayJSONObje(day, cal);
                weatherRows.add(weather);
                cal.add(cal.DATE, 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        DbUtilities.insertWeatherRows(weatherRows, caller);

    }

    private static WeatherRow getWeatherRowFromDayJSONObje(JSONObject day, GregorianCalendar cal){
        WeatherRow weather = new WeatherRow();
        getMinMaxFromDayJSONObj(day, weather);
        getWeatherIdAndDescFromDayJsonObj(day, weather);
        weather.setDate(cal.getTimeInMillis());
        weather.setHumidity(getHumidityFromDayJSONObj(day));
        weather.setPressure(getPressureFromDayJSONObj(day));
        weather.setWindDirection(getWindDirectionFromDayJSONObj(day));
        weather.setWindSpeed(getWindSpeedFromDayJSONObj(day));

        return weather;
    }
    private static LocationRow getLocationInfoFromWeatherResponse(String json){
        LocationRow location = new LocationRow();
        try{
            JSONObject city = new JSONObject(json).getJSONObject(WeatherAppConstants.OW_CITY);
            location.setCityName(city.getString(WeatherAppConstants.OW_CITY_NAME));
            getLocationCoordsFromCityJSONObj(city, location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return location;
    }

    private static void getLocationCoordsFromCityJSONObj(JSONObject city, LocationRow location){
        try {
            JSONObject coords =  city.getJSONObject(WeatherAppConstants.OW_CITY_COORD);
            location.setCoordLong(coords.getDouble(WeatherAppConstants.OW_CITY_LONG));
            location.setCoordLat(coords.getDouble(WeatherAppConstants.OW_CITY_LAT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static JSONArray getDayListFromOpenWeatherResponseJSON(String json){
        try {
            return new JSONObject(json).getJSONArray(WeatherAppConstants.OW_LIST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    private static void getMinMaxFromDayJSONObj(JSONObject day, WeatherRow weather){
        try{
            JSONObject temp = day.getJSONObject(WeatherAppConstants.OW_TEMP);
            weather.setMax(Math.round(temp.getDouble(WeatherAppConstants.OW_TEMP_MAX)));
            weather.setMin(Math.round(temp.getDouble(WeatherAppConstants.OW_TEMP_MIN)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static double getPressureFromDayJSONObj(JSONObject day){
        try{
            return day.getDouble(WeatherAppConstants.OW_PRESSURE);
        } catch(JSONException e){
            e.printStackTrace();
        }

        return 0.0;
    }

    private static int getHumidityFromDayJSONObj(JSONObject day){
        try{
            return day.getInt(WeatherAppConstants.OW_HUMIDITY);
        } catch(JSONException e){
            e.printStackTrace();
        }

        return 0;
    }

    private static double getWindSpeedFromDayJSONObj(JSONObject day){
        try{
            return day.getDouble(WeatherAppConstants.OW_WIND_SPEED);
        } catch(JSONException e){
            e.printStackTrace();
        }

        return 0.0;
    }

    private static double getWindDirectionFromDayJSONObj(JSONObject day){
        try{
            return day.getDouble(WeatherAppConstants.OW_WIND_DIRECTION);
        } catch(JSONException e){
            e.printStackTrace();
        }

        return 0.0;
    }

    private static void getWeatherIdAndDescFromDayJsonObj(JSONObject day, WeatherRow weather){
        try {
            JSONObject weatherObj =  day.getJSONArray(WeatherAppConstants.OW_WEATHER).getJSONObject(0);
            weather.setDesc(weatherObj.getString(WeatherAppConstants.OW_WEATHER_DESCRIPTION));
            weather.setWeatherId(weatherObj.getInt(WeatherAppConstants.OW_WEATHER_ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
