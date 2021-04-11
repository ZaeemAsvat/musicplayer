package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class SongFeatureDbHelper extends DbHelperBase<SongFeatures> {

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

    @Override
    public void insert(SongFeatures entry) {
        ContentValues values = new ContentValues();
        for (int i = 0; i < entry.getSongFeatures().length; i++)
            values.put(DbContract.SongFeatures.COLUMN_NAME_MFCC + "_" + i, entry.getSongFeatures()[i]);

        db.insert(DbContract.SongFeatures.TABLE_NAME, null, values);
    }

    @Override
    public ArrayList<SongFeatures> select(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        ArrayList<SongFeatures> result = new ArrayList<>();

        Cursor c = db.query(DbContract.SongFeatures.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        if (c != null) {

            if (c.getCount() > 0) {

                while (c.moveToNext()) {
                    long song_id = c.getLong(c.getColumnIndex(DbContract.SongFeatures.COLUMN_NAME_SONG_ID));
                    float[] featureVals = new float[c.getColumnCount() - 1];
                    for (int i = 0; i < featureVals.length; i++)
                        featureVals[i] = c.getFloat(c.getColumnIndex(DbContract.SongFeatures.COLUMN_NAME_MFCC + "_" + i));

                    result.add(new SongFeatures(song_id, featureVals));
                }
            }

            c.close();
        }

        return result;
    }

    @Override
    public SongFeatures selectFirst(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        SongFeatures songFeatures = null;
        ArrayList<SongFeatures> songFeaturesList = select(columns, selection, selectionArgs, groupBy, having, orderBy);
        if (!songFeaturesList.isEmpty())
            songFeatures = songFeaturesList.get(0);
        return songFeatures;
    }

    @Override
    public void update(SongFeatures entry, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();
        for (int i = 0; i < entry.getSongFeatures().length; i++)
            values.put(DbContract.SongFeatures.COLUMN_NAME_MFCC + "_" + i, entry.getSongFeatures()[i]);

        db.update(DbContract.SongFeatures.TABLE_NAME, values, whereClause, whereArgs);
    }

    @Override
    public void delete(String whereClause, String[] whereArgs) {
        db.delete(DbContract.SongFeatures.TABLE_NAME, whereClause, whereArgs);
    }

    public void dropTable () {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.SongFeatures.TABLE_NAME);
    }

}
