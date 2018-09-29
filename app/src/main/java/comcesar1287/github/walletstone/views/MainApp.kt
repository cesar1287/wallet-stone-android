package comcesar1287.github.walletstone.views

import android.app.Application
import android.arch.persistence.room.Room
import comcesar1287.github.walletstone.database.AppDataBase

class MainApp : Application() {

    companion object {
        var database: AppDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, AppDataBase::class.java, "walletStone").build()
    }
}