package comcesar1287.github.walletstone.database.dao

import android.arch.persistence.room.*
import comcesar1287.github.walletstone.database.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users") fun getAllUsers(): List<User>?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password") fun getUser(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email") fun getUserByEmail(email: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertUsers(vararg users: User)

    @Update
    fun updateUser(user: User)

    @Delete fun deleteUser(user: User)
}