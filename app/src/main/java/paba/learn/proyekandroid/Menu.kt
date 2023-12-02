package paba.learn.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        var _header: TextView = findViewById(R.id.textViewHeader)
        var _navbarHome: ImageView = findViewById(R.id.navbarHome)
        var _navbarMenu: ImageView = findViewById(R.id.navbarMenu)
        var _navbarProfile: ImageView = findViewById(R.id.navbarProfile)

        val mfragmentHome = fragmentHome()
        val mfragmentMenu = fragmentMenu()
        val mfragmentProfile = fragmentProfile()

        val mFragmentManager = supportFragmentManager
        mFragmentManager.findFragmentByTag(fragmentHome::class.java.simpleName)
        mFragmentManager.beginTransaction().add(R.id.frameContainer, mfragmentHome, fragmentHome::class.java.simpleName).commit()

        _navbarHome.setOnClickListener {
            _header.text = "HOME"
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mfragmentHome, fragmentHome::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        _navbarMenu.setOnClickListener {
            _header.text = "MENU"
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mfragmentMenu, fragmentMenu::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        _navbarProfile.setOnClickListener {
            _header.text = "PROFILE"
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mfragmentProfile, fragmentProfile::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }
}