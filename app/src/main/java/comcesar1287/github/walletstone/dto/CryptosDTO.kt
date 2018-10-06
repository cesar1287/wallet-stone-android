package comcesar1287.github.walletstone.dto

import android.os.Parcelable
import comcesar1287.github.walletstone.database.models.Crypto
import comcesar1287.github.walletstone.database.models.UserWallet
import kotlinx.android.parcel.Parcelize

@Parcelize
class CryptosDTO(var crypto: Crypto? = null,
                 var userWallet: UserWallet? = null) : Parcelable