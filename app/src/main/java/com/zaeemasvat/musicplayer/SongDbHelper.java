package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class SongDbHelper extends DbHelperBase<Song> {

    private SQLiteDatabase db;

    SongDbHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public void createTable () {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.Song.TABLE_NAME + " (" +
                DbContract.Song.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                DbContract.Song.COLUMN_NAME_TITLE  + " TEXT," +
                DbContract.Song.COLUMN_NAME_ARTIST_ID + " INTEGER," +
                DbContract.Song.COLUMN_NAME_PATH + " TEXT)");
    }

    @Override
    public void insert(Song entry) {

        ContentValues values = new ContentValues();
        values.put(DbContract.Song.COLUMN_NAME_ID, entry.getId());
        values.put(DbContract.Song.COLUMN_NAME_TITLE, entry.getTitle());
        values.put(DbContract.Song.COLUMN_NAME_ARTIST_ID, entry.getArtistId());
        values.put(DbContract.Song.COLUMN_NAME_PATH, entry.getPath());

        db.insert(DbContract.Song.TABLE_NAME, null, values);
    }

    @Override
    public ArrayList<Song> select(String[] columns,
                                  String selection,
                                  String[] selectionArgs,
                                  String groupBy,
                                  String having,
                                  String orderBy) {


        ArrayList<Song> result = new ArrayList<>();

        Cursor c = db.query(DbContract.Song.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        if (c != null) {
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    result.add(new Song(c.getLong(c.getColumnIndex(DbContract.Song.COLUMN_NAME_ID)),
                            c.getString(c.getColumnIndex(DbContract.Song.COLUMN_NAME_TITLE)),
                            c.getLong(c.getColumnIndex(DbContract.Song.COLUMN_NAME_ARTIST_ID)),
                            c.getString(c.getColumnIndex(DbContract.Song.COLUMN_NAME_PATH))));
                }
            }
            c.close();
        }

        return result;

    }

    @Override
    public Song selectFirst(String[] columns,
                            String selection,
                            String[] selectionArgs,
                            String groupBy,
                            String having,
                            String orderBy) {

        Song song = null;
        ArrayList<Song> songs = select(columns, selection, selectionArgs, groupBy, having, orderBy);
        if (!songs.isEmpty())
            song = songs.get(0);
        return song;
    }

    @Override
    public void update(Song entry, String whereClause, String[] whereArgs) {

        ContentValues values = new ContentValues();
        values.put(DbContract.Song.COLUMN_NAME_ID, entry.getId());
        values.put(DbContract.Song.COLUMN_NAME_TITLE, entry.getTitle());
        values.put(DbContract.Song.COLUMN_NAME_ARTIST_ID, entry.getArtistId());
        values.put(DbContract.Song.COLUMN_NAME_PATH, entry.getPath());

        db.update(DbContract.Song.TABLE_NAME, values, whereClause, whereArgs);
    }

    @Override
    public void delete(String whereClause, String[] whereArgs) {
        db.delete(DbContract.Song.TABLE_NAME, whereClause, whereArgs);
    }

    @Override
    public void dropTable() {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.Song.TABLE_NAME);
    }
}
