package paba.learn.proyekandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import paba.learn.proyekandroid.adapter.adapterCart
import paba.learn.proyekandroid.adapter.adapterMenuUser
import paba.learn.proyekandroid.data.AppDatabase
import paba.learn.proyekandroid.data.CartDatabase
import paba.learn.proyekandroid.data.MenuDatabase
import paba.learn.proyekandroid.data.entity.Cart
import paba.learn.proyekandroid.data.entity.Menus
import java.text.NumberFormat
import java.util.Locale

class DisplayCart : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var database_menu: MenuDatabase
    private lateinit var database_cart: CartDatabase
    private lateinit var adapterC: adapterCart
    private val arCart: MutableList<Cart> = mutableListOf()
    var totalHarga = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_cart)

        var _tvTotalHarga = findViewById<TextView>(R.id.totalHarga)

        database = AppDatabase.getDatabase(this)
        database_menu = MenuDatabase.getDatabase(this)
        database_cart = CartDatabase.getDatabase(this)

        val id_user = intent.getStringExtra(idUser)
        var user = database.userDao().getUser(id_user.toString().toInt())

        val isiCart = database_cart.cartDao().getAllItem(id_user.toString().toInt())

        val _rvCart = findViewById<RecyclerView>(R.id.rvCart)
        adapterC = adapterCart(arCart, id_user.toString())
        _rvCart.layoutManager = LinearLayoutManager(this)
        _rvCart.adapter = adapterC

        adapterC.setOnItemClickCallback(object : adapterCart.OnItemClickCallback {

        })
        totalHarga = 0

        for (i in isiCart) {
            val menu = database_menu.menuDao().getMenu(i.idMenu ?: 0)
            val harga = menu.hargaMenu
            val jumlah = i.jumlahMenu
            totalHarga += jumlah?.times(harga!!) ?: 0
        }

        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        val hargaMenuTotal = totalHarga
        val hargaMenuTotalFormat = formatter.format(hargaMenuTotal)

        _tvTotalHarga.text = hargaMenuTotalFormat.toString()

    }

    override fun onStart() {
        super.onStart()
        val cart = database_cart.cartDao().selectAll()
        adapterC.isiData(cart)
    }

    fun updateTotal(id_user: Int) {
        totalHarga = 0
        var _tvTotalHarga = findViewById<TextView>(R.id.totalHarga)

        val isiCart = database_cart.cartDao().getAllItem(id_user)

        for (i in isiCart) {
            val menu = database_menu.menuDao().getMenu(i.idMenu ?: 0)
            val harga = menu.hargaMenu
            val jumlah = i.jumlahMenu
            totalHarga += jumlah?.times(harga!!) ?: 0
        }

        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        val hargaMenuTotal = totalHarga
        val hargaMenuTotalFormat = formatter.format(hargaMenuTotal)

        _tvTotalHarga.text = hargaMenuTotalFormat.toString()
    }

    companion object {
        const val idUser = "GETID"
    }
}