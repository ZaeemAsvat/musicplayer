package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SongShuffleRelationDbHelper extends DbHelperBase<SongShuffleRelation> {

    private SQLiteDatabase db;

    SongShuffleRelationDbHelper(SQLiteDatabase db) { this.db = db; }

    public void createTable () {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.SongShuffleRelation.TABLE_NAME + " (" +
                DbContract.SongShuffleRelation.COLUMN_NAME_SONG_ID  + " INTEGER," +
                DbContract.SongShuffleRelation.COLUMN_NAME_SHUFFLE_ORDER_NUM + " INTEGER," +
                " FOREIGN KEY (" + DbContract.SongShuffleRelation.COLUMN_NAME_SONG_ID + ") REFERENCES " +
                DbContract.Song.TABLE_NAME + "(" + DbContract.Song.COLUMN_NAME_ID + "))");
    }

    @Override
    public void insert(SongShuffleRelation entry) {
        ContentValues values = new ContentValues();
        values.put(DbContract.SongShuffleRelation.COLUMN_NAME_SONG_ID, entry.getSongId());
        values.put(DbContract.SongShuffleRelation.COLUMN_NAME_SHUFFLE_ORDER_NUM, entry.getShuffleOrderNum());

        db.insert(DbContract.SongShuffleRelation.TABLE_NAME, null, values);
    }

    @Override
    public ArrayList<SongShuffleRelation> select(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        ArrayList<SongShuffleRelation> result = new ArrayList<>();

        Cursor c = db.query(DbContract.SongShuffleRelation.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        if (c != null) {
            if (c.getCount() > 0) {
                while (c.moveToNext())
                    result.add(new SongShuffleRelation(c.getLong(c.getColumnIndex(DbContract.SongShuffleRelation.COLUMN_NAME_SONG_ID)),
                        c.getInt(c.getColumnIndex(DbContract.SongShuffleRelation.COLUMN_NAME_SHUFFLE_ORDER_NUM))));
            }
            c.close();
        }

        return result;

    }

    @Override
    public SongShuffleRelation selectFirst(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        SongShuffleRelation songShuffleRelation = null;
        ArrayList<SongShuffleRelation> songShuffleRelations = select(columns, selection, selectionArgs, groupBy, having, orderBy);
        if (!songShuffleRelations.isEmpty())
            songShuffleRelation = songShuffleRelations.get(0);
        return songShuffleRelation;
    }

    @Override
    public void update(SongShuffleRelation entry, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();
        values.put(DbContract.SongShuffleRelation.COLUMN_NAME_SONG_ID, entry.getSongId());
        values.put(DbContract.SongShuffleRelation.COLUMN_NAME_SHUFFLE_ORDER_NUM, entry.getShuffleOrderNum());

        db.update(DbContract.SongShuffleRelation.TABLE_NAME, values, whereClause, whereArgs);
    }

    @Override
    public void delete(String whereClause, String[] whereArgs) {
        db.delete(DbContract.SongShuffleRelation.TABLE_NAME, whereClause, whereArgs);
    }

    public void dropTable() {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.SongShuffleRelation.TABLE_NAME);
    }
}