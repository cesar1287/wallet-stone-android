package comcesar1287.github.walletstone.database.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(@PrimaryKey
                var id: Int,
                @ColumnInfo(name = "name")
                var name: String,
                @ColumnInfo(name = "name")
                var email: String,
                @ColumnInfo(name = "password")
                var password: String) : Parcelable