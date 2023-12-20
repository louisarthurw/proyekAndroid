package paba.learn.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val mBundle = Bundle()
        var idLogin = intent.getStringExtra(idLogin)

        var _header: TextView = findViewById(R.id.textViewHeader)
        var _cart: ImageView = findViewById(R.id.btnCart)
        var _navbarHome: ImageView = findViewById(R.id.navbarHome)
        var _navbarMenu: ImageView = findViewById(R.id.navbarMenu)
        var _navbarProfile: ImageView = findViewById(R.id.navbarProfile)

        val mfragmentHome = fragmentHome()
        val mfragmentMenu = fragmentMenu()
        val mfragmentProfile = fragmentProfile()

        mBundle.putString("id", idLogin)
        mfragmentHome.arguments = mBundle

        val mFragmentManager = supportFragmentManager
        mFragmentManager.findFragmentByTag(fragmentHome::class.java.simpleName)
        mFragmentManager.beginTransaction().add(R.id.frameContainer, mfragmentHome, fragmentHome::class.java.simpleName).commit()

        _navbarHome.setOnClickListener {
            _header.text = "HOME"
            _cart.visibility = View.VISIBLE

            mBundle.putString("id", idLogin)
            mfragmentHome.arguments = mBundle

            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mfragmentHome, fragmentHome::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        _navbarMenu.setOnClickListener {
            _header.text = "MENU"
            _cart.visibility = View.VISIBLE

            mBundle.putString("id", idLogin)
            mfragmentMenu.arguments = mBundle

            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mfragmentMenu, fragmentMenu::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        _navbarProfile.setOnClickListener {
            _header.text = "ACCOUNT"
            _cart.visibility = View.GONE

            mBundle.putString("id", idLogin)
            mfragmentProfile.arguments = mBundle

            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mfragmentProfile, fragmentProfile::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        _cart.setOnClickListener{
            val intent = Intent(this@Menu, DisplayCart::class.java).apply {
                putExtra(DisplayCart.idUser, idLogin)
            }
            startActivity(intent)
        }
    }

    companion object {
        const val idLogin = "id"
    }
}