package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PlaylisDbHelper extends DbHelperBase<Playlist> {

    private SQLiteDatabase db;

    PlaylisDbHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public void createTable () {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.Playlist.TABLE_NAME + " (" +
                DbContract.Playlist.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                DbContract.Playlist.COLUMN_NAME_NAME + " TEXT)");
    }

    @Override
    public void insert(Playlist entry) {
        ContentValues values = new ContentValues();
        values.put(DbContract.Playlist.COLUMN_NAME_ID, entry.getPlaylistId());
        values.put(DbContract.Playlist.COLUMN_NAME_NAME, entry.getPlaylistName());
        db.insert(DbContract.Playlist.TABLE_NAME, null, values);
    }

    @Override
    public ArrayList<Playlist> select(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        ArrayList<Playlist> result = new ArrayList<>();

        Cursor c = db.query(DbContract.Playlist.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                result.add(new Playlist(c.getLong(c.getColumnIndex(DbContract.Playlist.COLUMN_NAME_ID)),
                        c.getString(c.getColumnIndex(DbContract.Playlist.COLUMN_NAME_NAME))));;

            }
            c.close();
        }

        return result;
    }

    @Override
    public Playlist selectFirst(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Playlist playlist = null;
        ArrayList<Playlist> playlists = select(columns, selection, selectionArgs, groupBy, having, orderBy);
        if (!playlists.isEmpty())
            playlist = playlists.get(0);
        return playlist;
    }

    @Override
    public void update(Playlist entry, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();
        values.put(DbContract.Playlist.COLUMN_NAME_ID, entry.getPlaylistId());
        values.put(DbContract.Playlist.COLUMN_NAME_NAME, entry.getPlaylistName());
        db.update(DbContract.Playlist.TABLE_NAME, values, whereClause, whereArgs);
    }

    @Override
    public void delete(String whereClause, String[] whereArgs) {
        db.delete(DbContract.Playlist.TABLE_NAME, whereClause, whereArgs);
    }

    public void dropTable() {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.Playlist.TABLE_NAME);
    }

}
