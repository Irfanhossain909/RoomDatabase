package com.example.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table") //Table name.

data class Student(
    @PrimaryKey(autoGenerate = true) //Primary kay Auto Chengeble.
    val id:Int?,
    @ColumnInfo(name = "first_name") //Columname For first name.
    val firstName:String?,
    @ColumnInfo(name = "last_name")//Columname For last name.
    val lastName:String?,
    @ColumnInfo(name = "roll_no")//Columname For RollNo.
    val rollNo:Int?
)
