package com.example.travelreminder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String TRIP = "trip";
    public static final String USER = "user";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String CITY_FROM = "cityFrom";
    public static final String CITY_TO = "cityTo";
    public static final String STATUS = "status";
    public static final String OPERATION = "operation";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String IMAGE = "image";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "trip", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createtrip = String.format("create table %s (%s text, %s text, %s text, %s text, %s text, %s text, %s text)",
                TRIP, ID, NAME, DATE, TIME, CITY_FROM, CITY_TO, STATUS);
        String createUser =  String.format("create table %s (%s text, %s text, %s text, %s BLOB)",
                USER, USERNAME, EMAIL, PHONE, IMAGE);
        sqLiteDatabase.execSQL(createtrip);
        sqLiteDatabase.execSQL(createUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
