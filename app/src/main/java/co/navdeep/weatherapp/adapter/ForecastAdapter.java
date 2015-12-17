package co.navdeep.weatherapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import co.navdeep.weatherapp.R;
import co.navdeep.weatherapp.data.WeatherRow;
import co.navdeep.weatherapp.utils.DbUtilities;
import co.navdeep.weatherapp.utils.ListItemViewHolder;
import co.navdeep.weatherapp.utils.Utilities;

/**
 * Created by Navdeep Sekhon on 8/17/2015.
 * www.navdeep.co
 */
public class ForecastAdapter extends CursorAdapter {
    private final int VIEW_TYPE_TODAY = 0;
    private final int VIEW_TYPE_FUTURE = 1;
    private boolean mTwoPane = false;

    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
        View view = null;

        if(viewType == VIEW_TYPE_TODAY)
            view = LayoutInflater.from(context).inflate(R.layout.list_item_forecast_today, parent, false);
        else
            view = LayoutInflater.from(context).inflate(R.layout.list_item_forecast, parent, false);

        ListItemViewHolder viewHolder = new ListItemViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ListItemViewHolder viewHolder = (ListItemViewHolder)view.getTag();

        WeatherRow row = DbUtilities.cursorRowToWeatherRow(cursor);
        viewHolder.iconView.setImageResource(Utilities.getWeatherIconIdFromDesc(row.getDesc()));
        viewHolder.dateView.setText(row.getReadableDate(mTwoPane));
        viewHolder.descriptionView.setText(row.getDesc());
        viewHolder.highTempView.setText(row.getMaxStringInUserPreferredUnits(context));
        viewHolder.lowTempView.setText(row.getMinStringInUserPrefferedUnits(context));
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && !mTwoPane) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE;
    }

    public void setmTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
    }
}
