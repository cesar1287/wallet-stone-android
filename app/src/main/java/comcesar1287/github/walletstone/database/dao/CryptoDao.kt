package comcesar1287.github.walletstone.database.dao

import android.arch.persistence.room.*
import comcesar1287.github.walletstone.database.models.Crypto

@Dao
interface CryptoDao {

    @Query("SELECT * FROM cryptos") fun getAllCryptos(): List<Crypto>?

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertCryptos(vararg cryptos: Crypto)

    @Update
    fun updateCrypto(crypto: Crypto)

    @Delete fun deleteCrypto(crypto: Crypto)
}