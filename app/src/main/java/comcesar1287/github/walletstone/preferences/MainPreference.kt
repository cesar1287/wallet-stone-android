package comcesar1287.github.walletstone.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import comcesar1287.github.walletstone.utils.USER_PREFERENCE

class MainPreference {

    companion object {
        fun cleanPreference(context: Context) {
            context.let {
                PreferenceManager.getDefaultSharedPreferences(it).edit().clear().apply()
            }
        }

        fun setUserReference(context: Context, userEmail: String) {
            context.let {
                val editor: SharedPreferences.Editor = PreferenceManager.getDefaultSharedPreferences(it).edit()
                editor.putString(USER_PREFERENCE, userEmail)
                editor.apply()
            }
        }

        fun getUserReference(context: Context): String {
            return context.let {
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                sharedPreferences.getString(USER_PREFERENCE, "")
            } ?: ""
        }
    }
}