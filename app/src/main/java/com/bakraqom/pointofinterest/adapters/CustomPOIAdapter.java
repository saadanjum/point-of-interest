package com.bakraqom.pointofinterest.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bakraqom.pointofinterest.PointOfInterest;
import com.bakraqom.pointofinterest.R;

import java.util.List;

/**
 * Created by saad on 10/25/15.
 */
public class CustomPOIAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<PointOfInterest> POIList;

    public CustomPOIAdapter(Activity activity, List<PointOfInterest> POIs ){
        this.POIList = POIs;
        this.activity = activity;

    }

    @Override
    public int getCount() {
        return POIList.size();
    }

    @Override
    public Object getItem(int position) {
        return POIList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.poi_list_row, null);

        TextView name = (TextView) convertView.findViewById(R.id.poi_listview_head);
        TextView latlon = (TextView) convertView.findViewById(R.id.poi_listview_category);
        Button viewBtn = (Button) convertView.findViewById(R.id.poi_listview_btn);

        final PointOfInterest poi = POIList.get(position);

        name.setText(poi.getNameOfPlace());
        latlon.setText(poi.getCategory());

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:"+String.valueOf(poi.getLat())+","+String.valueOf(poi.getLon())+"?z=17&q="+String.valueOf(poi.getLat())+","+String.valueOf(poi.getLon())+"("+poi.getNameOfPlace().replace(" ", "+")+")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                activity.startActivity(mapIntent);
            }
        });

        return convertView;
    }
}
