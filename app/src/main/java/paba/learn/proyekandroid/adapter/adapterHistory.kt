package paba.learn.proyekandroid.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import paba.learn.proyekandroid.History
import paba.learn.proyekandroid.R
import paba.learn.proyekandroid.data.HistoryDatabase
import paba.learn.proyekandroid.data.MenuDatabase

class adapterHistory(private val listHistory: MutableList<paba.learn.proyekandroid.data.entity.History>, private val idUser: String) :
    RecyclerView.Adapter<adapterHistory.ListViewHolder>() {
    private lateinit var context: Context
    private lateinit var database_menu: MenuDatabase
    private lateinit var database_history: HistoryDatabase
    fun isiData(list: List<paba.learn.proyekandroid.data.entity.History>) {
        listHistory.clear()
        listHistory.addAll(list)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvNama = itemView.findViewById<TextView>(R.id.tvNamaHistory)
        var _tvJumlah = itemView.findViewById<TextView>(R.id.tvJumlahDipesanHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_history, parent, false)
        context = parent.context
        database_history = HistoryDatabase.getDatabase(context)
        database_menu = MenuDatabase.getDatabase(context)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var history = listHistory[position]
        Log.d("list history", listHistory.toString())
        var namaMenu = database_menu.menuDao().getNamaMenu(history.idMenu ?: 0)

        holder._tvNama.setText(namaMenu)
        holder._tvJumlah.setText("Jumlah dipesan: "  + (history.jumlahDipesan ?: 0).toString())
    }
}