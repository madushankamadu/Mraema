package com.example.mraema.MedicineReminder.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mraema.MedicineReminder.PillsToDrink;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class MedicineDBHelper extends SQLiteOpenHelper {

    /**
     * Database name
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Database version
     */
    private static final String DATABASE_NAME = "MedicineAlarm.db";

    /**
     * Table names
     */
    private static final String PILL_TABLE = "pills";
    private static final String ALARM_TABLE = "alarms";
    private static final String PILL_ALARM_LINKS = "pill_alarm";
    private static final String HISTORIES_TABLE = "histories";

    /**
     * Common column name and location
     */
    public static final String KEY_ROWID = "id";

    /**
     * Pill table columns, used by History Table
     */
    private static final String KEY_PILLNAME = "pillName";

    /**
     * Alarm table columns, Hour & Minute used by History Table
     */
    private static final String KEY_INTENT = "intent";
    private static final String KEY_HOUR = "hour";
    private static final String KEY_MINUTE = "minute";
    private static final String KEY_EVERYDAY = "everyday";
    private static final String KEY_ALARMS_PILL_NAME = "pillName";
    private static final String KEY_DOSE_QUANTITY = "dose_quantity";
    private static final String KEY_DOSE_UNITS = "dose_units";
    private static final String KEY_ALARM_ID = "alarm_id";
    private static final String KEY_SUNDAY = "sunday";
    private static final String KEY_MONDAY = "monday";
    private static final String KEY_TUESDAY = "tuesday";
    private static final String KEY_WEDNSDAY = "wednsday";
    private static final String KEY_THURSDAY = "thursday";
    private static final String KEY_FRIDAY = "friday";
    private static final String KEY_SATERDAY = "saterday";


    /**
     * Pill-Alarm link table columns
     */
    private static final String KEY_PILLTABLE_ID = "pill_id";
    private static final String KEY_ALARMTABLE_ID = "alarm_id";

    /**
     * History Table columns, some used above
     */
    private static final String KEY_DATE_STRING = "date";
    private static final String KEY_ACTION = "action";

    /**
     * Pill Table: create statement
     */
    private static final String CREATE_PILL_TABLE =
            "create table " + PILL_TABLE + "("
                    + KEY_ROWID + " integer primary key not null,"
                    + KEY_PILLNAME + " text not null" + ")";

    /**
     * Alarm Table: create statement
     */
    private static final String CREATE_ALARM_TABLE =
            "create table " + ALARM_TABLE + "("
                    + KEY_ROWID + " integer primary key AUTOINCREMENT,"
                    + KEY_HOUR + " integer,"
                    + KEY_MINUTE + " integer,"
                    + KEY_ALARMS_PILL_NAME + " text not null,"
                    + KEY_SUNDAY + " text,"
                    + KEY_MONDAY + " text,"
                    + KEY_TUESDAY + " text,"
                    + KEY_WEDNSDAY + " text,"
                    + KEY_THURSDAY + " text,"
                    + KEY_FRIDAY + " text,"
                    + KEY_SATERDAY + " text,"
                    + KEY_DOSE_QUANTITY + " text,"
                    + KEY_DOSE_UNITS + " text,"
                    + KEY_EVERYDAY + " text" + ")";

    /**
     * Pill-Alarm link table: create statement
     */
    private static final String CREATE_PILL_ALARM_LINKS_TABLE =
            "create table " + PILL_ALARM_LINKS + "("
                    + KEY_ROWID + " integer primary key not null,"
                    + KEY_PILLTABLE_ID + " integer not null,"
                    + KEY_ALARMTABLE_ID + " integer not null" + ")";

    /**
     * Histories Table: create statement
     */
    private static final String CREATE_HISTORIES_TABLE =
            String.format("CREATE TABLE %s(%s integer primary key, %s text not null, %s text, %s text, %s text, %s integer, %s integer, %s integer , %s integer)", HISTORIES_TABLE, KEY_ROWID, KEY_PILLNAME, KEY_DOSE_QUANTITY, KEY_DOSE_UNITS, KEY_DATE_STRING, KEY_HOUR, KEY_ACTION, KEY_MINUTE, KEY_ALARM_ID);

    /**
     * Constructor
     */
    public MedicineDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    /** Creating tables */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PILL_TABLE);
        db.execSQL(CREATE_ALARM_TABLE);

        db.execSQL(CREATE_PILL_ALARM_LINKS_TABLE);
        db.execSQL(CREATE_HISTORIES_TABLE);
    }

    @Override
    // TODO: change this so that updating doesn't delete old data
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PILL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ALARM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PILL_ALARM_LINKS);
        db.execSQL("DROP TABLE IF EXISTS " + HISTORIES_TABLE);
        onCreate(db);
    }


// ############################## create methods ###################################### //


    public void addAlarms(MedicineAlarm medicineAlarm){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_HOUR,medicineAlarm.getHour());
        contentValues.put(KEY_MINUTE,medicineAlarm.getMinute());
        contentValues.put(KEY_ALARMS_PILL_NAME,medicineAlarm.getPilName());
        contentValues.put(KEY_SUNDAY,medicineAlarm.getSunday());
        contentValues.put(KEY_MONDAY,medicineAlarm.getMonday());
        contentValues.put(KEY_TUESDAY,medicineAlarm.getTuesday());
        contentValues.put(KEY_WEDNSDAY,medicineAlarm.getWednsday());
        contentValues.put(KEY_THURSDAY,medicineAlarm.getThursday());
        contentValues.put(KEY_FRIDAY,medicineAlarm.getFriday());
        contentValues.put(KEY_SATERDAY,medicineAlarm.getSaterday());
        contentValues.put(KEY_DOSE_QUANTITY,medicineAlarm.getDoseQuantity());
        contentValues.put(KEY_DOSE_UNITS,medicineAlarm.getDoseUnit());
        contentValues.put(KEY_EVERYDAY, medicineAlarm.getEveryday());


        sqLiteDatabase.insert(ALARM_TABLE,null,contentValues);
        sqLiteDatabase.close();
    }


    // ############################## get methods ###################################### //


    public List<MedicineAlarm> getAlarms(){
        List<MedicineAlarm> alarms = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ ALARM_TABLE;

        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()){
            do {
                MedicineAlarm al = new MedicineAlarm();
                al.setId(c.getInt(0));
                al.setHour(c.getInt(1));
                al.setMinute(c.getString(2));
                al.setPilName(c.getString(3));
                al.setSunday(Boolean.valueOf(c.getString(4)));
                al.setMonday(Boolean.valueOf(c.getString(5)));
                al.setTuesday(Boolean.valueOf(c.getString(6)));
                al.setWednsday(Boolean.valueOf(c.getString(7)));
                al.setThursday(Boolean.valueOf(c.getString(8)));
                al.setFriday(Boolean.valueOf(c.getString(9)));
                al.setSaterday(Boolean.valueOf(c.getString(10)));
                al.setDoseQuantity(c.getString(11));
                al.setDoseUnit(c.getString(12));
                al.setEveryday(Boolean.valueOf(c.getString(13)));

                alarms.add(al);
            }while (c.moveToNext());

        }


        return alarms;
    }


    public List<MedicineAlarm> getAlarmsWithTime(int hour,int minute) {
        List<MedicineAlarm> alarms = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + ALARM_TABLE + " WHERE " + KEY_HOUR + "=" + hour+" AND "+KEY_MINUTE+" = "+minute;
        Cursor c = db.rawQuery(query, null);

        Log.d("TAG", "getAlarmsWithTime: "+c.getCount()+ " "+c.getString(3));
        return alarms;
    }

}
