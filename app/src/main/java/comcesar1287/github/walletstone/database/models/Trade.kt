package comcesar1287.github.walletstone.database.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "trades")
data class Trade(@PrimaryKey(autoGenerate = true)
                 var id: Long? = null,
                 var userId: Long,
                 var coinIdIn: Long? = null,
                 var quantityIn: Double,
                 var coinIdOut: Long,
                 var quantityOut: Double) : Parcelable