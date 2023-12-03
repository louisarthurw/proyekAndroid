package paba.learn.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import paba.learn.proyekandroid.data.AppDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = AppDatabase.getDatabase(this)

        var _btnLogin = findViewById<Button>(R.id.btnLogin)
        var _btnSignup = findViewById<TextView>(R.id.tvSignup)

        var _etEmailLogin = findViewById<EditText>(R.id.etEmailLogin)
        var _etPasswordLogin = findViewById<EditText>(R.id.etPasswordLogin)

        var _tvFeedback = findViewById<TextView>(R.id.tvFeedback)

        _btnLogin.setOnClickListener {
            val _cekEmailValid = database.userDao().cekEmailValid(_etEmailLogin.text.toString())
            val _cekLogin = database.userDao()
                .cekLoginValid(_etEmailLogin.text.toString(), _etPasswordLogin.text.toString())

            if (_etEmailLogin.text.toString() != "" && _etPasswordLogin.text.toString() != "") {
                if (_cekEmailValid != null) {
                    if (_cekLogin != null) {
                        val intent = Intent(this@MainActivity, Menu::class.java).apply {
                            putExtra(Menu.idLogin, _cekLogin.uid.toString())
                        }
                        startActivity(intent)
                        _tvFeedback.text = ""
                    } else {
                        _tvFeedback.text = "Password salah!"
                    }
                } else {
                    _tvFeedback.text = "Akun belum terdaftar!"
                }
            } else {
                _tvFeedback.text = "Ada field yang belum diisi!"
            }

        }

        _btnSignup.setOnClickListener {
            val intent = Intent(this@MainActivity, SignUp::class.java)
            startActivity(intent)
        }
    }
}