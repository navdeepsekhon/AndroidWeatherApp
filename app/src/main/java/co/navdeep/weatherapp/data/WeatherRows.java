package co.navdeep.weatherapp.data;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Navdeep Sekhon on 7/12/2015.
 * www.navdeep.co
 */
public class WeatherRows implements Iterable<WeatherRow>{
    ArrayList<WeatherRow> rows = new ArrayList<>();
    long locationId;

    public WeatherRows(long locationId){
        this.locationId = locationId;
    }
    public void add(WeatherRow row){
        row.setLocationId(locationId);
        rows.add(row);
    }

    public ContentValues[] getContentValues(){
        ContentValues[] contentValues = new ContentValues[rows.size()];
        for(int i = 0; i < rows.size(); i++){
            contentValues[i] = rows.get(i).getContentValues();
        }
        return contentValues;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    @Override
    public Iterator iterator() {
        return rows.iterator();
    }
}
