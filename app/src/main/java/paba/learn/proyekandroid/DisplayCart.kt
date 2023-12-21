package paba.learn.proyekandroid

import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import paba.learn.proyekandroid.adapter.adapterCart
import paba.learn.proyekandroid.adapter.adapterMenuUser
import paba.learn.proyekandroid.data.AppDatabase
import paba.learn.proyekandroid.data.CartDatabase
import paba.learn.proyekandroid.data.HistoryDatabase
import paba.learn.proyekandroid.data.MenuDatabase
import paba.learn.proyekandroid.data.entity.Cart
import paba.learn.proyekandroid.data.entity.History
import paba.learn.proyekandroid.data.entity.Menus
import java.text.NumberFormat
import java.util.Locale

class DisplayCart : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var database_menu: MenuDatabase
    private lateinit var database_cart: CartDatabase
    private lateinit var database_history: HistoryDatabase
    private lateinit var adapterC: adapterCart
    private val arCart: MutableList<Cart> = mutableListOf()
    var totalHarga = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_cart)

        var _tvTotalHarga = findViewById<TextView>(R.id.totalHarga)
        var _btnCheckout = findViewById<Button>(R.id.buttonCheckout)

        database = AppDatabase.getDatabase(this)
        database_menu = MenuDatabase.getDatabase(this)
        database_cart = CartDatabase.getDatabase(this)
        database_history = HistoryDatabase.getDatabase(this)

        val id_user = intent.getStringExtra(idUser)

        val isiCart = database_cart.cartDao().getAllItem(id_user.toString().toInt())
        Log.d("id_user", id_user.toString())
        Log.d("isi cart", isiCart.toString())

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

        _btnCheckout.setOnClickListener {
            val saldoUser = database.userDao().getUser(id_user.toString().toInt()).balance ?: 0
            Log.d("saldo sebelum co", saldoUser.toString())
            Log.d("nominal co", totalHarga.toString())

            AlertDialog.Builder(this)
                .setTitle("CHECKOUT")
                .setMessage("APAKAH ANDA YAKIN AKAN CHECKOUT?")
                .setNegativeButton(
                    "TIDAK",
                    DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(
                            this,
                            "Batal checkout!",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                .setPositiveButton(
                    "YA",
                    DialogInterface.OnClickListener { dialog, which ->
                        if (totalHarga > saldoUser) {
                            Toast.makeText(this, "Saldo tidak cukup, silahkan top up terlebih dahulu!", Toast.LENGTH_SHORT).show()
                        } else {
                            val saldoSetelahCO = saldoUser - totalHarga
                            database_cart.cartDao().checkOut(id_user.toString().toInt())
                            database.userDao().updateBalance(saldoSetelahCO, id_user.toString().toInt())

                            for (i in isiCart) {
                                val jumlah = i.jumlahMenu ?: 0
                                val jumlahDipesan = database_history.historyDao().getJumlahDipesan(i.idMenu ?: 0)

                                Log.d("jumlah di cart", jumlah.toString())
                                Log.d("jumlah di database history", jumlahDipesan.toString())

                                if (jumlahDipesan != null) {
                                    var jumlahBaru = jumlahDipesan + jumlah
                                    database_history.historyDao().update(jumlahBaru, i.idMenu ?: 0)
                                } else {
                                    database_history.historyDao().insert(History(i.idMenu, i.jumlahMenu))
                                }
                            }

                            Toast.makeText(this, "Berhasil checkout!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    })
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        val id_userr = intent.getStringExtra(idUser).toString()
        val cart = database_cart.cartDao().getAllItem(id_userr.toInt())
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