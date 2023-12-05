package paba.learn.proyekandroid

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import paba.learn.proyekandroid.data.MenuDatabase
import paba.learn.proyekandroid.data.entity.Menus
import java.io.IOException

class AddEditMenu : AppCompatActivity() {
    val database = MenuDatabase.getDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_menu)

        var id_user = intent.getStringExtra(idUser).toString()

        var _tvHeader = findViewById<TextView>(R.id.textView21)
        var _etNamaMenu = findViewById<TextView>(R.id.etNamaMenu)
        var _etHargaMenu = findViewById<TextView>(R.id.etHargaMenu)
        var _etDeskripsiMenu = findViewById<TextView>(R.id.etDeskripsiMenu)
        var _etGambarMenu = findViewById<TextView>(R.id.etGambarMenu)
        var _gambarMenu = findViewById<ImageView>(R.id.ivDisplayGambarMenu)
        var _btnAddMenu = findViewById<Button>(R.id.btnAddMenu)
        var _btnEditMenu = findViewById<Button>(R.id.btnEditMenu)
        var _btnBack = findViewById<Button>(R.id.btnBack)

        var iID: Int = 0
        var iAddEdit: Int = 0
        iID = intent.getIntExtra("idMenu", 0)
        iAddEdit = intent.getIntExtra("addEdit", 0)

        if (iAddEdit == 0) {
            _tvHeader.text = "Add Menu"
            _btnAddMenu.visibility = View.VISIBLE
            _btnEditMenu.visibility = View.GONE
            Log.d("iid", iID.toString())
            Log.d("add_or_edit", iAddEdit.toString())
        } else {
            _tvHeader.text = "Edit Menu"
            _btnAddMenu.visibility = View.GONE
            _btnEditMenu.visibility = View.VISIBLE

            val editedMenu = database.menuDao().getMenu(iID)
            _etNamaMenu.setText(editedMenu.namaMenu)
            _etHargaMenu.setText(editedMenu.hargaMenu.toString())
            _etDeskripsiMenu.setText(editedMenu.deskripsiMenu)
            _etGambarMenu.setText(editedMenu.gambarMenu)

            if (isFileInFolder(this@AddEditMenu, _etGambarMenu.text.toString())) {
                val bitmap: Bitmap? = showImage(this@AddEditMenu, _etGambarMenu.text.toString())
                bitmap?.let {
                    _gambarMenu.setImageBitmap(bitmap)
                }
            } else {
                val bitmap: Bitmap? = showImage(this@AddEditMenu, "defaultimage.png")
                bitmap?.let {
                    _gambarMenu.setImageBitmap(bitmap)
                }
            }
        }

        _etGambarMenu.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                if (isFileInFolder(this@AddEditMenu, _etGambarMenu.text.toString())) {
                    val bitmap: Bitmap? = showImage(this@AddEditMenu, _etGambarMenu.text.toString())
                    bitmap?.let {
                        _gambarMenu.setImageBitmap(bitmap)
                    }
                } else {
                    val bitmap: Bitmap? = showImage(this@AddEditMenu, "defaultimage.png")
                    bitmap?.let {
                        _gambarMenu.setImageBitmap(bitmap)
                    }
                }
                return@setOnKeyListener true
            }
            false
        }

        _btnAddMenu.setOnClickListener {
            if (_etNamaMenu.text.toString() != "" && _etHargaMenu.text.toString() != "" && _etDeskripsiMenu.text.toString() != "" && _etGambarMenu.text.toString() != "") {
                CoroutineScope(Dispatchers.IO).async {
                    database.menuDao().insert(
                        Menus(
                            null,
                            _etNamaMenu.text.toString(),
                            _etHargaMenu.text.toString().toInt(),
                            _etDeskripsiMenu.text.toString(),
                            _etGambarMenu.text.toString(),
                        )
                    )
                    finish()
                }
                Toast.makeText(applicationContext, "Berhasil menambahkan menu!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Ada field yang belum diisi!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        _btnEditMenu.setOnClickListener {
            if (_etNamaMenu.text.toString() != "" && _etHargaMenu.text.toString() != "" && _etDeskripsiMenu.text.toString() != "" && _etGambarMenu.text.toString() != "") {
                CoroutineScope(Dispatchers.IO).async {
                    database.menuDao().update(
                        _etNamaMenu.text.toString(),
                        _etHargaMenu.text.toString().toInt(),
                        _etDeskripsiMenu.text.toString(),
                        _etGambarMenu.text.toString(),
                        iID
                    )
                    finish()
                }
                Toast.makeText(applicationContext, "Berhasil edit menu!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Ada field yang belum diisi!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        _btnBack.setOnClickListener {
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