package co.navdeep.weatherapp.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Navdeep Sekhon on 12/16/2015.
 * www.navdeep.co
 */
public class WeatherAppAuthenticatorService extends Service {

    // Instance field that stores the authenticator object
    private WeatherAppAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new WeatherAppAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
