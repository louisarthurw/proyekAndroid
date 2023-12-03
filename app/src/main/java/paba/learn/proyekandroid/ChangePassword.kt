package paba.learn.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import paba.learn.proyekandroid.data.AppDatabase

class ChangePassword : AppCompatActivity() {
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        var _etNewPass = findViewById<EditText>(R.id.etNewPass)
        var _etReinputNewPass = findViewById<EditText>(R.id.etReinputNewPass)
        var _btnConfirmChangePass = findViewById<Button>(R.id.btnConfirmChangePassword)
        var _tvFeedback2 = findViewById<TextView>(R.id.tvFeedback2)

        database = AppDatabase.getDatabase(this)

        _btnConfirmChangePass.setOnClickListener {
            if (_etNewPass.text.toString() != "" && _etReinputNewPass.text.toString() != "") {
                if (_etNewPass.text.toString() != _etReinputNewPass.text.toString()) {
                    _tvFeedback2.text = "Password tidak sesuai!"
                } else {
                    var id = intent.getStringExtra(idUser).toString()
                    var user = database.userDao().getUser(id.toInt())
                    database.userDao().update(user.fullName.toString(), user.email.toString(), user.phoneNumber.toString(), user.address.toString(), _etNewPass.text.toString(), user.balance ?: 0, user.uid ?: 0)
                    Toast.makeText(applicationContext, "Berhasil mengganti password, silahkan login kembali!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ChangePassword, MainActivity::class.java)
                    startActivity(intent)
                }
            } else {
                _tvFeedback2.text = "Ada field yang belum terisi!"
            }
        }
    }

    companion object {
        const val idUser = "id"
    }
}