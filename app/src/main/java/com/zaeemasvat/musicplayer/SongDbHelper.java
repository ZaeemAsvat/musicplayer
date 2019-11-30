package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class SongDbHelper {

    private SQLiteDatabase db;

    SongDbHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public void createTable () {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.Song.TABLE_NAME + " (" +
                DbContract.Song.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                DbContract.Song.COLUMN_NAME_TITLE  + " TEXT," +
                DbContract.Song.COLUMN_NAME_ARTIST + " TEXT," +
                DbContract.Song.COLUMN_NAME_PATH + " TEXT)");
    }

    public void insertSong(Song song) {

        ContentValues values = new ContentValues();
        values.put(DbContract.Song.COLUMN_NAME_ID, song.getId());
        values.put(DbContract.Song.COLUMN_NAME_TITLE, song.getTitle());
        values.put(DbContract.Song.COLUMN_NAME_ARTIST, song.getArtist());
        values.put(DbContract.Song.COLUMN_NAME_PATH, song.getPath());

        db.insert(DbContract.Song.TABLE_NAME, null, values);
    }

    public ArrayList<Song> selectSongs (ArrayList<String> whereArgs) {

        ArrayList<Song> result = new ArrayList<>();

        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("SELECT * FROM " + DbContract.Song.TABLE_NAME);
        if (whereArgs != null) {
            for (String whereArg : whereArgs)
                selectQuery.append(" WHERE " + whereArg + " AND ");
            selectQuery.delete(selectQuery.length() - 5, selectQuery.length());
        }

        Cursor c = db.rawQuery(selectQuery.toString(), null);

        if (c != null) {
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    result.add(new Song(c.getLong(c.getColumnIndex(DbContract.Song.COLUMN_NAME_ID)),
                            c.getString(c.getColumnIndex(DbContract.Song.COLUMN_NAME_TITLE)),
                            c.getString(c.getColumnIndex(DbContract.Song.COLUMN_NAME_ARTIST)),
                            c.getString(c.getColumnIndex(DbContract.Song.COLUMN_NAME_PATH))));
                }
            }
            c.close();
        }

        return result;
    }

    public Song selectSong (ArrayList<String> whereArgs) {

        Song result = null;

        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("SELECT * FROM " + DbContract.Song.TABLE_NAME);
        for (String whereArg : whereArgs)
            selectQuery.append(" WHERE " + whereArg + " AND ");
        selectQuery.delete(selectQuery.length() - 5, selectQuery.length());

        Cursor c = db.rawQuery(selectQuery.toString(), null);

        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                result = new Song(c.getLong(c.getColumnIndex(DbContract.Song.COLUMN_NAME_ID)),
                        c.getString(c.getColumnIndex(DbContract.Song.COLUMN_NAME_TITLE)),
                        c.getString(c.getColumnIndex(DbContract.Song.COLUMN_NAME_ARTIST)),
                        c.getString(c.getColumnIndex(DbContract.Song.COLUMN_NAME_PATH)));
            }
            c.close();
        }

        return result;
    }

    public void deleteSong (ArrayList<String> whereArgs) {

        StringBuilder deleteQuery = new StringBuilder();
        deleteQuery.append("DELETE FROM " + DbContract.Song.TABLE_NAME);
        if (whereArgs != null) {
            for (String whereArg : whereArgs)
                deleteQuery.append(" WHERE " + whereArg + " AND ");
            deleteQuery.delete(deleteQuery.length() - 5, deleteQuery.length());
        }

        db.execSQL(deleteQuery.toString());
    }

    public void updateSong (Song song, ArrayList<String> whereArgs) {

        StringBuilder updateQuery = new StringBuilder();
        updateQuery.append("UPDATE " + DbContract.Song.TABLE_NAME
                            + " SET " + DbContract.Song.COLUMN_NAME_TITLE + " = " + song.getTitle()
                                 + ", " + DbContract.Song.COLUMN_NAME_ARTIST + " = " + song.getArtist());
        for (String whereArg : whereArgs)
            updateQuery.append(" WHERE " + whereArg + " AND ");
        updateQuery.delete(updateQuery.length() - 5, updateQuery.length());

        db.execSQL(updateQuery.toString(), null);
    }

    public void dropTable() {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.Song.TABLE_NAME);
    }


}
