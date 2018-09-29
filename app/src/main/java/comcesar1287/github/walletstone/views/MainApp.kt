package comcesar1287.github.walletstone.views

import android.app.Application
import android.arch.persistence.room.Room
import com.facebook.stetho.Stetho
import comcesar1287.github.walletstone.database.AppDataBase

class MainApp : Application() {

    companion object {
        var database: AppDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()

        //Room
        database = Room.databaseBuilder(this, AppDataBase::class.java, "walletStone").allowMainThreadQueries().build()

        //Stetho
        val initializerBuilder = Stetho.newInitializerBuilder(this)
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
        val initializer = initializerBuilder.build()
        Stetho.initialize(initializer)
    }
}