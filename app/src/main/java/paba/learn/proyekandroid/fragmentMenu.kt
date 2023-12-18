package paba.learn.proyekandroid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import paba.learn.proyekandroid.adapter.adapterMenu
import paba.learn.proyekandroid.adapter.adapterMenuUser
import paba.learn.proyekandroid.data.AppDatabase
import paba.learn.proyekandroid.data.MenuDatabase
import paba.learn.proyekandroid.data.entity.Cart
import paba.learn.proyekandroid.data.entity.Menus

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragmentMenu.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentMenu : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var database: AppDatabase
    private lateinit var database_menu: MenuDatabase
    private lateinit var adapterMU: adapterMenuUser
    private val arMenuUser: MutableList<Menus> = mutableListOf()
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = AppDatabase.getDatabase(requireContext())
        database_menu = MenuDatabase.getDatabase(requireContext())

        if (arguments != null) {
            id = arguments?.getString("id").toString()
            var userLogin = database.userDao().getUser(id.toInt())
        }

        val _rvMenus = view.findViewById<RecyclerView>(R.id.rvMenuUser)
        adapterMU = adapterMenuUser(arMenuUser, id)
        _rvMenus.layoutManager = LinearLayoutManager(requireContext())
        _rvMenus.adapter = adapterMU

        adapterMU.setOnItemClickCallback(object : adapterMenuUser.OnItemClickCallback {

        })
    }

    override fun onStart() {
        super.onStart()
        val menu = database_menu.menuDao().selectAll()
        adapterMU.isiData(menu)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragmentMenu.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragmentMenu().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}