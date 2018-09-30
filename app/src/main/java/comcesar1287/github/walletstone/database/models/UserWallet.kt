package comcesar1287.github.walletstone.database.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "usersWallet")
data class UserWallet(@PrimaryKey(autoGenerate = true)
                      var id: Long? = null,
                      var userId: Long? = null,
                      var coinId: Long,
                      var value: Double) : Parcelable