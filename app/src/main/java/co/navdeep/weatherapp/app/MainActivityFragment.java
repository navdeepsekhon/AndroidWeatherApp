package co.navdeep.weatherapp.app;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import co.navdeep.weatherapp.R;
import co.navdeep.weatherapp.adapter.ForecastAdapter;
import co.navdeep.weatherapp.data.WeatherContract;
import co.navdeep.weatherapp.data.WeatherProjection;
import co.navdeep.weatherapp.sync.WeatherAppSyncAdapter;
import co.navdeep.weatherapp.utils.Utilities;
import co.navdeep.weatherapp.utils.WeatherAppConstants;


/**
 * Created by Navdeep on 6/13/2015.
 * http://www.navdeep.co
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor > {

    private final String LOG_TAG = getClass().getSimpleName();

    private ForecastAdapter mForecastAdapter;
    private Callback mCallback;
    private int mPosition = ListView.INVALID_POSITION;
    private ListView mListView;
    private boolean mTwoPane = false;

    private String SELECTED_POSITION_KEY = "selectedPosition";

    public MainActivityFragment() {
    }
    public interface Callback {
        public void onItemSelected(Uri dateUri);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback = (Callback) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement Callback for MainActivity Fragment");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    void onLocationChanged( ) {
        WeatherAppSyncAdapter.syncImmediately(getActivity());
        getLoaderManager().restartLoader(WeatherAppConstants.CURSOR_LOADER_ID, null, this);
    }

    void onUnitChanged(){
        getLoaderManager().restartLoader(WeatherAppConstants.CURSOR_LOADER_ID, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_forecastfragment, menu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(WeatherAppConstants.CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_location){
            openPreferredLocationInMap();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mPosition != ListView.INVALID_POSITION)
            outState.putInt(SELECTED_POSITION_KEY, mPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        if(savedInstanceState != null && savedInstanceState.containsKey(SELECTED_POSITION_KEY))
            mPosition = savedInstanceState.getInt(SELECTED_POSITION_KEY);

        mForecastAdapter = new ForecastAdapter(getActivity(), null, 0);
        mForecastAdapter.setmTwoPane(mTwoPane);

        mListView = (ListView)rootView.findViewById(R.id.listview_forecast);
        mListView.setAdapter(mForecastAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    String locationSetting = Utilities.getPreferredLocation();
                    Uri dataUri = WeatherContract.WeatherEntry.buildWeatherLocationWithDate(
                            locationSetting, cursor.getLong(WeatherProjection.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DATE))
                    );
                    mPosition = position;
                    mCallback.onItemSelected(dataUri);
                }
            }
        });

        if(!mForecastAdapter.isEmpty() && mPosition != ListView.INVALID_POSITION)
            mListView.smoothScrollToPosition(mPosition);
        return rootView;
    }

    /***** LoaderManager functions *****/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String locationSetting = Utilities.getPreferredLocation();

        String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(
                locationSetting, System.currentTimeMillis());

        return new CursorLoader(getActivity(), weatherForLocationUri, WeatherProjection.FORECAST_COLUMNS, null, null, sortOrder);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mForecastAdapter.swapCursor(data);
        if(mPosition != ListView.INVALID_POSITION)
            mListView.smoothScrollToPosition(mPosition);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mForecastAdapter.swapCursor(null);
    }

    public void setmTwoPane(boolean mTwoPane){
        this.mTwoPane = mTwoPane;
        if(mForecastAdapter != null)
            mForecastAdapter.setmTwoPane(mTwoPane);
    }

    private void openPreferredLocationInMap() {
        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps
        if ( null != mForecastAdapter ) {
            Cursor c = mForecastAdapter.getCursor();
            if ( null != c ) {
                c.moveToPosition(0);
                String posLat = c.getString(WeatherProjection.getColumnIndex(WeatherContract.LocationEntry.COLUMN_COORD_LAT));
                String posLong = c.getString(WeatherProjection.getColumnIndex(WeatherContract.LocationEntry.COLUMN_COORD_LONG));
                Uri geoLocation = Uri.parse("geo:" + posLat + "," + posLong);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(geoLocation);

                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Log.d(LOG_TAG, "Couldn't call " + geoLocation.toString() + ", no receiving apps installed!");
                }
            }

        }
    }
}
