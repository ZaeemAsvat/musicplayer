package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UserSongInteractionDbHelper {

    SQLiteDatabase db;

    UserSongInteractionDbHelper (SQLiteDatabase db) {
        this.db = db;
    }

    public void createTable () {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.UserSongInteraction.TABLE_NAME + " (" +
                DbContract.UserSongInteraction._ID + " INTEGER PRIMARY KEY," +
                DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION + " TEXT," +
                DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID  + " INTEGER," +
                " FOREIGN KEY (" + DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID + ") REFERENCES " +
                DbContract.Song.TABLE_NAME + "(" + DbContract.Song._ID + "))");
    }

    public long createUserSongInteraction (UserSongInteraction userSongInteraction) {

        ContentValues values = new ContentValues();
        values.put(DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID, userSongInteraction.getSongId());
        values.put(DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION, userSongInteraction.getInteractionVerdict());

        return db.insert(DbContract.UserSongInteraction.TABLE_NAME, null, values);
    }

    public ArrayList<UserSongInteraction> selectUserSongInteractions (ArrayList<String> whereArgs) {

        ArrayList<UserSongInteraction> result = new ArrayList<>();

        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("SELECT * FROM " + DbContract.Song.TABLE_NAME);
        for (String whereArg : whereArgs)
            selectQuery.append(" WHERE " + whereArg + " AND ");
        selectQuery.delete(selectQuery.length() - 5, selectQuery.length());

        Cursor c = db.rawQuery(selectQuery.toString(), null);

        if (c != null) {
            while (c.moveToNext()) {
                result.add(new UserSongInteraction(c.getLong(c.getColumnIndex(DbContract.UserSongInteraction._ID)),
                        c.getLong(c.getColumnIndex(DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID)),
                        c.getInt(c.getColumnIndex(DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION)) > 0));
            }
            c.close();
        }

        return result;
    }

    public UserSongInteraction selectUseSongInteracion (ArrayList<String> whereArgs) {

        UserSongInteraction result = null;

        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("SELECT * FROM " + DbContract.Song.TABLE_NAME);
        for (String whereArg : whereArgs)
            selectQuery.append(" WHERE " + whereArg + " AND ");
        selectQuery.delete(selectQuery.length() - 5, selectQuery.length());

        Cursor c = db.rawQuery(selectQuery.toString(), null);

        if (c != null) {
            c.moveToFirst();
            result = new UserSongInteraction(c.getLong(c.getColumnIndex(DbContract.UserSongInteraction._ID)),
                    c.getLong(c.getColumnIndex(DbContract.UserSongInteraction.COLUMN_NAME_SONG_ID)),
                    c.getInt(c.getColumnIndex(DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION)) > 0);
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
                + ", " + DbContract.UserSongInteraction.COLUMN_NAME_INTERACTION + " = " + userSongInteraction.getInteractionVerdict());
        for (String whereArg : whereArgs)
            updateQuery.append(" WHERE " + whereArg + " AND ");
        updateQuery.delete(updateQuery.length() - 5, updateQuery.length());

        db.execSQL(updateQuery.toString(), null);
    }

}

