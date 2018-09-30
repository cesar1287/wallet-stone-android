package comcesar1287.github.walletstone.extensions

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

    fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }