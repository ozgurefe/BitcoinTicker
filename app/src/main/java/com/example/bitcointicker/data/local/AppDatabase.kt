package com.example.bitcointicker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bitcointicker.data.entities.Coin

@Database(entities = [Coin::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "coin.db")
                .fallbackToDestructiveMigration()
                .build()
    }

}