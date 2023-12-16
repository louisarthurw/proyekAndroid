package paba.learn.proyekandroid

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import paba.learn.proyekandroid.data.AppDatabase
import paba.learn.proyekandroid.data.MenuDatabase
import java.io.IOException
import java.text.NumberFormat
import java.util.Locale

class DetailMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        var databaseMenu = MenuDatabase.getDatabase(this)

        var _ivDetailGambarMenu = findViewById<ImageView>(R.id.ivDetailGambarMenu)
        var _tvDetailNamaMenu = findViewById<TextView>(R.id.tvDetailNamaMenu)
        var _tvDetailHargaMenu = findViewById<TextView>(R.id.tvDetailHargaMenu)
        var _tvDetailDeskripsiMenu = findViewById<TextView>(R.id.tvDetailDeskripsiMenu)
        var _btnBack = findViewById<Button>(R.id.btnBack2)

        var id_user: String = ""
        var id_menu: Int = 0

        id_user = intent.getStringExtra("idUser").toString()
        id_menu = intent.getIntExtra("idMenu", 0)

        var menu = databaseMenu.menuDao().getMenu(id_menu)
        var gambarMenu = menu.gambarMenu
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        val hargaMenu = formatter.format(menu.hargaMenu)

        if (isFileInFolder(this@DetailMenu, gambarMenu.toString())) {
            val bitmap: Bitmap? = showImage(this@DetailMenu, gambarMenu.toString())
            bitmap?.let {
                _ivDetailGambarMenu.setImageBitmap(bitmap)
            }
        } else {
            val bitmap: Bitmap? = showImage(this@DetailMenu, "defaultimage.png")
            bitmap?.let {
                _ivDetailGambarMenu.setImageBitmap(bitmap)
            }
        }
        _tvDetailNamaMenu.text = menu.namaMenu.toString()
        _tvDetailHargaMenu.text = hargaMenu.toString()
        _tvDetailDeskripsiMenu.text = menu.deskripsiMenu.toString()

        _btnBack.setOnClickListener {
//            val intent = Intent(this@DetailMenu, MainAdmin::class.java).apply {
//                putExtra(MainAdmin.idUser, id_user)
//            }
//            startActivity(intent)
            finish()
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

    companion object {
        const val idUser = "id"
    }
}