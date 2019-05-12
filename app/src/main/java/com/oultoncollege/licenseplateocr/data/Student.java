package com.oultoncollege.licenseplateocr.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "student", indices = @Index("license_plate"))
public class Student {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "student_id")
    public String id;

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

    public Student() {}

    public Student(String id, String firstName, String lastName, String email, String licensePlate, String vehicleMakeModel, String stickerNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.licensePlate = licensePlate;
        this.vehicleMakeModel = vehicleMakeModel;
        this.stickerNumber = stickerNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVehicleMakeModel() {
        return vehicleMakeModel;
    }

    public void setVehicleMakeModel(String vehicleMakeModel) {
        this.vehicleMakeModel = vehicleMakeModel;
    }

    public String getStickerNumber() {
        return stickerNumber;
    }

    public void setStickerNumber(String stickerNumber) {
        this.stickerNumber = stickerNumber;
    }

    public String toString() {
        return id + " - " + firstName + " " + lastName + " - " + email + " - " + licensePlate + " - " + vehicleMakeModel + " - " + stickerNumber;
    }
}
