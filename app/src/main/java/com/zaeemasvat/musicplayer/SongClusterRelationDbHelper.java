package com.zaeemasvat.musicplayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SongClusterRelationDbHelper extends DbHelperBase<SongClusterRelation> {

    private SQLiteDatabase db;

    SongClusterRelationDbHelper (SQLiteDatabase db) {
        this.db = db;
    }

    public void createTable () {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbContract.SongClusterRelation.TABLE_NAME + " (" +
                DbContract.SongClusterRelation.COLUMN_NAME_SONG_ID  + " INTEGER," +
                DbContract.SongClusterRelation.COLUMN_NAME_CLUSTER_NUM + " INTEGER," +
                " FOREIGN KEY (" + DbContract.SongClusterRelation.COLUMN_NAME_SONG_ID + ") REFERENCES " +
                DbContract.Song.TABLE_NAME + "(" + DbContract.Song.COLUMN_NAME_ID + "))");
    }

    @Override
    public void insert(SongClusterRelation entry) {
        ContentValues values = new ContentValues();
        values.put(DbContract.SongClusterRelation.COLUMN_NAME_SONG_ID, entry.getSongId());
        values.put(DbContract.SongClusterRelation.COLUMN_NAME_CLUSTER_NUM, entry.getClusterNum());

        db.insert(DbContract.SongClusterRelation.TABLE_NAME, null, values);
    }

    @Override
    public ArrayList<SongClusterRelation> select(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        ArrayList<SongClusterRelation> result = new ArrayList<>();

        Cursor c = db.query(DbContract.SongClusterRelation.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        if (c != null) {
            if (c.getCount() > 0) {
                while (c.moveToNext())
                    result.add(new SongClusterRelation(c.getLong(c.getColumnIndex(DbContract.SongClusterRelation.COLUMN_NAME_SONG_ID)),
                            c.getInt(c.getColumnIndex(DbContract.SongClusterRelation.COLUMN_NAME_CLUSTER_NUM))));
            }
            c.close();
        }

        return result;

    }

    @Override
    public SongClusterRelation selectFirst(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        SongClusterRelation songClusterRelation = null;
        ArrayList<SongClusterRelation> songClusterRelations = select(columns, selection, selectionArgs, groupBy, having, orderBy);
        if (!songClusterRelations.isEmpty())
            songClusterRelation = songClusterRelations.get(0);
        return songClusterRelation;
    }

    @Override
    public void update(SongClusterRelation entry, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();
        values.put(DbContract.SongClusterRelation.COLUMN_NAME_SONG_ID, entry.getSongId());
        values.put(DbContract.SongClusterRelation.COLUMN_NAME_CLUSTER_NUM, entry.getClusterNum());

        db.update(DbContract.SongClusterRelation.TABLE_NAME, values, whereClause, whereArgs);
    }

    @Override
    public void delete(String whereClause, String[] whereArgs) {
        db.delete(DbContract.SongClusterRelation.TABLE_NAME, whereClause, whereArgs);
    }

    public void dropTable() {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.SongClusterRelation.TABLE_NAME);
    }

}
