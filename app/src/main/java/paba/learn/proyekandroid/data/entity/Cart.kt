package paba.learn.proyekandroid.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Cart(
    @PrimaryKey(autoGenerate = true) var idCart: Int? = null,
    @ColumnInfo(name = "id_menu") var idMenu: Int?,
    @ColumnInfo(name = "jumlah_menu") var jumlahMenu: Int?,
    @ColumnInfo(name = "id_user") var idUser: Int?,
)
