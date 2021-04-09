package com.example.moviemania;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieManiaDBHelper extends SQLiteOpenHelper {
    private static final String DBName = "MovieMania.db";
    private static final String TABLE1 = "RegisterMovieTable";

    private static final String TABLE1_COL1 = "TITLE";
    private static final String TABLE1_COL2 = "YEAR";
    private static final String TABLE1_COL3 = "CASTOFMOVIE";
    private static final String TABLE1_COL4 = "DIRECTOR";
    private static final String TABLE1_COL5 = "RATINGS";
    private static final String TABLE1_COL6 = "REVIEW";

    public MovieManiaDBHelper(Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE1 + "(TITLE TEXT PRIMARY KEY, YEAR INTEGER, CASTOFMOVIE TEXT, DIRECTOR TEXT, RATINGS INTEGER, REVIEW TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //Registering a movie to the Database.
    public boolean registerMovie(String title, int year, String castOfMovie, String director, int ratings, String review){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE1_COL1,title);
        contentValues.put(TABLE1_COL2,year);
        contentValues.put(TABLE1_COL3,castOfMovie);
        contentValues.put(TABLE1_COL4,director);
        contentValues.put(TABLE1_COL5,ratings);
        contentValues.put(TABLE1_COL6,review);
        long result = db.insert(TABLE1,null,contentValues);
        return result != -1;
    }

    //To get all the columns in word table
    public Cursor getMovieTitle(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select TITLE from " + TABLE1 + " order by " + TABLE1_COL1,null);
        return cursor;
    }
}
