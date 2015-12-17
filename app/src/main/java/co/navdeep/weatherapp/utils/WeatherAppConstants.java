package co.navdeep.weatherapp.utils;

/**
 * Created by Navdeep on 6/7/2015.
 */
public class WeatherAppConstants {
    public static final String OW_APIID_STRING = "OPEN_WEATHEER_API_KEY_HERE";
    public static final String urlSevenDayForecastByZip = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=10" + OW_APIID_STRING;
    public static final String urlFiveDayForeCastByZip = "http://api.openweathermap.org/data/2.5/forecast?mode=json&units=metric&cnt=5" + OW_APIID_STRING;


    //openWeather api call params
    public static final String OW_QUERY_PARAM = "q";

    //openWeather json response constants
    public static final String OW_CITY = "city";
    public static final String OW_CITY_NAME = "name";
    public static final String OW_CITY_COORD = "coord";
    public static final String OW_CITY_LONG = "lon";
    public static final String OW_CITY_LAT = "lat";

    public static final String OW_PRESSURE = "pressure";
    public static final String OW_HUMIDITY = "humidity";
    public static final String OW_WIND_SPEED = "speed";
    public static final String OW_WIND_DIRECTION = "deg";

    public static final String OW_LIST = "list";
    public static final String OW_MAIN = "main";
    public static final String  OW_TEMP = "temp";
    public static final String OW_TEMP_MIN = "min";
    public static final String OW_TEMP_MAX = "max";
    public static final String OW_WEATHER = "weather";
    public static final String OW_WEATHER_DESCRIPTION = "main";
    public static final String OW_WEATHER_ID = "id";

    //Loader IDs
    public static int CURSOR_LOADER_ID = 0;
}
