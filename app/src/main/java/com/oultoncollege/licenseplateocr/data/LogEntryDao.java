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

    @Query("SELECT * FROM log WHERE date <= :date")
    List<LogEntry> getLogsBeforeDate(long date);

    @Query("SELECT * FROM log WHERE date = :date")
    List<LogEntry> getLogsByDate(long date);

    @Insert
    void add(LogEntry... logEntries);

    @Query("DELETE FROM log WHERE id = :id")
    void deleteById(int id);

    @Delete
    void remove(LogEntry... logEntries);
}
