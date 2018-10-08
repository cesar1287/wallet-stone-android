package comcesar1287.github.walletstone.database.dao

import android.arch.persistence.room.*
import comcesar1287.github.walletstone.database.models.Trade

@Dao
interface TradeDao {

    @Query("SELECT * FROM trades WHERE userId = :userId ORDER BY id DESC") fun getAllUserTrades(userId: Long): List<Trade>?

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertUserTrade(vararg trade: Trade)

    @Update
    fun updateTrade(trade: Trade)

    @Delete fun deleteTrade(trade: Trade)
}