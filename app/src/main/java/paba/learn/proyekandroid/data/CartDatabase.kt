package paba.learn.proyekandroid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import paba.learn.proyekandroid.data.dao.CartDao
import paba.learn.proyekandroid.data.entity.Cart

@Database(entities = [Cart::class], version = 2)
abstract class CartDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var instance: CartDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): CartDatabase {
            if (instance == null) {
                synchronized(CartDatabase::class.java){
                    instance = Room.databaseBuilder(context.applicationContext, CartDatabase::class.java, "cart-database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance as CartDatabase
        }
    }
}