package com.sultan.aidanaapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sultan.aidanaapp.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase() {
    abstract fun dao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: MealDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MealDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "user_database.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}