package co.navdeep.weatherapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import co.navdeep.weatherapp.R;
import co.navdeep.weatherapp.abstractActivities.WeatherAppActivity;

/**
 * Created by Navdeep on 6/13/2015.
 * http://www.navdeep.co
 */
public class DetailActivity extends WeatherAppActivity {

    private ShareActionProvider shareProvider;
    private DetailActivityFragment mDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            mDetailFragment = new DetailActivityFragment();

            if(getIntent() != null && getIntent().getData() != null){
                Bundle args = new Bundle();
                args.putParcelable(DetailActivityFragment.ARG_URI_KEY, getIntent().getData());
                mDetailFragment.setArguments(args);
            }

            getSupportFragmentManager().beginTransaction().add(R.id.weather_detail_container, mDetailFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        shareProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (mDetailFragment != null && mDetailFragment.currentText != null) {
            shareProvider.setShareIntent(createShareForecastIntent());
        }
        return true;
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                mDetailFragment.currentText);
        return shareIntent;
    }
}
