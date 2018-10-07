package comcesar1287.github.walletstone.database.models

import android.arch.persistence.room.Entity
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "usersWallet", primaryKeys = ["userId", "coinId"])
data class UserWallet(var userId: Long,
                      var coinId: Long,
                      var quantity: Double) : Parcelable