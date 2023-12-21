package paba.learn.proyekandroid.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import paba.learn.proyekandroid.data.entity.Cart
import paba.learn.proyekandroid.data.entity.History

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(history: History)

    @Query("UPDATE history SET jumlah_dipesan=:jumlah_dipesan WHERE idMenu=:id_menu")
    fun update(jumlah_dipesan: Int, id_menu: Int)

    @Query("SELECT * FROM history ORDER BY idMenu ASC")
    fun selectAll(): MutableList<History>

    @Query("SELECT jumlah_dipesan FROM history WHERE idMenu=:id_menu")
    fun getJumlahDipesan(id_menu: Int): Int?
}