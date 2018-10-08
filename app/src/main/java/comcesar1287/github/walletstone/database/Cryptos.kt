package comcesar1287.github.walletstone.database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Cryptos(val cryptoId: Long,
                   val cryptoName: String,
                   val cryptoInitials: String,
                   val cryptoIcon: String,
                   val cryptoValue: Double) : Parcelable {
    REAL(1, "Real", "BRL", "ic_real", 1.0),
    BRITA(2, "Brita", "USD", "ic_coin", 4.05),
    BTC(3, "Bitcoin", "BTC", "ic_bitcoin", 26659.06),
    LTC(4, "Litcoin", "LTC", "ic_litecoin", 245.78),
    BCASH(5, "Bitcoin Cash", "BCH", "ic_bitcoin_cash", 2180.28)
}