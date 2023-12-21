package paba.learn.proyekandroid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import paba.learn.proyekandroid.data.dao.HistoryDao
import paba.learn.proyekandroid.data.dao.MenuDao
import paba.learn.proyekandroid.data.entity.History
import paba.learn.proyekandroid.data.entity.Menus

@Database(entities = [History::class], version = 1)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var instance: HistoryDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): HistoryDatabase {
            if (instance == null) {
                synchronized(HistoryDatabase::class.java){
                    instance = Room.databaseBuilder(context.applicationContext, HistoryDatabase::class.java, "history-database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance as HistoryDatabase
        }
    }
}