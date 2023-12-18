package paba.learn.proyekandroid

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import paba.learn.proyekandroid.data.CartDatabase
import paba.learn.proyekandroid.data.MenuDatabase
import paba.learn.proyekandroid.data.entity.Cart
import paba.learn.proyekandroid.data.entity.Menus
import java.io.IOException
import java.text.NumberFormat
import java.util.Locale

class AddToCart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)

        var dataBaseMenu = MenuDatabase.getDatabase(this)
        var database = CartDatabase.getDatabase(this)
        var _ivDetailGambarMenu = findViewById<ImageView>(R.id.imageMakanan)
        var _tvDetailNamaMenu = findViewById<TextView>(R.id.namaMenu)
        var _tvDetailHargaMenu = findViewById<TextView>(R.id.hargaMenu)
        var _tvDetailDeskripsiMenu = findViewById<TextView>(R.id.deskripsiMenu)
        var _fbAdd = findViewById<FloatingActionButton>(R.id.floatingActionButtonAdd)
        var _fbMin = findViewById<FloatingActionButton>(R.id.floatingActionButtonMinus)
        var _tvDetailJumlahMenu = findViewById<TextView>(R.id.jumlahMenu)
        var _btnAddToCart = findViewById<Button>(R.id.buttonAddToCart)
        var color = ContextCompat.getColor(this, R.color.white)
        _fbAdd.imageTintList = ColorStateList.valueOf(color)
        _fbMin.imageTintList = ColorStateList.valueOf(color)



        var id_user: Int = 0
        var id_menu: Int = 0

        id_user = intent.getIntExtra("idUser", 0)
        id_menu = intent.getIntExtra("idMenu", 0)

        Log.d("ID_USER", id_user.toString())
        Log.d("ID_MENU", id_menu.toString())

        var menu = dataBaseMenu.menuDao().getMenu(id_menu)
        var gambarMenu = menu.gambarMenu
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        val hargaMenu = formatter.format(menu.hargaMenu)

        if (isFileInFolder(this@AddToCart, gambarMenu.toString())) {
            val bitmap: Bitmap? = showImage(this@AddToCart, gambarMenu.toString())
            bitmap?.let {
                _ivDetailGambarMenu.setImageBitmap(bitmap)
            }
        } else {
            val bitmap: Bitmap? = showImage(this@AddToCart, "defaultimage.png")
            bitmap?.let {
                _ivDetailGambarMenu.setImageBitmap(bitmap)
            }
        }
        _tvDetailNamaMenu.text = menu.namaMenu.toString()
        _tvDetailHargaMenu.text = hargaMenu.toString()
        _tvDetailDeskripsiMenu.text = menu.deskripsiMenu.toString()

        if (database.cartDao().isItemInCart(id_menu, id_user) != null) {
            _tvDetailJumlahMenu.text = database.cartDao().getJumlahItem(id_menu, id_user).toString()
        }

        var jumlahItemAwal = _tvDetailJumlahMenu.text.toString().toInt()

        _fbAdd.setOnClickListener {
            var jumlahMenu = _tvDetailJumlahMenu.text.toString().toInt()
            jumlahMenu++
            _tvDetailJumlahMenu.text = jumlahMenu.toString()
        }

        _fbMin.setOnClickListener {
            var jumlahMenu = _tvDetailJumlahMenu.text.toString().toInt()
            if (jumlahMenu == 0) {
                Toast.makeText(applicationContext, "Jumlah Item sudah 0!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                jumlahMenu--
                _tvDetailJumlahMenu.text = jumlahMenu.toString()
            }
        }

        _btnAddToCart.setOnClickListener {
            if (_tvDetailJumlahMenu.text.toString().toInt() == 0) {
                if (jumlahItemAwal == 0) {
                    Toast.makeText(
                        applicationContext,
                        "Item berjumlah 0 tidak bisa diadd",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    var itemID = database.cartDao().getItemID(id_menu, id_user)
                    database.cartDao().deleteItem(itemID)
                    Toast.makeText(
                        applicationContext,
                        "Item berhasil dihapus dari cart",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            } else {
                if (database.cartDao().isItemInCart(id_menu, id_user) != null) {
                    var itemID = database.cartDao().getItemID(id_menu, id_user)
                    database.cartDao()
                        .update(_tvDetailJumlahMenu.text.toString().toInt(), itemID)
                    Toast.makeText(
                        applicationContext,
                        "Berhasil update jumlah item!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    database.cartDao().insert(
                        Cart(
                            null,
                            id_menu,
                            _tvDetailJumlahMenu.text.toString().toInt(),
                            id_user
                        )
                    )
                    Toast.makeText(
                        applicationContext,
                        "Berhasil Add Item ke dalam Cart",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                finish()
            }
        }
    }

    private fun showImage(context: Context, file: String): Bitmap? {
        return try {
            val inputStream = context.assets?.open(file)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun isFileInFolder(context: Context, file: String): Boolean {
        return try {
            val inputStream = context.assets.open(file)
            inputStream.close()
            true
        } catch (e: IOException) {
            false
        }
    }
}