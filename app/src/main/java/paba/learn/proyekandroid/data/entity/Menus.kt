package paba.learn.proyekandroid.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Menus(
    @PrimaryKey(autoGenerate = true) var idMenu: Int? = null,
    @ColumnInfo(name = "nama_menu") var namaMenu: String?,
    @ColumnInfo(name = "harga_menu") var hargaMenu: Int?,
    @ColumnInfo(name = "deskripsi_menu") var deskripsiMenu: String?,
    @ColumnInfo(name = "gambar_menu") var gambarMenu: String?,
)
