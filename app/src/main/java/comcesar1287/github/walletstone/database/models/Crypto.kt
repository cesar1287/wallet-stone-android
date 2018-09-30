package comcesar1287.github.walletstone.database.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "cryptos")
data class Crypto(@PrimaryKey(autoGenerate = true)
                var id: Int? = null,
                var name: String,
                var initials: String,
                var icon: String,
                var value: Double) : Parcelable