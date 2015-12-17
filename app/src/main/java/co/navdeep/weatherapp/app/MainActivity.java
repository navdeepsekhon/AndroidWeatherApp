package co.navdeep.weatherapp.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import co.navdeep.weatherapp.R;
import co.navdeep.weatherapp.abstractActivities.WeatherAppActivity;
import co.navdeep.weatherapp.sync.WeatherAppSyncAdapter;
import co.navdeep.weatherapp.utils.Utilities;

/**
 * Created by Navdeep on 6/13/2015.
 * http://www.navdeep.co
 */
public class MainActivity extends WeatherAppActivity implements MainActivityFragment.Callback{

    String mLocation;
    String mUnits;
    boolean mTwoPane = false;

    private final String DETAIL_FRAGMENT_TAG = "DETAIL_FRAGMENT_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utilities.setUserPreferences(this);
        mLocation = Utilities.getPreferredLocation();
        mUnits = Utilities.getPreferredUnits();

        if (findViewById(R.id.weather_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.weather_detail_container, new DetailActivityFragment(), DETAIL_FRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }

        MainActivityFragment ff = (MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        ff.setmTwoPane(mTwoPane);
        WeatherAppSyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utilities.setUserPreferences(this);
        String location = Utilities.getPreferredLocation();
        String units = Utilities.getPreferredUnits();
        // update the location in our second pane using the fragment manager
        if (location != null && !location.equals(mLocation)) {
            MainActivityFragment ff = (MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_main);
            if ( null != ff ) {
             ff.onLocationChanged();
            }
             mLocation = location;
        } else if(units != null && !units.equals(mUnits)){
            MainActivityFragment ff = (MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_main);
            if ( null != ff ) {
                ff.onUnitChanged();
            }
            mUnits = units;
        }


    }

    @Override
    public void onItemSelected(Uri dataUri) {
        if(!mTwoPane){
            Intent details = new Intent(this, DetailActivity.class)
                    .setData(dataUri);
            startActivity(details);
        } else{
            DetailActivityFragment detailActivityFragment = new DetailActivityFragment();
            Bundle args = new Bundle();
            args.putParcelable(DetailActivityFragment.ARG_URI_KEY, dataUri);
            detailActivityFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.weather_detail_container, detailActivityFragment, DETAIL_FRAGMENT_TAG)
                    .commit();
        }
    }

}
