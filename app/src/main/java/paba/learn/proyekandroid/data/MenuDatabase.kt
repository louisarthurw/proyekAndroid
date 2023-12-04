package paba.learn.proyekandroid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import paba.learn.proyekandroid.data.dao.MenuDao
import paba.learn.proyekandroid.data.entity.Menus

@Database(entities = [Menus::class], version = 1)
abstract class MenuDatabase: RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        @Volatile
        private var instance: MenuDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MenuDatabase {
            if (instance == null) {
                synchronized(MenuDatabase::class.java){
                    instance = Room.databaseBuilder(context.applicationContext, MenuDatabase::class.java, "menu-database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance as MenuDatabase
        }
    }
}