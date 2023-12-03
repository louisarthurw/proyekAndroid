package paba.learn.proyekandroid.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import paba.learn.proyekandroid.data.entity.Users

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: Users)

    @Query("UPDATE users SET full_name=:full_name, email=:email, phone_number=:phone_number, address=:address, password=:password, balance=:balance WHERE uid=:uid")
    fun update(full_name: String, email: String, phone_number: String, address: String, password: String, balance: Int, uid: Int)

    @Delete
    fun delete(user: Users)

    @Query("SELECT * FROM users ORDER BY uid ASC")
    fun selectAll(): MutableList<Users>

    @Query("SELECT * FROM users WHERE email = :email")
    fun cekEmailValid(email:String): Users?

    @Query("SELECT * FROM users WHERE email=:email AND password=:password")
    fun cekLoginValid(email:String, password: String): Users?

    @Query("SELECT * FROM users WHERE uid=:uid")
    fun getUser(uid: Int): Users

    @Query("DELETE FROM users WHERE uid=:uid")
    fun deleteUser(uid: Int)

}