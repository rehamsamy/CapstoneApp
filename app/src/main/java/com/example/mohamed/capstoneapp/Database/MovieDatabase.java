package com.example.mohamed.capstoneapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mohamed.capstoneapp.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase mInstance;
    private static String DATABASE_NAME = "tasks";

    public static MovieDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class
                    , MovieDatabase.DATABASE_NAME).allowMainThreadQueries().build();
        }
        return mInstance;
    }

    public abstract TaskDao taskDao();
}
