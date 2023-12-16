package paba.learn.proyekandroid

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import paba.learn.proyekandroid.data.AppDatabase
import java.text.NumberFormat
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragmentProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentProfile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var database: AppDatabase

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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var _namaUser = view.findViewById<TextView>(R.id.namaUser)
        var _emailUser = view.findViewById<TextView>(R.id.emailUser)
        var _alamatUser = view.findViewById<TextView>(R.id.alamatUser)
        var _noTelpUser = view.findViewById<TextView>(R.id.noTelpUser)
        var _saldoUser = view.findViewById<TextView>(R.id.saldoUser)

        var _btnTopup = view.findViewById<Button>(R.id.btnTopup)
        var _btnChangePassword = view.findViewById<Button>(R.id.btnChangePassword)
        var _btnSwitchAdmin = view.findViewById<Button>(R.id.btnSwitchAdmin)
        var _btnLogout = view.findViewById<Button>(R.id.btnLogout)
        var _btnDeleteAcc = view.findViewById<Button>(R.id.btnDeleteAcc)

        database = AppDatabase.getDatabase(requireContext())

        val id = arguments?.getString("id").toString()
        var userLogin = database.userDao().getUser(id.toInt())

        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        val saldo = formatter.format(userLogin.balance)

        _namaUser.text = userLogin.fullName
        _emailUser.text = userLogin.email
        _alamatUser.text = userLogin.address
        _noTelpUser.text = userLogin.phoneNumber
        _saldoUser.text = saldo.toString()

        _btnChangePassword.setOnClickListener {
            val intent = Intent(requireContext(), ChangePassword::class.java).apply {
                putExtra(ChangePassword.idUser, id)
            }
            startActivity(intent)
        }

        _btnSwitchAdmin.setOnClickListener {
            val intent = Intent(requireContext(), SwitchAdmin::class.java).apply {
                putExtra(SwitchAdmin.idUser, id)
            }
            startActivity(intent)
        }

        _btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            requireActivity().finish()
            startActivity(intent)
        }

        _btnDeleteAcc.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("HAPUS AKUN")
                .setMessage("APAKAH BENAR AKUN AKAN DIHAPUS?")
                .setNegativeButton(
                    "TIDAK",
                    DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(
                            requireContext(),
                            "HAPUS AKUN DIBATALKAN",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                .setPositiveButton(
                    "YA",
                    DialogInterface.OnClickListener { dialog, which ->
                        database.userDao().deleteUser(id.toInt())
                        Toast.makeText(
                            requireContext(),
                            "AKUN BERHASIL DIHAPUS",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                    })
                .show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragmentProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragmentProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}