package comcesar1287.github.walletstone.database.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import comcesar1287.github.walletstone.dto.CryptosDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "cryptos")
data class Crypto(@PrimaryKey(autoGenerate = true)
                  var id: Long? = null,
                  var name: String,
                  var initials: String,
                  var icon: String,
                  var value: Double) : Parcelable {

    companion object {
        fun toCryptoDTOList(cryptosList: List<Crypto>?, userWalletList: List<UserWallet>?): List<CryptosDTO> {
            val cryptosDTOList = mutableListOf<CryptosDTO>()

            cryptosList?.forEach {  crypto ->
                val cryptosDTO = CryptosDTO()

                cryptosDTO.crypto = crypto

                userWalletList?.forEach { userWallet ->
                    if (crypto.id == userWallet.coinId) {
                        cryptosDTO.userWallet = userWallet
                    }
                }

                cryptosDTOList.add(cryptosDTO)
            }

            return cryptosDTOList
        }
    }
}