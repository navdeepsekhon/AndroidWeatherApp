package co.navdeep.weatherapp.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Navdeep Sekhon on 12/16/2015.
 * www.navdeep.co
 */
public class WeatherAppSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static WeatherAppSyncAdapter sWeatherAppSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("SunshineSyncService", "onCreate - SunshineSyncService");
        synchronized (sSyncAdapterLock) {
            if (sWeatherAppSyncAdapter == null) {
                sWeatherAppSyncAdapter = new WeatherAppSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sWeatherAppSyncAdapter.getSyncAdapterBinder();
    }
}
