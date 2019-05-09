package com.oultoncollege.licenseplateocr.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "student", indices = @Index("license_plate"))
public class Student {

    @PrimaryKey
    public int ID;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    public String email;

    @ColumnInfo(name = "license_plate")
    public String licensePlate;

    @ColumnInfo(name = "vehicle_make_model")
    public String vehicleMakeModel;

    @ColumnInfo(name = "sticker_number")
    public String stickerNumber;
}
