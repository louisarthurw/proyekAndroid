package paba.learn.proyekandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import paba.learn.proyekandroid.data.entity.Users
import paba.learn.proyekandroid.R;

class UserAdapter(private var listUser: MutableList<Users>): RecyclerView.Adapter<UserAdapter.ListViewHolder> (){
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(dtuser: Users)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun isiData(list: List<Users>) {
        listUser.clear()
        listUser.addAll(list)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var _fullName: TextView = itemView.findViewById(R.id.full_name)
        var _email: TextView = itemView.findViewById(R.id.email)
        var _phoneNumber: TextView = itemView.findViewById(R.id.phone_number)
        var _address: TextView = itemView.findViewById(R.id.address)
        var _password: TextView = itemView.findViewById(R.id.password)
        var _balance: TextView = itemView.findViewById(R.id.balance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  listUser.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder._fullName.text = listUser[position].fullName
        holder._email.text = listUser[position].email
        holder._phoneNumber.text = listUser[position].phoneNumber
        holder._address.text = listUser[position].address
        holder._password.text = listUser[position].password
        holder._balance.text = listUser[position].balance.toString()
    }
}