package com.zaeemasvat.musicplayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "music_player.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.Song.TABLE_NAME + " (" +
                DbContract.Song.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                DbContract.Song.COLUMN_NAME_TITLE  + " TEXT," +
                DbContract.Song.COLUMN_NAME_ARTIST + " TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.UserSongInteraction.TABLE_NAME + " (" +
                DbContract.UserSongInteraction.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION + " TEXT," +
                DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID  + " INTEGER," +
                " FOREIGN KEY (" + DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID + ") REFERENCES " +
                    DbContract.Song.TABLE_NAME + "(" + DbContract.Song.COLUMN_NAME_ID + "))");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.Song.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.UserSongInteraction.TABLE_NAME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
