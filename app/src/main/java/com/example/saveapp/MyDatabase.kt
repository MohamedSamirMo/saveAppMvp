package com.example.saveapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = arrayOf(ContactModel::class), version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase()  {
    abstract fun getDao(): MyDao
    companion object {
        lateinit var myDatabase: MyDatabase
        fun init(context: Context){
            myDatabase=Room.databaseBuilder(context,MyDatabase::class.java
                ,"myDatabase")
//                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

    }


}