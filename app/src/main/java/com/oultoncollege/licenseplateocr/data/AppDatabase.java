package com.oultoncollege.licenseplateocr.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class, LogEntry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract StudentDao studentDao();
    public abstract LogEntryDao logEntryDao();

    private static AppDatabase licenseDB;

    public static AppDatabase getInstance(Context context) {
        if (licenseDB == null) {
            licenseDB = buildDatabaseInstance(context);
        }
        return licenseDB;
    }

    public static AppDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "licenseplate.db")
                .allowMainThreadQueries()
                .build();
    }

    public void release() {
        licenseDB = null;
    }
}
