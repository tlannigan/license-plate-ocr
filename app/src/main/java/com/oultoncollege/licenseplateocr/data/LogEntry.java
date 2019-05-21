package com.oultoncollege.licenseplateocr.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "log")
public class LogEntry {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "time_stamp")
    public long timeStamp;

    @ColumnInfo(name = "license_plate")
    public String licensePlate;

    public String description;

    public LogEntry() {

    }

    public LogEntry(long timeStamp, String licensePlate, String description) {
        this.timeStamp = timeStamp;
        this.licensePlate = licensePlate;
        this.description = description;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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
}
