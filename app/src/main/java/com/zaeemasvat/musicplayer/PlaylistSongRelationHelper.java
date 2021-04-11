package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PlaylistSongRelationHelper extends DbHelperBase<PlaylistSongRelation> {

    private final SQLiteDatabase db;

    PlaylistSongRelationHelper (SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void createTable() {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.Playlist.TABLE_NAME + " (" +
                DbContract.UserSongInteraction.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                " FOREIGN KEY (" + DbContract.PlaylistSongRelation.COLUMN_NAME_SONG_ID + ") REFERENCES " +
                DbContract.Song.TABLE_NAME + "(" + DbContract.Song.COLUMN_NAME_ID + ")," +
                " FOREIGN KEY (" + DbContract.PlaylistSongRelation.COLUMN_NAME_PLAYLIST_ID + ") REFERENCES " +
                DbContract.Playlist.TABLE_NAME + "(" + DbContract.Playlist.COLUMN_NAME_ID + "))");
    }

    @Override
    public void insert(PlaylistSongRelation entry) {
        ContentValues values = new ContentValues();
        values.put(DbContract.PlaylistSongRelation.COLUMN_NAME_SONG_ID, entry.getSongId());
        values.put(DbContract.PlaylistSongRelation.COLUMN_NAME_PLAYLIST_ID, entry.getPlaylistId());

        db.insert(DbContract.PlaylistSongRelation.TABLE_NAME, null, values);
    }

    @Override
    public ArrayList<PlaylistSongRelation> select(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        ArrayList<PlaylistSongRelation> result = new ArrayList<>();

        Cursor c = db.query(DbContract.PlaylistSongRelation.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                result.add(new PlaylistSongRelation(c.getLong(c.getColumnIndex(DbContract.PlaylistSongRelation.COLUMN_NAME_SONG_ID)),
                        c.getLong(c.getColumnIndex(DbContract.PlaylistSongRelation.COLUMN_NAME_PLAYLIST_ID))));
            }

            c.close();
        }

        return result;
    }

    @Override
    public PlaylistSongRelation selectFirst(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        PlaylistSongRelation playlistSongRelation = null;
        ArrayList<PlaylistSongRelation> playlistSongRelations = select(columns, selection, selectionArgs, groupBy, having, orderBy);
        if (!playlistSongRelations.isEmpty())
            playlistSongRelation = playlistSongRelations.get(0);
        return playlistSongRelation;
    }

    @Override
    public void update(PlaylistSongRelation entry, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();
        values.put(DbContract.PlaylistSongRelation.COLUMN_NAME_SONG_ID, entry.getSongId());
        values.put(DbContract.PlaylistSongRelation.COLUMN_NAME_PLAYLIST_ID, entry.getPlaylistId());

        db.update(DbContract.PlaylistSongRelation.TABLE_NAME, values, whereClause, whereArgs);
    }

    @Override
    public void delete(String whereClause, String[] whereArgs) {
        db.delete(DbContract.PlaylistSongRelation.TABLE_NAME, whereClause, whereArgs);
    }

    public void dropTable () {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.UserSongInteraction.TABLE_NAME);
    }
}
