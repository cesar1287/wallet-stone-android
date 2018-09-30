package comcesar1287.github.walletstone.database.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(@PrimaryKey(autoGenerate = true)
                var id: Long? = null,
                var name: String,
                var email: String,
                var password: String) : Parcelable