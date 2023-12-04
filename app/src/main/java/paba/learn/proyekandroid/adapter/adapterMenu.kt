package paba.learn.proyekandroid.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import paba.learn.proyekandroid.AddEditMenu
import paba.learn.proyekandroid.R
import paba.learn.proyekandroid.data.entity.Menus

class adapterMenu(private val listMenu: MutableList<Menus>) :
    RecyclerView.Adapter<adapterMenu.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

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
        var _tvDeskripsiMenu: TextView = itemView.findViewById(R.id.tvDeskripsiMenu)

        var _btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        var _btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_menu, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMenu.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var menu = listMenu[position]
        holder._tvNamaMenu.setText(menu.namaMenu)
        holder._tvHargaMenu.setText(menu.hargaMenu.toString())
        holder._tvDeskripsiMenu.setText(menu.deskripsiMenu)

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, AddEditMenu::class.java)
            intent.putExtra("idMenu", menu.idMenu)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(menu)
        }
    }
}