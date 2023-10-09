package com.example.roomdatabase

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
//Create Abstract class and extend roomdatabase with constructor.
abstract class StudentDatabase:RoomDatabase() {
    //decleared abstruct methord and creat StudentDao Object named studentDao
    abstract fun studentDao():StudentDao

    //create compenion object.Which help to  create database.
    companion object{
        @Volatile //Create INSTANCE for StudentDatabase Which is empty.
        private var INSTANCE:StudentDatabase?=null

        //Create a methord and we will get database and retun from StudentDatabase.
        fun getDatabase(context:Context):StudentDatabase{
            val tempInstance = INSTANCE //Create TempInstance and store INSTANCE.
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "student_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}