package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SongShuffleRelationDbHelper {

    private SQLiteDatabase db;

    SongShuffleRelationDbHelper(SQLiteDatabase db) { this.db = db; }

    public void createTable () {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.SongShuffleRelation.TABLE_NAME + " (" +
                DbContract.SongShuffleRelation.COLUMN_NAME_SONG_ID  + " INTEGER," +
                DbContract.SongShuffleRelation.COLUMN_NAME_SHUFFLE_ORDER_NUM + " INTEGER," +
                " FOREIGN KEY (" + DbContract.SongShuffleRelation.COLUMN_NAME_SONG_ID + ") REFERENCES " +
                DbContract.Song.TABLE_NAME + "(" + DbContract.Song.COLUMN_NAME_ID + "))");
    }

    public void insertSongShuffleRelation (SongShuffleRelation songShuffleRelation) {

        ContentValues values = new ContentValues();
        values.put(DbContract.SongShuffleRelation.COLUMN_NAME_SONG_ID, songShuffleRelation.getSongId());
        values.put(DbContract.SongShuffleRelation.COLUMN_NAME_SHUFFLE_ORDER_NUM, songShuffleRelation.getShuffleOrderNum());

        db.insert(DbContract.SongShuffleRelation.TABLE_NAME, null, values);
    }

    public SongShuffleRelation selectSongShuffleRelation (String whereArgs) {

        SongShuffleRelation result = null;

        String selectQuery = "SELECT * FROM " + DbContract.SongShuffleRelation.TABLE_NAME + " " + whereArgs;

        Cursor c = db.rawQuery(selectQuery.toString(), null);

        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                result = new SongShuffleRelation(c.getLong(c.getColumnIndex(DbContract.SongShuffleRelation.COLUMN_NAME_SONG_ID)),
                        c.getInt(c.getColumnIndex(DbContract.SongShuffleRelation.COLUMN_NAME_SHUFFLE_ORDER_NUM)));
            }
            c.close();
        }

        return result;
    }

    public ArrayList<SongShuffleRelation> selectSongShuffleRelations (String whereArgs) {

        ArrayList<SongShuffleRelation> result = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DbContract.SongShuffleRelation.TABLE_NAME + " " + whereArgs;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            if (c.getCount() > 0)
                while (c.moveToNext())
                    result.add(new SongShuffleRelation(c.getLong(c.getColumnIndex(DbContract.SongShuffleRelation.COLUMN_NAME_SONG_ID)),
                            c.getInt(c.getColumnIndex(DbContract.SongShuffleRelation.COLUMN_NAME_SHUFFLE_ORDER_NUM))));
            c.close();
        }

        return result;
    }

    public void deleteSongShuffleRelations (String whereArgs) {

        String deleteQuery = "DELETE * FROM " + DbContract.SongShuffleRelation.TABLE_NAME + " " + whereArgs;
        db.execSQL(deleteQuery);
    }

    public void deleteSongShuffleRelations (long song_id) {
        deleteSongShuffleRelations("WHERE " + DbContract.SongShuffleRelation.COLUMN_NAME_SONG_ID + " = " + song_id);
    }

    public void dropTable() {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.SongShuffleRelation.TABLE_NAME);
    }
}