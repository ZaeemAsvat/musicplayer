package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class UserSongInteractionDbHelper {

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

    public long insertUserSongInteraction (UserSongInteraction userSongInteraction) {

        ContentValues values = new ContentValues();
        values.put(DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID, userSongInteraction.getSongId());
        values.put(DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION, userSongInteraction.getSongInteraction());

        return db.insert(DbContract.UserSongInteraction.TABLE_NAME, null, values);
    }

    public ArrayList<UserSongInteraction> selectUserSongInteractions (ArrayList<String> whereArgs) {

        ArrayList<UserSongInteraction> result = new ArrayList<>();

        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("SELECT * FROM " + DbContract.UserSongInteraction.TABLE_NAME);
        if (whereArgs != null) {
            for (String whereArg : whereArgs)
                selectQuery.append(" WHERE " + whereArg + " AND ");
            selectQuery.delete(selectQuery.length() - 5, selectQuery.length());
        }

        Cursor c = db.rawQuery(selectQuery.toString(), null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                result.add(new UserSongInteraction(c.getLong(c.getColumnIndex(DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID)),
                        c.getInt(c.getColumnIndex(DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION)) > 0));
            }

            c.close();
        }

        return result;
    }

    public UserSongInteraction selectUserSongInteracions (ArrayList<String> whereArgs) {

        UserSongInteraction result = null;

        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("SELECT * FROM " + DbContract.UserSongInteraction.TABLE_NAME);
        for (String whereArg : whereArgs)
            selectQuery.append(" WHERE " + whereArg + " AND ");
        selectQuery.delete(selectQuery.length() - 5, selectQuery.length());

        Cursor c = db.rawQuery(selectQuery.toString(), null);

        if (c != null) {
            c.moveToFirst();
            result = new UserSongInteraction(c.getLong(c.getColumnIndex(DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID)),
                    c.getInt(c.getColumnIndex(DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION)) > 0);
            c.close();
        }

        return result;
    }

    public UserSongInteraction selectUserSongInteractions(long song_id) {

        UserSongInteraction result = null;

        String selectQuery = "SELECT * FROM " + DbContract.UserSongInteraction.TABLE_NAME + " WHERE " +
                DbContract.SongFeatures.COLUMN_NAME_SONG_ID + " = " + song_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            if (c.getCount() == 1) {
                c.moveToFirst();
                result = new UserSongInteraction(song_id,
                        c.getInt(c.getColumnIndex(DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION)) > 0);
            }

            c.close();
        }

        return result;
    }

    public void deleteUserSongInteraction (ArrayList<String> whereArgs) {

        StringBuilder deleteQuery = new StringBuilder();
        deleteQuery.append("DELETE FROM " + DbContract.UserSongInteraction.TABLE_NAME);
        for (String whereArg : whereArgs)
            deleteQuery.append(" WHERE " + whereArg + " AND ");
        deleteQuery.delete(deleteQuery.length() - 5, deleteQuery.length());

        db.execSQL(deleteQuery.toString(), null);
    }

    public void updateUserSongInteraction (UserSongInteraction userSongInteraction, ArrayList<String> whereArgs) {

        StringBuilder updateQuery = new StringBuilder();
        updateQuery.append("UPDATE " + DbContract.UserSongInteraction.TABLE_NAME
                + " SET " + DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID + " = " + userSongInteraction.getSongId()
                + ", " + DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION + " = " + userSongInteraction.getSongInteraction());
        for (String whereArg : whereArgs)
            updateQuery.append(" WHERE " + whereArg + " AND ");
        updateQuery.delete(updateQuery.length() - 5, updateQuery.length());

        db.execSQL(updateQuery.toString(), null);
    }

    public void dropTable () {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.UserSongInteraction.TABLE_NAME);
    }

}

