package com.bakraqom.pointofinterest.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bakraqom.pointofinterest.DatabaseHandler;
import com.bakraqom.pointofinterest.GpsTracker;
import com.bakraqom.pointofinterest.PointOfInterest;
import com.bakraqom.pointofinterest.R;

import junit.framework.Test;

/**
 * Created by saad on 10/24/15.
 */
public class MainFragment extends android.support.v4.app.Fragment {

    Button getLocationButton;
    static Context context;
    static Dialog poiDialog;

    TextView name_et;
    Spinner category_sp;
    static ProgressBar add_progress;
    static Button addBtn;

    GpsTracker gps;

    static public double lat, lon;
    static DatabaseHandler db;
    static PointOfInterest currentPOI;


    static public void saveResult(){
        if (db != null){
            currentPOI.setLat(lat);
            currentPOI.setLon(lon);
            Log.i("Database", "Adding POI");
            db.addPOI(currentPOI);
            addBtn.setVisibility(View.VISIBLE);
            add_progress.setVisibility(View.INVISIBLE);
            poiDialog.hide();
            Toast.makeText(context, "POI Added", Toast.LENGTH_SHORT).show();
        }
        else{
            db = new DatabaseHandler(context);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.create_tab_layout, container, false);

        context = rootView.getContext();

        db = new DatabaseHandler(context);
        poiDialog = new Dialog(context);

        Display dm = getActivity().getWindowManager().getDefaultDisplay();
        final int width = dm.getWidth();
        final int height = dm.getHeight();
        ;

        getLocationButton = (Button)rootView.findViewById(R.id.btn_get_location);

        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                gps = new GpsTracker(context);

                currentPOI = new PointOfInterest();

                poiDialog = new Dialog(context);
                poiDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                poiDialog.getWindow().setLayout(width, height/2);
                poiDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                poiDialog.setContentView(R.layout.add_poi_dialog);

                name_et = (EditText) poiDialog.findViewById(R.id.et_name_of_place);
                category_sp = (Spinner) poiDialog.findViewById(R.id.spinner_category);

                add_progress = (ProgressBar) poiDialog.findViewById(R.id.add_poi_progress);
                add_progress.setVisibility(View.INVISIBLE);

                addBtn = (Button) poiDialog.findViewById(R.id.add_poi_btn);


                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentPOI.setCategory(category_sp.getSelectedItem().toString());
                        currentPOI.setNameOfPlace(name_et.getText().toString());
                        gps = new GpsTracker(context);

                        if (!gps.canGetLocation()){
                            gps.showSettingsAlert();
                        }
                        else {
                            add_progress.setVisibility(View.VISIBLE);
                            addBtn.setVisibility(View.INVISIBLE);
                        }
                    }
                });

                poiDialog.setCancelable(true);
                poiDialog.show();



            }

        });

        return rootView;

    }
}
