package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ArtistDbHelper extends DbHelperBase<Artist> {

    private SQLiteDatabase db;

    ArtistDbHelper(SQLiteDatabase db) {
        this.db = db;
    }

    public void createTable () {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.Artist.TABLE_NAME + " (" +
                DbContract.Artist.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                DbContract.Artist.COLUMN_NAME_NAME  + " TEXT)");
    }

    @Override
    public void insert(Artist entry) {

        ContentValues values = new ContentValues();
        values.put(DbContract.Artist.COLUMN_NAME_NAME, entry.getName());

        db.insert(DbContract.Artist.TABLE_NAME, null, values);
    }

    @Override
    public ArrayList<Artist> select(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        ArrayList<Artist> result = new ArrayList<>();

        Cursor c = db.query(DbContract.Artist.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        if (c != null) {
            if (c.getCount() > 0)
                while (c.moveToNext())
                    result.add(new Artist(c.getString(c.getColumnIndex(DbContract.Artist.COLUMN_NAME_NAME))));
            c.close();
        }

        return result;
    }

    @Override
    public Artist selectFirst(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Artist artist = null;
        ArrayList<Artist> artists = select(columns, selection, selectionArgs, groupBy, having, orderBy);
        if (!artists.isEmpty())
            artist = artists.get(0);
        return artist;
    }

    @Override
    public void update(Artist entry, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();
        values.put(DbContract.Artist.COLUMN_NAME_ID, entry.getId());
        values.put(DbContract.Artist.COLUMN_NAME_NAME, entry.getName());
        db.update(DbContract.Artist.TABLE_NAME, values, whereClause, whereArgs);
    }

    @Override
    public void delete(String whereClause, String[] whereArgs) {
        db.delete(DbContract.Artist.TABLE_NAME, whereClause, whereArgs);
    }

    public void dropTable() {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.Artist.TABLE_NAME);
    }

}
