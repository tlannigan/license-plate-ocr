package com.oultoncollege.licenseplateocr.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM student")
    List<Student> getAllStudents();

    @Query("SELECT * FROM student WHERE license_plate = :licensePlate")
    Student findStudentByLicensePlate(String licensePlate);

    @Insert
    void add(Student... students);

    @Insert
    void fill(List<Student> students);

    @Query("DELETE FROM student")
    void flush();
}
