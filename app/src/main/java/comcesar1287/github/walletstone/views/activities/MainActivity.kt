package comcesar1287.github.walletstone.views.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import comcesar1287.github.walletstone.R
import comcesar1287.github.walletstone.api.APIUtils
import comcesar1287.github.walletstone.api.models.CentralBank
import comcesar1287.github.walletstone.api.models.MercadoBitcoin
import comcesar1287.github.walletstone.database.Cryptos
import comcesar1287.github.walletstone.database.models.Crypto
import comcesar1287.github.walletstone.database.models.UserWallet
import comcesar1287.github.walletstone.extensions.inTransaction
import comcesar1287.github.walletstone.preferences.MainPreference
import comcesar1287.github.walletstone.views.MainApp
import comcesar1287.github.walletstone.views.fragments.DashboardFragment
import comcesar1287.github.walletstone.views.fragments.ExtractFragment
import comcesar1287.github.walletstone.views.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        supportFragmentManager.inTransaction {
            replace(R.id.content, DashboardFragment())
        }

        if (MainPreference.getUserReference(this).isBlank()) {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        } else {
            //Setup cryptos app
            //[START]
            val cryptoDao = MainApp.database?.cryptoDao()
            Cryptos.values().forEach { cryptos ->
                val crypto = Crypto(cryptos.cryptoId, cryptos.cryptoName, cryptos.cryptoInitials, cryptos.cryptoIcon, cryptos.cryptoValue)
                cryptoDao?.insertCryptos(crypto)

                APIUtils.getMercadoBitcoinApi(cryptos.cryptoInitials).getDataCryptoMercadoBitcoin().enqueue(object : Callback<MercadoBitcoin>{
                    override fun onFailure(call: Call<MercadoBitcoin>, t: Throwable) {
                        //TODO
                    }

                    override fun onResponse(call: Call<MercadoBitcoin>, response: Response<MercadoBitcoin>) {
                        if (response.isSuccessful) {
                            val initials = arrayListOf<String>()
                            val dataStr = response.raw().request().url().toString()
                            val pattern = Pattern.compile("[A-Z][A-Z]+")
                            val matcher = pattern.matcher(dataStr)

                            while (matcher.find()) {
                                initials.add(matcher.group())
                            }

                            val mercadoBitcoin = response.body()
                            cryptoDao?.updateValueCrypto(mercadoBitcoin?.ticker?.sell!!, initials[0])
                        }
                    }

                })

                if (cryptos.cryptoInitials == "USD") {
                    APIUtils.getCentralBankApi().getDataCryptoCentralBank().enqueue(object : Callback<CentralBank>{
                        override fun onFailure(call: Call<CentralBank>, t: Throwable) {
                            //TODO
                        }

                        override fun onResponse(call: Call<CentralBank>, response: Response<CentralBank>) {
                            if (response.isSuccessful) {
                                val centralBank = response.body()

                                cryptoDao?.updateValueCrypto(centralBank!!.value[0].cotacaoVenda, "USD")
                            }
                        }

                    })
                }
            }
            //[END]

            //Setup user wallet first time
            //[START]
            val userDao = MainApp.database?.userDao()
            val userWalletDao = MainApp.database?.userWalletDao()

            val email = MainPreference.getUserReference(this)
            val user = userDao?.getUserByEmail(email)

            user?.id?.let { userId ->
                userWalletDao?.getAllUserWalletCryptos(userId)?.let { userWalletList ->
                    if (userWalletList.isEmpty()) {
                        val userWallet = UserWallet(userId = userId, coinId = 1, quantity = 100000.0)
                        userWalletDao.insertUserCrypto(userWallet)
                    }
                }
            }
            //[END]
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_dashboard -> {
                supportFragmentManager.inTransaction {
                    replace(R.id.content, DashboardFragment())
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_extract -> {
                supportFragmentManager.inTransaction {
                    replace(R.id.content, ExtractFragment())
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                supportFragmentManager.inTransaction {
                    replace(R.id.content, ProfileFragment())
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
