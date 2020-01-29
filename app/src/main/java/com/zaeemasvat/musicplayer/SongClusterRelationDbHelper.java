package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SongClusterRelationDbHelper {

    private SQLiteDatabase db;

    SongClusterRelationDbHelper (SQLiteDatabase db) {
        this.db = db;
    }

    public void createTable () {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.SongClusterRelation.TABLE_NAME + " (" +
                DbContract.SongClusterRelation.COLUMN_NAME_SONG_ID  + " INTEGER," +
                DbContract.SongClusterRelation.COLUMN_NAME_CLUSTER_NUM + " INTEGER," +
                " FOREIGN KEY (" + DbContract.SongClusterRelation.COLUMN_NAME_SONG_ID + ") REFERENCES " +
                DbContract.Song.TABLE_NAME + "(" + DbContract.Song.COLUMN_NAME_ID + "))");
    }

    public void insertSongClusterRelation (SongClusterRelation songClusterRelation) {

        ContentValues values = new ContentValues();
        values.put(DbContract.SongClusterRelation.COLUMN_NAME_SONG_ID, songClusterRelation.getSongId());
        values.put(DbContract.SongClusterRelation.COLUMN_NAME_CLUSTER_NUM, songClusterRelation.getClusterNum());

        db.insert(DbContract.SongClusterRelation.TABLE_NAME, null, values);
    }

    public SongClusterRelation selectSongClusterRelation (String whereArgs) {

        SongClusterRelation result = null;

        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("SELECT * FROM " + DbContract.SongClusterRelation.TABLE_NAME).append(" " + whereArgs);

        Cursor c = db.rawQuery(selectQuery.toString(), null);

        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                result = new SongClusterRelation(c.getLong(c.getColumnIndex(DbContract.SongClusterRelation.COLUMN_NAME_SONG_ID)),
                        c.getInt(c.getColumnIndex(DbContract.SongClusterRelation.COLUMN_NAME_CLUSTER_NUM)));
            }
            c.close();
        }

        return result;
    }

    public SongClusterRelation selectSongClusterRelation (int cluster_num) {

        return selectSongClusterRelation("WHERE " + DbContract.SongClusterRelation.COLUMN_NAME_CLUSTER_NUM + " = " + cluster_num);
    }

    public ArrayList<SongClusterRelation> selectSongClusterRelations (String whereArgs) {

        ArrayList<SongClusterRelation> result = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DbContract.SongClusterRelation.TABLE_NAME + " " + whereArgs;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            if (c.getCount() > 0)
                while (c.moveToNext())
                    result.add(new SongClusterRelation(c.getLong(c.getColumnIndex(DbContract.SongClusterRelation.COLUMN_NAME_SONG_ID)),
                            c.getInt(c.getColumnIndex(DbContract.SongClusterRelation.COLUMN_NAME_CLUSTER_NUM))));
            c.close();
        }

        return result;
    }

    public void deleteSongClusterRelations (String whereArgs) {

        String deleteQuery = "DELETE * FROM " + DbContract.SongClusterRelation.TABLE_NAME + " " + whereArgs;
        db.execSQL(deleteQuery);
    }

    public void deleteSongClusterRelations (long song_id) {
        deleteSongClusterRelations("WHERE " + DbContract.SongClusterRelation.COLUMN_NAME_SONG_ID + " = " + song_id);
    }

    public void dropTable() {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.SongClusterRelation.TABLE_NAME);
    }

}
