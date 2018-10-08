package comcesar1287.github.walletstone.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import comcesar1287.github.walletstone.R
import comcesar1287.github.walletstone.adapters.TradeAdapter
import comcesar1287.github.walletstone.database.models.Crypto
import comcesar1287.github.walletstone.preferences.MainPreference
import comcesar1287.github.walletstone.views.MainApp
import kotlinx.android.synthetic.main.fragment_extract.*

class ExtractFragment : Fragment() {

    private var cryptoMap: HashMap<Long, Crypto> = hashMapOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_extract, container, false)
    }

    override fun onResume() {
        super.onResume()

        activity?.let { activity ->
            val tradeDao = MainApp.database?.tradeDao()
            val userDao = MainApp.database?.userDao()

            val email = MainPreference.getUserReference(activity)
            val user = userDao?.getUserByEmail(email)

            val userTradesList = tradeDao?.getAllUserTrades(user?.id!!)

            if (userTradesList!!.isEmpty()) {
                recyclerView.visibility = View.GONE
                noData.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                noData.visibility = View.GONE

                val cryptoDao = MainApp.database?.cryptoDao()
                val cryptoList = cryptoDao?.getAllCryptos()

                cryptoList?.forEach { crypto ->
                    cryptoMap[crypto.id!!] = crypto
                }

                val layoutManager = LinearLayoutManager(activity)
                recyclerView.layoutManager = layoutManager
                val tradeAdapter = TradeAdapter(userTradesList, cryptoMap)
                recyclerView.adapter = tradeAdapter
            }
        }
    }
}
