package paba.learn.proyekandroid.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History (
    @PrimaryKey(autoGenerate = true) var idMenu: Int? = null,
    @ColumnInfo(name = "jumlah_dipesan") var jumlahDipesan: Int?
)