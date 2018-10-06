package comcesar1287.github.walletstone.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import comcesar1287.github.walletstone.R
import comcesar1287.github.walletstone.adapters.CryptosAdapter
import comcesar1287.github.walletstone.database.models.Crypto
import comcesar1287.github.walletstone.extensions.toBRL
import comcesar1287.github.walletstone.preferences.MainPreference
import comcesar1287.github.walletstone.views.MainApp
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private var valueTotal: Double = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { activity ->
            val cryptoDao = MainApp.database?.cryptoDao()
            val userWalletDao = MainApp.database?.userWalletDao()
            val userDao = MainApp.database?.userDao()

            val email = MainPreference.getUserReference(activity)
            val user = userDao?.getUserByEmail(email)

            user?.id?.let {  userId ->
                val userWalletList = userWalletDao?.getAllUserWalletCryptos(userId)
                val cryptosList = cryptoDao?.getAllCryptos()

                userWalletList?.forEach { userWallet ->
                    cryptosList?.forEach { crypto ->
                        if (userWallet.coinId == crypto.id) {
                            valueTotal += userWallet.quantity * crypto.value
                        }
                    }
                }

                realValue.text = valueTotal.toBRL()

                val layoutManager = LinearLayoutManager(activity)
                recyclerView.layoutManager = layoutManager
                val cryptosAdapter = CryptosAdapter(activity, Crypto.toCryptoDTOList(cryptosList, userWalletList))
                recyclerView.adapter = cryptosAdapter
            }
        }
    }
}
