package com.myyour.e_comm_app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myyour.e_comm_app.model.Item

@Database(entities = [ProductEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO

    companion object {
        fun getDatabaseInstance(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "product"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}