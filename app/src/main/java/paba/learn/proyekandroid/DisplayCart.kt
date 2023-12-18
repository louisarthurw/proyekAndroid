package paba.learn.proyekandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import paba.learn.proyekandroid.adapter.adapterCart
import paba.learn.proyekandroid.adapter.adapterMenuUser
import paba.learn.proyekandroid.data.AppDatabase
import paba.learn.proyekandroid.data.CartDatabase
import paba.learn.proyekandroid.data.MenuDatabase
import paba.learn.proyekandroid.data.entity.Cart
import paba.learn.proyekandroid.data.entity.Menus

class DisplayCart : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var database_menu: MenuDatabase
    private lateinit var database_cart: CartDatabase
    private lateinit var adapterC: adapterCart
    private val arCart: MutableList<Cart> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_cart)

        database = AppDatabase.getDatabase(this)
        database_menu = MenuDatabase.getDatabase(this)
        database_cart = CartDatabase.getDatabase(this)

        val id_user = intent.getStringExtra(idUser)
        var user = database.userDao().getUser(id_user.toString().toInt())

        val _rvCart = findViewById<RecyclerView>(R.id.rvCart)
        adapterC = adapterCart(arCart, id_user.toString())
        _rvCart.layoutManager = LinearLayoutManager(this)
        _rvCart.adapter = adapterC

        adapterC.setOnItemClickCallback(object : adapterCart.OnItemClickCallback {

        })
    }

    override fun onStart() {
        super.onStart()
        val cart = database_cart.cartDao().selectAll()
        adapterC.isiData(cart)
    }

    companion object {
        const val idUser = "GETID"
    }
}