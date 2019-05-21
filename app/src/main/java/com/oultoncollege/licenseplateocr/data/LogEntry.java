package com.oultoncollege.licenseplateocr.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "log")
public class LogEntry {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "license_plate")
    private String licensePlate;

    private String description;

    private long date;

    public LogEntry() {

    }

    public LogEntry(String licensePlate, String description, long date) {
        this.licensePlate = licensePlate;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String toString() {
        return licensePlate + "\n" + description;
    }
}
