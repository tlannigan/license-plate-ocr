package com.oultoncollege.licenseplateocr.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM student")
    Student[] getAllStudents();

    @Query("SELECT * FROM student WHERE license_plate = :licensePlate")
    Student findStudentByLicensePlate(String licensePlate);

    @Insert
    void fill(Student... students);

    @Delete
    void flush();
}