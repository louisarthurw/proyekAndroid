package paba.learn.proyekandroid.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import paba.learn.proyekandroid.data.entity.Users

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<Users>

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Users>

    @Insert
    fun insertAll(vararg users: Users)

    @Delete
    fun delete(user: Users)
}