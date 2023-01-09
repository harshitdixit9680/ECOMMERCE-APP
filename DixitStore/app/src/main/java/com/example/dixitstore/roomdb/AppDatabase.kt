package com.example.dixitstore.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductMoel::class], version = 1)

  abstract class AppDatabase : RoomDatabase()
{
        abstract fun productDao() :ProductDao

        companion object{
            private var database :AppDatabase? = null
            private val DATABASE_NAME = "Dixit&Store"
            @Synchronized
            fun getInstance(context: Context):AppDatabase{
                if (database == null){
                        database = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            DATABASE_NAME
                        ).allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
                return database!!
            }
        }
}