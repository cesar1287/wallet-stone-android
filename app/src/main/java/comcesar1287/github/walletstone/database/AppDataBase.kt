package comcesar1287.github.walletstone.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import comcesar1287.github.walletstone.database.dao.CryptoDao
import comcesar1287.github.walletstone.database.dao.UserDao
import comcesar1287.github.walletstone.database.models.Crypto
import comcesar1287.github.walletstone.database.models.User

@Database(version = 1, entities = [User::class, Crypto::class])
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cryptoDao(): CryptoDao
}