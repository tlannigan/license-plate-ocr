package com.oultoncollege.licenseplateocr.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract StudentDao studentDao();

    public static AppDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "licenseplate.db")
                .allowMainThreadQueries()
                .build();
    }
}
