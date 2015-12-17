package co.navdeep.weatherapp.data;

import android.content.ContentValues;
import android.content.Context;

import co.navdeep.weatherapp.R;
import co.navdeep.weatherapp.utils.Utilities;

/**
 * Created by Navdeep Sekhon on 7/12/2015.
 * www.navdeep.co
 */
public class WeatherRow {
    long locationId;
    int weatherId;
    long date;
    long min;
    long max;
    double pressure;
    double windSpeed;
    double windDirection;
    int humidity;
    String desc;

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getMax() {
        return max;
    }

    public String getMaxStringInUserPreferredUnits(Context context){
        long userPrefMax = Utilities.convertTempToUserPreferred(max);
        return context.getString(R.string.format_temperature, userPrefMax);
    }

    public String getMinStringInUserPrefferedUnits(Context context){
        long userPrefMin = Utilities.convertTempToUserPreferred(min);
        return context.getString(R.string.format_temperature, userPrefMin);
    }

    public void setMax(long max) {
        this.max = max;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMinMaxString(){
        return Utilities.getMaxMinStringInUserPreferredUnits(max, min);
    }

    public String getReadableDate(boolean twoPane){
        return Utilities.getReadableDateString(date, twoPane);
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(WeatherContract.WeatherEntry.COLUMN_LOC_KEY, locationId);
        contentValues.put(WeatherContract.WeatherEntry.COLUMN_DATE, date);
        contentValues.put(WeatherContract.WeatherEntry.COLUMN_DEGREES, windDirection);
        contentValues.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, humidity);
        contentValues.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, pressure);
        contentValues.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, max);
        contentValues.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, min);
        contentValues.put(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC, desc);
        contentValues.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, windSpeed);
        contentValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, weatherId);

        return contentValues;
    }

    public String toString(){
        return getReadableDate(false) + " " + getDesc() + " " + getMinMaxString();
    }
}
