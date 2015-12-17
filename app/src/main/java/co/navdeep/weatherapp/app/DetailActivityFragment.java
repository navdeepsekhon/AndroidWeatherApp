package co.navdeep.weatherapp.app;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.navdeep.weatherapp.R;
import co.navdeep.weatherapp.data.WeatherProjection;
import co.navdeep.weatherapp.data.WeatherRow;
import co.navdeep.weatherapp.utils.DbUtilities;
import co.navdeep.weatherapp.utils.Utilities;
import co.navdeep.weatherapp.utils.Wind;

/**
 * Created by Navdeep on 6/13/2015.
 * http://www.navdeep.co
 */
public class DetailActivityFragment extends Fragment {

    private static final int DETAIL_LOADER = 0;
    public static final String ARG_URI_KEY = "uri";

    private Uri mUri = null;
    public String currentText;

    private static TextView mDayTextView;
    private static TextView mDateTextView;
    private static TextView mDescTextView;
    private static TextView mHighTextView;
    private static TextView mLowTextView;
    private static TextView mHumidityTextView;
    private static TextView mPressureTextView;
    private static TextView mWindTextView;
    private static ImageView mImageView;

    public DetailActivityFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(DETAIL_LOADER, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                if (mUri == null){
                    return null;
                }
                return new CursorLoader(getActivity(), mUri, WeatherProjection.FORECAST_COLUMNS, null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                if(!data.moveToFirst())
                    return;
                WeatherRow row = DbUtilities.cursorRowToWeatherRow(data);
                currentText = row.toString();
                mDayTextView.setText(Utilities.getDayOfTheWeek(row.getDate()));
                mDateTextView.setText(Utilities.getMonthAndDate(row.getDate()));
                mDescTextView.setText(row.getDesc());
                mHighTextView.setText(row.getMaxStringInUserPreferredUnits(getActivity()));
                mLowTextView.setText(row.getMinStringInUserPrefferedUnits(getActivity()));
                mHumidityTextView.setText(getActivity().getString(R.string.format_humidity, row.getHumidity()));
                mPressureTextView.setText(getActivity().getString(R.string.format_pressure, row.getPressure()));
                mWindTextView.setText(Wind.getFormattedWind(getActivity(), row.getWindSpeed(), row.getWindDirection()));
                mImageView.setImageResource(Utilities.getWeatherIconIdFromDesc(row.getDesc()));
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if(arguments != null && arguments.containsKey(ARG_URI_KEY))
            mUri = arguments.getParcelable(ARG_URI_KEY);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        extractViewReferences(view);
        return view;
    }

    private void extractViewReferences(View view){
        mDayTextView = (TextView)view.findViewById(R.id.detail_day_textview);
        mDateTextView = (TextView)view.findViewById(R.id.detail_date_textview);
        mDescTextView = (TextView)view.findViewById(R.id.detail_desc_textview);
        mHighTextView = (TextView)view.findViewById(R.id.detail_high_textview);
        mLowTextView = (TextView)view.findViewById(R.id.detail_low_textview);
        mHumidityTextView = (TextView)view.findViewById(R.id.detail_humidity_textview);
        mPressureTextView = (TextView)view.findViewById(R.id.detail_pressure_textview);
        mWindTextView = (TextView)view.findViewById(R.id.detail_wind_textview);
        mImageView = (ImageView)view.findViewById(R.id.detail_imageview);
    }


}
