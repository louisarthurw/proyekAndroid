package paba.learn.proyekandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import paba.learn.proyekandroid.data.AppDatabase
import paba.learn.proyekandroid.fragmentProfile

class TopUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up)

        var id = intent.getStringExtra(idUser).toString()
        var database = AppDatabase.getDatabase(this)
        val saldoAwal = (database.userDao().getUser(id.toInt()).balance ?: 0).toInt()

        var _etNominal = findViewById<EditText>(R.id.etNominal)
        var _btnTambahSaldo = findViewById<Button>(R.id.btnTambahSaldo)

        _btnTambahSaldo.setOnClickListener {
            val nominalTopup = _etNominal.text.toString().toInt()
            if (nominalTopup == 0) {
                Toast.makeText(this,"Silahkan enter nominal yang benar! ", Toast.LENGTH_SHORT).show()
            } else {
                val saldoAkhir = saldoAwal + nominalTopup

                database.userDao().updateBalance(saldoAkhir, id.toInt())

                Toast.makeText(this,"Berhasil top up! ", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    companion object {
        const val idUser = "id"
    }
}