package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class UserSongInteractionDbHelper extends DbHelperBase<UserSongInteraction>{

    private SQLiteDatabase db;

    UserSongInteractionDbHelper (SQLiteDatabase db) {
        this.db = db;
    }

    public void createTable () {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.UserSongInteraction.TABLE_NAME + " (" +
                DbContract.UserSongInteraction.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION + " BOOLEAN," + // ??
                DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID  + " INTEGER," +
                " FOREIGN KEY (" + DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID + ") REFERENCES " +
                DbContract.Song.TABLE_NAME + "(" + DbContract.Song.COLUMN_NAME_ID + "))");
    }

    @Override
    public void insert(UserSongInteraction entry) {
        ContentValues values = new ContentValues();
        values.put(DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID, entry.getSongId());
        values.put(DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION, entry.getSongInteraction());

        db.insert(DbContract.UserSongInteraction.TABLE_NAME, null, values);
    }

    @Override
    public ArrayList<UserSongInteraction> select(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        ArrayList<UserSongInteraction> result = new ArrayList<>();

        Cursor c = db.query(DbContract.UserSongInteraction.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                result.add(new UserSongInteraction(c.getLong(c.getColumnIndex(DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID)),
                        c.getInt(c.getColumnIndex(DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION)) > 0));
            }

            c.close();
        }

        return result;
    }

    @Override
    public UserSongInteraction selectFirst(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        UserSongInteraction userSongInteraction = null;
        ArrayList<UserSongInteraction> userSongInteractions = select(columns, selection, selectionArgs, groupBy, having, orderBy);
        if (!userSongInteractions.isEmpty())
            userSongInteraction = userSongInteractions.get(0);
        return userSongInteraction;
    }

    @Override
    public void update(UserSongInteraction entry, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();
        values.put(DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID, entry.getSongId());
        values.put(DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION, entry.getSongInteraction());

        db.update(DbContract.UserSongInteraction.TABLE_NAME, values, whereClause, whereArgs);
    }

    @Override
    public void delete(String whereClause, String[] whereArgs) {
        db.delete(DbContract.UserSongInteraction.TABLE_NAME, whereClause, whereArgs);
    }

    public void dropTable () {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.UserSongInteraction.TABLE_NAME);
    }

}

