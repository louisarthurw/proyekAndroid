package paba.learn.proyekandroid.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import paba.learn.proyekandroid.AddEditMenu
import paba.learn.proyekandroid.DetailMenu
import paba.learn.proyekandroid.MainAdmin
import paba.learn.proyekandroid.Menu
import paba.learn.proyekandroid.R
import paba.learn.proyekandroid.data.CartDatabase
import paba.learn.proyekandroid.data.entity.Menus
import java.io.IOException
import java.text.NumberFormat
import java.util.Locale

class adapterMenu(private val listMenu: MutableList<Menus>, private val idUser: String) :
    RecyclerView.Adapter<adapterMenu.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var context: Context
    private lateinit var database_cart: CartDatabase

    interface OnItemClickCallback {
        fun delData(dtmenu: Menus)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun isiData(list: List<Menus>) {
        listMenu.clear()
        listMenu.addAll(list)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _ivGambarMenu: ImageView = itemView.findViewById(R.id.ivGambarMenu)
        var _tvNamaMenu: TextView = itemView.findViewById(R.id.tvNamaMenu)
        var _tvHargaMenu: TextView = itemView.findViewById(R.id.tvHargaMenu)

        var _btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        var _btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_menu, parent, false)
        context = parent.context
        database_cart = CartDatabase.getDatabase(context)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMenu.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var menu = listMenu[position]
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        val hargaMenu = formatter.format(menu.hargaMenu)

        if (isFileInFolder(context, menu.gambarMenu.toString())) {
            val bitmap: Bitmap? = showImage(context, menu.gambarMenu.toString())
            bitmap?.let {
                holder._ivGambarMenu.setImageBitmap(bitmap)
            }
        } else {
            val bitmap: Bitmap? = showImage(context, "defaultimage.png")
            bitmap?.let {
                holder._ivGambarMenu.setImageBitmap(bitmap)
            }
        }
        holder._tvNamaMenu.setText(menu.namaMenu)
        holder._tvHargaMenu.setText(hargaMenu.toString())

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, AddEditMenu::class.java)
            intent.putExtra("idMenu", menu.idMenu)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(menu)
            val id_menu = menu.idMenu
            database_cart.cartDao().deleteMenu(id_menu ?: 0)
        }

        holder._ivGambarMenu.setOnClickListener {
            val intent = Intent(it.context, DetailMenu::class.java)
            intent.putExtra("idUser", idUser)
            intent.putExtra("idMenu", menu.idMenu)
            it.context.startActivity(intent)
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
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}