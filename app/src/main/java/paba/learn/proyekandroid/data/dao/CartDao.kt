package paba.learn.proyekandroid.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import paba.learn.proyekandroid.data.entity.Cart
import paba.learn.proyekandroid.data.entity.Menus

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cart: Cart)

    @Query("UPDATE cart SET  jumlah_menu=:jumlah_menu WHERE idCart=:id_cart")
    fun update(jumlah_menu: Int, id_cart: Int)

    @Delete
    fun delete(cart: Cart)

    @Query("SELECT * FROM cart ORDER BY idCart ASC")
    fun selectAll(): MutableList<Cart>

    @Query("SELECT * FROM cart WHERE id_menu=:id_menu AND id_user=:id_user")
    fun getItemID(id_menu: Int, id_user: Int): Int

    @Query("SELECT * FROM cart WHERE id_menu=:id_menu AND id_user=:id_user")
    fun isItemInCart(id_menu: Int, id_user: Int): Cart?

    @Query("SELECT jumlah_menu FROM cart WHERE id_menu=:id_menu AND id_user=:id_user")
    fun getJumlahItem(id_menu: Int, id_user: Int): Int

    @Query("DELETE FROM cart WHERE idCart=:id_cart")
    fun deleteItem(id_cart: Int)
}