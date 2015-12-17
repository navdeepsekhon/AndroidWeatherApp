package co.navdeep.weatherapp.data;

import android.content.ContentValues;

/**
 * Created by Navdeep Sekhon on 7/12/2015.
 * www.navdeep.co
 */
public class LocationRow {
    String locationSetting;
    String cityName;
    double coordLong;
    double coordLat;

    public LocationRow(){
        super();
    }
    public LocationRow(String locationSetting, String cityName, double coordLong, double coordLat) {
        super();
        this.locationSetting = locationSetting;
        this.cityName = cityName;
        this.coordLong = coordLong;
        this.coordLat = coordLat;
    }

    public String getLocationSetting() {
        return locationSetting;
    }

    public void setLocationSetting(String locationSetting) {
        this.locationSetting = locationSetting;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getCoordLong() {
        return coordLong;
    }

    public void setCoordLong(double coordLong) {
        this.coordLong = coordLong;
    }

    public double getCoordLat() {
        return coordLat;
    }

    public void setCoordLat(double coordLat) {
        this.coordLat = coordLat;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING, locationSetting);
        contentValues.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, cityName);
        contentValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LAT, coordLat);
        contentValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LONG, coordLong);
        return contentValues;
    }
}
