package paba.learn.proyekandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import paba.learn.proyekandroid.adapter.adapterHistory
import paba.learn.proyekandroid.adapter.adapterMenu
import paba.learn.proyekandroid.data.HistoryDatabase
import paba.learn.proyekandroid.data.MenuDatabase
import paba.learn.proyekandroid.data.entity.History
import paba.learn.proyekandroid.data.entity.Menus

class History : AppCompatActivity() {
    private lateinit var database: HistoryDatabase
    private lateinit var adapterH: adapterHistory
    private val arHistory: MutableList<History> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        database = HistoryDatabase.getDatabase(this)
        var id_user = intent.getStringExtra(MainAdmin.idUser).toString()

        var _rvHistory = findViewById<RecyclerView>(R.id.rvHistory)
        adapterH = adapterHistory(arHistory, id_user)
        _rvHistory.layoutManager = LinearLayoutManager(this)
        _rvHistory.adapter = adapterH
    }

    override fun onStart() {
        super.onStart()
        val history = database.historyDao().selectAll()
        adapterH.isiData(history)
    }

    companion object {
        const val idUser = "id"
    }
}