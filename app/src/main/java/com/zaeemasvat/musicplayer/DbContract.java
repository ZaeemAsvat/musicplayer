package com.zaeemasvat.musicplayer;

import android.provider.BaseColumns;

public final class DbContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DbContract() {}

    /* Inner classes that defines table contents */

    // ------------------------- USER SONG INTERACTION ------------------------------------------

    public static class UserSongInteraction implements BaseColumns {
        public static final String TABLE_NAME = "USER_SONG_INTERACTION";
        public static final String COLUMN_NAME_ID = "user_song_interaction_id";
        public static final String COLUMN_NAME_SONG_ID = "song_id";
        public static final String COLUMN_NAME_INTERACTION = "interaction";
    }


    // ---------------------------------- SONG DATA -----------------------------------------------

    public static class Song implements BaseColumns {
        public static final String TABLE_NAME = "SONG";
        public static final String COLUMN_NAME_ID = "song_id";
        public static final String COLUMN_NAME_TITLE = "song_title";
        public static final String COLUMN_NAME_ARTIST = "song_artist";
        public static final String COLUMN_NAME_PATH = "song_path";
    }


    // --------------------------------- SONG FEATURE DATA ----------------------------------------
    public static class SongFeatures implements BaseColumns {
        public static final String TABLE_NAME = "SONG_FEATURES";
        public static final String COLUMN_NAME_SONG_ID = "song_id";
        public static final String COLUMN_NAME_MFCC = "feature_mfcc"; // multiple values exist
    }


    // --------------------------------- SONG CLUSTER RELATION --------------------------------
    public static class SongClusterRelation implements BaseColumns {
        public static final String TABLE_NAME = "SONG_CLUSTER_RELATION";
        public static final String COLUMN_NAME_SONG_ID = "song_id";
        public static final String COLUMN_NAME_CLUSTER_NUM = "cluster_num";
    }

    // --------------------------------- CURRENT SONG SHUFFLED LIST --------------------------------
    public static class SongShuffleRelation implements BaseColumns {
        public static final String TABLE_NAME = "SONG_SHUFFLED_RELATION";
        public static final String COLUMN_NAME_SONG_ID = "song_id";
        public static final String COLUMN_NAME_SHUFFLE_ORDER_NUM = "shuffle_order_number";
    }


}