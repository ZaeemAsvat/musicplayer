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
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.SongFeatures.TABLE_NAME + " (" +
                    DbContract.SongFeatures.COLUMN_NAME_MFCC1  + " REAL," +
                    DbContract.SongFeatures.COLUMN_NAME_MFCC2  + " REAL," +
                    DbContract.SongFeatures.COLUMN_NAME_MFCC3  + " REAL," +
                    " FOREIGN KEY (" + DbContract.SongFeatures.COLUMN_NAME_SONG_ID + ") REFERENCES " +
                    DbContract.Song.TABLE_NAME + "(" + DbContract.Song.COLUMN_NAME_ID + "))");
    }

    public long insertUserSongInteraction (SongFeatures songFeatures) {

        ContentValues values = new ContentValues();
        values.put(DbContract.SongFeatures.COLUMN_NAME_MFCC1, songFeatures.getSongFeature(SongFeature.mfcc1));
        values.put(DbContract.SongFeatures.COLUMN_NAME_MFCC2, songFeatures.getSongFeature(SongFeature.mfcc2));
        values.put(DbContract.SongFeatures.COLUMN_NAME_MFCC3, songFeatures.getSongFeature(SongFeature.mfcc3));
        values.put(DbContract.SongFeatures.COLUMN_NAME_SONG_ID, songFeatures.getSong_id());

        return db.insert(DbContract.SongFeatures.TABLE_NAME, null, values);
    }

    public SongFeatures selectUserSongInteractions (int song_id) {

        SongFeatures result = null;

        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("SELECT * FROM " + DbContract.UserSongInteraction.TABLE_NAME + " WHERE " +
                            DbContract.SongFeatures.COLUMN_NAME_SONG_ID + " = " + song_id);

        Cursor c = db.rawQuery(selectQuery.toString(), null);

        if (c != null) {

            if (c.getCount() == 1) {

                c.moveToFirst();

                Float[] featureVals = new Float[SongFeature.numSongFeatures.ordinal()];
                featureVals[0] = c.getFloat(c.getColumnIndex(DbContract.SongFeatures.COLUMN_NAME_MFCC1));
                featureVals[1] = c.getFloat(c.getColumnIndex(DbContract.SongFeatures.COLUMN_NAME_MFCC2));
                featureVals[2] = c.getFloat(c.getColumnIndex(DbContract.SongFeatures.COLUMN_NAME_MFCC3));
                result = new SongFeatures(song_id, featureVals);
            }

            c.close();
        }

        return result;
    }

    public void deleteUserSongInteraction (ArrayList<String> whereArgs) {

        StringBuilder deleteQuery = new StringBuilder();
        deleteQuery.append("DELETE FROM " + DbContract.SongFeatures.TABLE_NAME);
        for (String whereArg : whereArgs)
            deleteQuery.append(" WHERE " + whereArg + " AND ");
        deleteQuery.delete(deleteQuery.length() - 5, deleteQuery.length());

        db.execSQL(deleteQuery.toString(), null);
    }

}
