package com.zaeemasvat.musicplayer;
import java.util.ArrayList;

public abstract class DbHelperBase<object> {

    public abstract void createTable ();

    public abstract void insert(object entry);

    public abstract ArrayList<object> select (String[] columns,
                                String selection,
                                String[] selectionArgs,
                                String groupBy,
                                String having,
                                String orderBy);

    public abstract object selectFirst (String[] columns,
                               String selection,
                               String[] selectionArgs,
                               String groupBy,
                               String having,
                               String orderBy);

    public abstract void delete (String whereClause, String[] whereArgs);

    public abstract void update (object entry, String whereClause, String[] whereArgs);

    public abstract void dropTable();

}
