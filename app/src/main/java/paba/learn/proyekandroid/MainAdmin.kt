package paba.learn.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import paba.learn.proyekandroid.adapter.adapterMenu
import paba.learn.proyekandroid.data.MenuDatabase
import paba.learn.proyekandroid.data.entity.Menus

class MainAdmin : AppCompatActivity() {
    private lateinit var database: MenuDatabase
    private lateinit var adapterM: adapterMenu
    private val arMenu: MutableList<Menus> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)

        database = MenuDatabase.getDatabase(this)

        var id_user = intent.getStringExtra(idUser).toString()
        val _rvMenus = findViewById<RecyclerView>(R.id.rvMenus)
        val _btnLogoutAdmin = findViewById<ImageView>(R.id.ivLogoutAdmin)
        val _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

        adapterM = adapterMenu(arMenu, id_user)
        _rvMenus.layoutManager = LinearLayoutManager(this)
        _rvMenus.adapter = adapterM

        _btnLogoutAdmin.setOnClickListener {
            val intent = Intent(this@MainAdmin, Menu::class.java).apply {
                putExtra(Menu.idLogin, id_user)
            }
            startActivity(intent)
            this.finish()
        }

        _fabAdd.setOnClickListener {
            val intent = Intent(this@MainAdmin, AddEditMenu::class.java).apply {
                putExtra(AddEditMenu.idUser, id_user)
            }
            startActivity(intent)
        }

        adapterM.setOnItemClickCallback(object : adapterMenu.OnItemClickCallback {
            override fun delData(dtmenu: Menus) {
                database.menuDao().delete(dtmenu)
                val note = database.menuDao().selectAll()
                adapterM.isiData(note)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val menu = database.menuDao().selectAll()
        adapterM.isiData(menu)
    }

    companion object {
        const val idUser = "id"
    }
}