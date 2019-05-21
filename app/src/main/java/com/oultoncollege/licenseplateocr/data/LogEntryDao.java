package com.oultoncollege.licenseplateocr.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LogEntryDao {

    @Query("SELECT * FROM log")
    List<LogEntry> getAllLogs();

    @Query("SELECT * FROM log WHERE license_plate = :licensePlate")
    LogEntry findLogByLicensePlate(String licensePlate);

    @Query("SELECT * FROM log WHERE time_stamp <= :timeStamp")
    List<LogEntry> getLogsBeforeDate(long timeStamp);

    @Insert
    void add(LogEntry... logEntries);

    @Delete
    void remove(LogEntry... logEntries);
}
