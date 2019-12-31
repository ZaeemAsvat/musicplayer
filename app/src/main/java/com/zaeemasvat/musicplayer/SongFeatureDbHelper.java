package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class SongFeatureDbHelper {

    private SQLiteDatabase db;

    SongFeatureDbHelper (SQLiteDatabase db) { this.db = db;}

    public void createTable () {

        StringBuilder createTableBuilderStr = new StringBuilder();
        createTableBuilderStr.append("CREATE TABLE IF NOT EXISTS " + DbContract.SongFeatures.TABLE_NAME + " (");
        for (int i = 0; i < 40; i++)
            createTableBuilderStr.append(DbContract.SongFeatures.COLUMN_NAME_MFCC + "_" + i + " REAL,");
        createTableBuilderStr.append(DbContract.SongFeatures.COLUMN_NAME_SONG_ID + " INTEGER,");
        createTableBuilderStr.append(" FOREIGN KEY (" + DbContract.SongFeatures.COLUMN_NAME_SONG_ID + ") REFERENCES " +
                    DbContract.Song.TABLE_NAME + "(" + DbContract.Song.COLUMN_NAME_ID + "))");

        db.execSQL(createTableBuilderStr.toString());
    }

    public long insertSongFeatures (SongFeatures songFeatures) {

        ContentValues values = new ContentValues();
        for (int i = 0; i < songFeatures.getSongFeatures().length; i++)
            values.put(DbContract.SongFeatures.COLUMN_NAME_MFCC + "_" + i, songFeatures.getSongFeatures()[i]);

        return db.insert(DbContract.SongFeatures.TABLE_NAME, null, values);
    }

    public SongFeatures selectSongFeatures (long song_id) {

        SongFeatures result = null;

        String selectQuery = "SELECT * FROM " + DbContract.SongFeatures.TABLE_NAME + " WHERE " +
                            DbContract.SongFeatures.COLUMN_NAME_SONG_ID + " = " + song_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            if (c.getCount() == 1) {

                c.moveToFirst();
                float[] featureVals = new float[c.getColumnCount() - 1];
                for (int i = 0; i < featureVals.length; i++)
                    featureVals[i] = c.getFloat(c.getColumnIndex(DbContract.SongFeatures.COLUMN_NAME_MFCC + "_" + i));

                result = new SongFeatures(song_id, featureVals);
            }

            c.close();
        }

        return result;
    }

    public ArrayList<SongFeatures> selectAllSongFeatures () {

        ArrayList<SongFeatures> result = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DbContract.SongFeatures.TABLE_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount() > 0) {

            while (c.moveToNext()) {
                float[] featureVals = new float[c.getColumnCount() - 1];
                for (int i = 0; i < featureVals.length; i++)
                    featureVals[i] = c.getFloat(c.getColumnIndex(DbContract.SongFeatures.COLUMN_NAME_MFCC + "_" + i));

                result.add(new SongFeatures(c.getLong(c.getColumnIndex(DbContract.SongFeatures.COLUMN_NAME_SONG_ID)), featureVals));
            }

            c.close();
        }

        return result;
    }

    public void deleteSongFeatures (ArrayList<String> whereArgs) {

        StringBuilder deleteQuery = new StringBuilder();
        deleteQuery.append("DELETE FROM " + DbContract.SongFeatures.TABLE_NAME);
        for (String whereArg : whereArgs)
            deleteQuery.append(" WHERE " + whereArg + " AND ");
        deleteQuery.delete(deleteQuery.length() - 5, deleteQuery.length());

        db.execSQL(deleteQuery.toString(), null);
    }

    public void dropTable () {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.SongFeatures.TABLE_NAME);
    }

}
