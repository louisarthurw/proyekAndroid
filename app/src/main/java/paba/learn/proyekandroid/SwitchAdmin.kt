package paba.learn.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class SwitchAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_admin)

        var id = intent.getStringExtra(SwitchAdmin.idUser).toString()
        var _etPasswordAdmin = findViewById<EditText>(R.id.etPasswordAdmin)
        var _btnLoginAdmin = findViewById<Button>(R.id.btnLoginAdmin)
        var _tvFeedbackAdmin = findViewById<TextView>(R.id.tvFeedback3)

        _btnLoginAdmin.setOnClickListener {
            if (_etPasswordAdmin.text.toString() != "") {
                if (_etPasswordAdmin.text.toString() != "admin") {
                    _tvFeedbackAdmin.text = "Password salah!"
                } else {
                    val intent = Intent(this@SwitchAdmin, MainAdmin::class.java).apply {
                        putExtra(MainAdmin.idUser, id)
                    }
                    startActivity(intent)
                }
            } else {
                _tvFeedbackAdmin.text = "Password belum terisi!"
            }
        }
    }

    companion object {
        const val idUser = "id"
    }
}