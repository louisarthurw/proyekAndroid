package paba.learn.proyekandroid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import paba.learn.proyekandroid.data.dao.UserDao
import paba.learn.proyekandroid.data.entity.Users

@Database(entities = [Users::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class.java){
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app-database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance as AppDatabase
        }
    }
}