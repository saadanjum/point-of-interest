package com.bakraqom.pointofinterest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saad on 10/24/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "poi";

    private static final String POINTS_TABLE = "points";
    // Points columns
    private static final String KEY_lat = "lat";
    private static final String KEY_lon = "lon";
    private static final String KEY_place = "place_name";
    private static final String KEY_category = "category";
    private static final String KEY_id = "id";



    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_POINTS_TABLE = "CREATE TABLE points ("+KEY_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_lat+" REAL, "+KEY_lon+" REAL, "+KEY_place+" TEXT, "+KEY_category+" TEXT)";
        db.execSQL(CREATE_POINTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addPOI(PointOfInterest poi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_lat, poi.getLat());
        values.put(KEY_lon, poi.getLon());
        values.put(KEY_place, poi.getNameOfPlace());
        values.put(KEY_category, poi.getCategory());

        // Inserting Row
        db.insert(POINTS_TABLE, null, values);
        db.close(); // Closing database connection
    }


    // Getting single POI
    public PointOfInterest getPOI(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(POINTS_TABLE, new String[] { KEY_id,
                        KEY_lat, KEY_lon, KEY_place, KEY_category }, KEY_id + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        PointOfInterest poi = new PointOfInterest(cursor.getDouble(1), cursor.getDouble(2),
                cursor.getString(3), cursor.getString(4));
        // return contact
        return poi;
    }

    // Getting All POIs
    public List<PointOfInterest> getAllPoints() {
        List<PointOfInterest> pointsList = new ArrayList<PointOfInterest>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + POINTS_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PointOfInterest poi = new PointOfInterest();
                poi.setId(cursor.getInt(0));
                poi.setLat(cursor.getDouble(1));
                poi.setLon(cursor.getDouble(2));
                poi.setNameOfPlace(cursor.getString(3));
                poi.setCategory(cursor.getString(4));
                // Adding contact to list
                pointsList.add(poi);
            } while (cursor.moveToNext());
        }

        // return contact list
        return pointsList;
    }


}
