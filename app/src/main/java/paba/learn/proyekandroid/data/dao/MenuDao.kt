package paba.learn.proyekandroid.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import paba.learn.proyekandroid.data.entity.Menus

@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(menu: Menus)

    @Query("UPDATE menus SET nama_menu=:nama_menu, harga_menu=:harga_menu, deskripsi_menu=:deskripsi_menu, gambar_menu=:gambar_menu WHERE idMenu=:id_menu")
    fun update(nama_menu: String, harga_menu: Int, deskripsi_menu: String, gambar_menu: String, id_menu: Int)

    @Delete
    fun delete(menu: Menus)

    @Query("SELECT * FROM menus ORDER BY idMenu ASC")
    fun selectAll(): MutableList<Menus>

    @Query("SELECT * FROM menus WHERE idMenu=:idMenu")
    fun getMenu(idMenu: Int): Menus

    @Query("SELECT nama_menu FROM menus WHERE idMenu=:idMenu")
    fun getNamaMenu(idMenu: Int): String?
}