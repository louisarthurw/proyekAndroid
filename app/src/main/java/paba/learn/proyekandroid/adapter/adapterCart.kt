package paba.learn.proyekandroid.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import paba.learn.proyekandroid.AddToCart
import paba.learn.proyekandroid.DetailMenu
import paba.learn.proyekandroid.DisplayCart
import paba.learn.proyekandroid.MainActivity
import paba.learn.proyekandroid.R
import paba.learn.proyekandroid.data.AppDatabase
import paba.learn.proyekandroid.data.CartDatabase
import paba.learn.proyekandroid.data.MenuDatabase
import paba.learn.proyekandroid.data.entity.Cart
import paba.learn.proyekandroid.data.entity.Menus
import java.io.IOException
import java.text.NumberFormat
import java.util.Locale

class adapterCart(private val listCart: MutableList<Cart>, private val idUser: String) :
    RecyclerView.Adapter<adapterCart.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var context: Context
    private lateinit var database_menu: MenuDatabase
    private lateinit var database_cart: CartDatabase

    interface OnItemClickCallback {
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun isiData(list: List<Cart>) {
        listCart.clear()
        listCart.addAll(list)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _ivGambarMenu3: ImageView = itemView.findViewById(R.id.ivGambarMakanan)
        var _tvNamaMenu3: TextView = itemView.findViewById(R.id.namaMenuCart)
        var _tvHargaMenu3: TextView = itemView.findViewById(R.id.hargaMenuCart)
        var _addButtonCart: FloatingActionButton =
            itemView.findViewById(R.id.floatingActionButtonAddCart)
        var _minButtonCart: FloatingActionButton =
            itemView.findViewById(R.id.floatingActionButtonMinusCart)
        var _jumlahMenuCart: TextView = itemView.findViewById(R.id.jumlahMenuCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_cart, parent, false)
        context = parent.context
        database_menu = MenuDatabase.getDatabase(this.context)
        database_cart = CartDatabase.getDatabase(this.context)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listCart.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var color = ContextCompat.getColor(this.context, R.color.white)
        holder._addButtonCart.imageTintList = ColorStateList.valueOf(color)
        holder._minButtonCart.imageTintList = ColorStateList.valueOf(color)
        var cart = listCart[position]
        var menu = database_menu.menuDao().getMenu(cart.idMenu ?: 0)
        var totalHarga: Int

        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        val hargaMenu = menu.hargaMenu
        val hargaMenuFormat = formatter.format(hargaMenu)

        if (isFileInFolder(context, menu.gambarMenu.toString())) {
            val bitmap: Bitmap? = showImage(context, menu.gambarMenu.toString())
            bitmap?.let {
                holder._ivGambarMenu3.setImageBitmap(bitmap)
            }
        } else {
            val bitmap: Bitmap? = showImage(context, "defaultimage.png")
            bitmap?.let {
                holder._ivGambarMenu3.setImageBitmap(bitmap)
            }
        }
        holder._tvNamaMenu3.setText(menu.namaMenu)
        holder._tvHargaMenu3.setText(hargaMenuFormat.toString())
        holder._jumlahMenuCart.setText(cart.jumlahMenu.toString())

        holder._addButtonCart.setOnClickListener {
            var itemID = database_cart.cartDao().getItemID(menu.idMenu ?: 0, idUser.toInt())
            var jumlahMenuDalamCart = holder._jumlahMenuCart.text.toString().toInt()
            jumlahMenuDalamCart++
            database_cart.cartDao().update(jumlahMenuDalamCart, itemID)
            holder._jumlahMenuCart.text = jumlahMenuDalamCart.toString()

            (context as? DisplayCart)?.updateTotal(idUser.toInt())
        }

        holder._minButtonCart.setOnClickListener {
            var itemID = database_cart.cartDao().getItemID(menu.idMenu ?: 0, idUser.toInt())
            var jumlahMenuDalamCart = holder._jumlahMenuCart.text.toString().toInt()
            jumlahMenuDalamCart--
            if (jumlahMenuDalamCart == 0) {
                AlertDialog.Builder(this.context)
                    .setTitle("HAPUS ITEM")
                    .setMessage("APAKAH BENAR ITEM AKAN DIHAPUS DARI CART?")
                    .setNegativeButton(
                        "TIDAK",
                        DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(
                                this.context,
                                "HAPUS AKUN DIBATALKAN",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                    .setPositiveButton(
                        "YA",
                        DialogInterface.OnClickListener { dialog, which ->
                            database_cart.cartDao().deleteItem(itemID)
                            listCart.removeAt(position)
                            notifyItemRemoved(position)
                            (context as? DisplayCart)?.updateTotal(idUser.toInt())
                            Toast.makeText(
                                this.context,
                                "AKUN BERHASIL DIHAPUS",
                                Toast.LENGTH_SHORT
                            ).show()

                        })
                    .show()
            }
            else {
                database_cart.cartDao()
                    .update(jumlahMenuDalamCart, itemID)
                holder._jumlahMenuCart.text = jumlahMenuDalamCart.toString()
                (context as? DisplayCart)?.updateTotal(idUser.toInt())
            }
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

    private fun showImage(context: Context, file: String): Bitmap? {
        return try {
            val inputStream = context.assets?.open(file)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
