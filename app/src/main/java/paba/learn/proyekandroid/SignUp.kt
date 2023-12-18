package paba.learn.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import paba.learn.proyekandroid.data.AppDatabase
import paba.learn.proyekandroid.data.entity.Users

class SignUp : AppCompatActivity() {
    private lateinit var _fullName: EditText
    private lateinit var _email: EditText
    private lateinit var _phoneNumber: EditText
    private lateinit var _address: EditText
    private lateinit var _password: EditText
    private lateinit var _reinputPassword: EditText
    private lateinit var _btnCreateAcc: Button
    private lateinit var _btnLogin: TextView
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        _fullName = findViewById(R.id.etFullName)
        _email = findViewById(R.id.etEmail)
        _phoneNumber = findViewById(R.id.etPhoneNumber)
        _address = findViewById(R.id.etAddress)
        _password = findViewById(R.id.etPassword)
        _reinputPassword = findViewById(R.id.etReinputPassword)
        _btnCreateAcc = findViewById(R.id.btnCreateAcc)
        _btnLogin = findViewById(R.id.tvLogin)

        database = AppDatabase.getDatabase(this)

        _btnCreateAcc.setOnClickListener {
            var isUserRegistered = database.userDao().cekEmailValid(_email.text.toString())
            if (_fullName.text.isNotEmpty() && _email.text.isNotEmpty() && _phoneNumber.text.isNotEmpty() && _address.text.isNotEmpty() && _password.text.isNotEmpty() && _reinputPassword.text.isNotEmpty()) {
                if (_password.text.toString().equals(_reinputPassword.text.toString())) {
                    if (isUserRegistered != null) {
                        Toast.makeText(
                            applicationContext,
                            "Akun dengan email " + _email.text.toString() + " sudah terdaftar!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        CoroutineScope(Dispatchers.IO).async {
                            database.userDao().insert(
                                Users(
                                    null,
                                    _fullName.text.toString(),
                                    _email.text.toString(),
                                    _phoneNumber.text.toString(),
                                    _address.text.toString(),
                                    _password.text.toString(),
                                    0
                                )
                            )
                        }
                        Toast.makeText(
                            applicationContext,
                            "Berhasil membuat akun",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@SignUp, MainActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(applicationContext, "Password tidak sesuai", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "Ada field yang belum diisi", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        _btnLogin.setOnClickListener {
            val intent = Intent(this@SignUp, MainActivity::class.java)
            startActivity(intent)
        }
    }
}