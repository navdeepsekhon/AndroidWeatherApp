package co.navdeep.weatherapp.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import co.navdeep.weatherapp.R;

/**
 * Created by Navdeep Sekhon on 12/15/2015.
 * www.navdeep.co
 */
public class ListItemViewHolder {
    public final ImageView iconView;
    public final TextView dateView;
    public final TextView descriptionView;
    public final TextView highTempView;
    public final TextView lowTempView;

    public ListItemViewHolder(View view){
        iconView = (ImageView) view.findViewById(R.id.list_item_imageview);
        dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
        descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
        highTempView = (TextView) view.findViewById(R.id.list_item_high_textview);
        lowTempView = (TextView) view.findViewById(R.id.list_item_low_textview);
    }
}
