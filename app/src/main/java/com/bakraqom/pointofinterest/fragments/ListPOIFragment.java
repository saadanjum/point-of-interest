package com.bakraqom.pointofinterest.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bakraqom.pointofinterest.DatabaseHandler;
import com.bakraqom.pointofinterest.PointOfInterest;
import com.bakraqom.pointofinterest.R;
import com.bakraqom.pointofinterest.adapters.CustomPOIAdapter;

import java.util.List;

/**
 * Created by saad on 10/25/15.
 */
public class ListPOIFragment extends Fragment {


    static CustomPOIAdapter adapter;
    static ListView poi_lv;
    static List<PointOfInterest> POIList;
    static DatabaseHandler db;
    static Activity act;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_poi_layout, container, false);

        db = new DatabaseHandler(getContext());

        POIList = db.getAllPoints();
        act = getActivity();

        poi_lv = (ListView) rootView.findViewById(R.id.poi_listview);
        adapter = new CustomPOIAdapter(getActivity(), POIList);
        poi_lv.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        POIList = db.getAllPoints();
        adapter = new CustomPOIAdapter(getActivity(), POIList);
        poi_lv.setAdapter(adapter);
        poi_lv.invalidateViews();
    }

    static public void refreshPage(){
        POIList = db.getAllPoints();
        adapter = new CustomPOIAdapter(act, POIList);
        poi_lv.setAdapter(adapter);
        poi_lv.invalidateViews();
    }
}
