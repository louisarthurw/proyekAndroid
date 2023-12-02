package paba.learn.proyekandroid

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import paba.learn.proyekandroid.adapter.UserAdapter
import paba.learn.proyekandroid.data.AppDatabase
import paba.learn.proyekandroid.data.entity.Users

class DisplayAllUser : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var listUsers = mutableListOf<Users>()
    private lateinit var adapterUsers: UserAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_all_user)
        var _btnBack = findViewById<Button>(R.id.btnBackFromDisplayAllUsers)

        recyclerView = findViewById(R.id.rvUsers)

        database = AppDatabase.getDatabase(this)
        adapterUsers = UserAdapter(listUsers)

        recyclerView.adapter = adapterUsers
        recyclerView.layoutManager = LinearLayoutManager(this)

        _btnBack.setOnClickListener {
            val intent = Intent(this@DisplayAllUser, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).async {
            val user = database.userDao().selectAll()
            adapterUsers.isiData(user)
        }
    }

//    override fun onResume() {
//        super.onResume()
//        getData()
//    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun getData() {
//        listUsers.clear()
//        listUsers.addAll(database.userDao().getAll())
//        adapterUsers.notifyDataSetChanged()
//    }
}