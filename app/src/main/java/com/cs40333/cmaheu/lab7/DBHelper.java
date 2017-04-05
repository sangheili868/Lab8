package com.cs40333.cmaheu.lab7;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

import java.util.ArrayList;

/**
 * Created by bchaudhr on 3/20/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public SQLiteDatabase db;
    public static String DATABASE_NAME = "team.db";
    public static int DATABASE_VERSION = 1;
    public static String TABLE_TEAM = "Team";
    public static String COL_SHORT_DATE = "short_date";
    public static String COL_LONG_DATE = "long_date";
    public static String COL_LOCATION = "location";
    public static String COL_LOGO = "logo";
    public static String COL_PLACE_NAME = "place_name";
    public static String COL_TEAM_NAME = "team_name";
    public static String COL_RECORD = "record";
    public static String COL_SCORE = "score";;
    public static String COL_TIME_LEFT = "time_left";
    public static String COL_ID = "id";

    public DBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TEAM + " ( " +
                COL_SHORT_DATE + " TEXT," +
                COL_LONG_DATE + " TEXT," +
                COL_LOCATION + " TEXT," +
                COL_LOGO + " TEXT," +
                COL_PLACE_NAME + " TEXT," +
                COL_TEAM_NAME + " TEXT," +
                COL_RECORD + " TEXT," +
                COL_SCORE + " TEXT," +
                COL_TIME_LEFT + " TEXT," +
                COL_ID + " INTEGER PRIMARY KEY )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists " + TABLE_TEAM );
        onCreate(db);
    }

    public void insertData(Team team) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SHORT_DATE, team.getShortDate());
        contentValues.put(COL_LONG_DATE, team.getLongDate());
        contentValues.put(COL_LOCATION, team.getLocation());
        contentValues.put(COL_LOGO,team.getLogo());
        contentValues.put(COL_PLACE_NAME, team.getPlaceName());
        contentValues.put(COL_TEAM_NAME, team.getTeamName());
        contentValues.put(COL_RECORD, team.getRecord());
        contentValues.put(COL_SCORE, team.getScore());
        contentValues.put(COL_TIME_LEFT, team.getTimeleft());
        contentValues.put(COL_ID, team.getId());
        long ret = db.insert(TABLE_TEAM, null, contentValues );

        if (ret > -1) {
            System.out.println("Successfully inserted");
        } else {
            System.out.println("Insert Unsuccessful");
        }

        db.close();
    }

    public void deleteData(int ID) {
        db = getWritableDatabase();
        db.delete(TABLE_TEAM, " " + COL_ID + " = ?", new String[]{Integer.toString(ID)});
        db.close();
    }


    public ArrayList<Team> getAllTeams() {
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TEAM, new String[]{});
        ArrayList<Team> teams = new ArrayList<>();

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    teams.add(new Team(
                            cursor.getString(cursor.getColumnIndex(COL_SHORT_DATE)),
                            cursor.getString(cursor.getColumnIndex(COL_LONG_DATE)),
                            cursor.getString(cursor.getColumnIndex(COL_LOCATION)),
                            cursor.getString(cursor.getColumnIndex(COL_LOGO)),
                            cursor.getString(cursor.getColumnIndex(COL_PLACE_NAME)),
                            cursor.getString(cursor.getColumnIndex(COL_TEAM_NAME)),
                            cursor.getString(cursor.getColumnIndex(COL_RECORD)),
                            cursor.getString(cursor.getColumnIndex(COL_SCORE)),
                            cursor.getString(cursor.getColumnIndex(COL_TIME_LEFT)),
                            cursor.getInt(cursor.getColumnIndex(COL_ID))

                    ));
                }while (cursor.moveToNext());
            }
        }

        cursor.close();
        return teams;
    }

    public Team getATeam(int ID) {
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TEAM + " WHERE "+COL_ID + " = ?", new String[]{Integer.toString(ID)});
        Team myTeam=null;

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    myTeam=new Team(
                            cursor.getString(cursor.getColumnIndex(COL_SHORT_DATE)),
                            cursor.getString(cursor.getColumnIndex(COL_LONG_DATE)),
                            cursor.getString(cursor.getColumnIndex(COL_LOCATION)),
                            cursor.getString(cursor.getColumnIndex(COL_LOGO)),
                            cursor.getString(cursor.getColumnIndex(COL_PLACE_NAME)),
                            cursor.getString(cursor.getColumnIndex(COL_TEAM_NAME)),
                            cursor.getString(cursor.getColumnIndex(COL_RECORD)),
                            cursor.getString(cursor.getColumnIndex(COL_SCORE)),
                            cursor.getString(cursor.getColumnIndex(COL_TIME_LEFT)),
                            cursor.getInt(cursor.getColumnIndex(COL_ID))

                    );
                }while (cursor.moveToNext());
            }
        }

        cursor.close();
        if(myTeam == null ) throw new IndexOutOfBoundsException();
        else return myTeam;
    }
}