package co.navdeep.weatherapp.abstractActivities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import co.navdeep.weatherapp.R;
import co.navdeep.weatherapp.settings.SettingsActivity;

/**
 * Created by Navdeep on 6/13/2015.
 * http://www.navdeep.co
 */
public abstract class WeatherAppActivity extends ActionBarActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent settings = new Intent(this,SettingsActivity.class);
            startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
