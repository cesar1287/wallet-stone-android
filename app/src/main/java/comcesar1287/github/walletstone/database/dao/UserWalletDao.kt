package comcesar1287.github.walletstone.database.dao

import android.arch.persistence.room.*
import comcesar1287.github.walletstone.database.models.UserWallet

@Dao
interface UserWalletDao {

    @Query("SELECT * FROM usersWallet WHERE userId = :userId") fun getAllUserWalletCryptos(userId: Long): List<UserWallet>?

    @Query("SELECT * FROM usersWallet WHERE userId = :userId AND coinId = :coinId") fun getSpecificUserCoin(userId: Long,
                                                                                                                  coinId: Long): UserWallet

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertUserCrypto(vararg userWallet: UserWallet)

    @Update
    fun updateUser(userWallet: UserWallet)

    @Delete fun deleteUser(userWallet: UserWallet)
}