package paba.learn.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var _btnLogin = findViewById<Button>(R.id.btnLogin)
        var _btnSignup = findViewById<TextView>(R.id.tvSignup)

        _btnLogin.setOnClickListener {
            val intent = Intent(this@MainActivity, DisplayAllUser::class.java)
            startActivity(intent)
        }

        _btnSignup.setOnClickListener {
            val intent = Intent(this@MainActivity, SignUp::class.java)
            startActivity(intent)
        }
    }
}