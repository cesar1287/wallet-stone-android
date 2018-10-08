package comcesar1287.github.walletstone.views.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import comcesar1287.github.walletstone.R
import comcesar1287.github.walletstone.database.models.Crypto
import comcesar1287.github.walletstone.database.models.Trade
import comcesar1287.github.walletstone.database.models.UserWallet
import comcesar1287.github.walletstone.dto.CryptosDTO
import comcesar1287.github.walletstone.extensions.toBRL
import comcesar1287.github.walletstone.extensions.toBrazilianFormat
import comcesar1287.github.walletstone.views.MainApp
import kotlinx.android.synthetic.main.activity_trade.*

class TradeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, TextWatcher {

    private var cryptoList: List<Crypto>? = null
    private var cryptoMap: HashMap<String, Crypto> = hashMapOf()
    private var cryptoNameList: MutableList<String> = mutableListOf()
    private var cryptosDTO = CryptosDTO()
    private var cryptoSelected: Crypto? = null
    private var quantityCoinInDouble: Double? = 0.0
    private var valueCoinInDouble: Double? = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trade)

        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupUI()

        trade.setOnClickListener {
            val quantityTyped = quantityCoinOut.text.toString()

            if (quantityTyped.isBlank()) {
                Snackbar.make(trade, getText(R.string.error_all_fields_required), Snackbar.LENGTH_SHORT).show()
            } else {
                val quantityCoinOutDouble = quantityTyped.toDouble()
                val valueCoinOutDouble = cryptoSelected?.value
                val totalCoinOut = quantityCoinOutDouble * valueCoinOutDouble!!

                val quantityCoinInDouble = cryptosDTO.userWallet?.quantity
                val valueCoinInDouble = cryptosDTO.crypto?.value
                val totalCoinIn = quantityCoinInDouble!! * valueCoinInDouble!!

                if (totalCoinOut > totalCoinIn) {
                    Snackbar.make(trade, getText(R.string.error_invalid_trade), Snackbar.LENGTH_SHORT).show()
                } else {
                    val userWalletDao = MainApp.database?.userWalletDao()
                    val tradeDao = MainApp.database?.tradeDao()

                    val exchangeRate = valueCoinOutDouble / valueCoinInDouble
                    val totalCoinsBurned = quantityCoinOutDouble * exchangeRate

                    val coinOutDb = userWalletDao?.getSpecificUserCoin(cryptosDTO.userWallet?.userId!!, cryptoSelected?.id!!)
                    val userWallet = UserWallet(userId = cryptosDTO.userWallet?.userId!!, coinId = cryptoSelected?.id!!, quantity = quantityCoinOutDouble
                            .plus(coinOutDb?.quantity.let { it } ?: 0.0))
                    userWalletDao?.insertUserCrypto(userWallet)

                    val coinInDb = userWalletDao?.getSpecificUserCoin(cryptosDTO.userWallet?.userId!!, cryptosDTO.userWallet?.coinId!!)
                    val userWalletCoinIn = UserWallet(userId = cryptosDTO.userWallet?.userId!!, coinId = cryptosDTO.userWallet?.coinId!!,
                            quantity = (coinInDb!!.quantity - totalCoinsBurned) )
                    userWalletDao.deleteUser(cryptosDTO.userWallet!!)
                    userWalletDao.insertUserCrypto(userWalletCoinIn)

                    val trade = Trade(userId = cryptosDTO.userWallet?.userId!!, coinIdIn = cryptoSelected?.id!!, quantityIn = quantityCoinOutDouble,
                            coinIdOut = cryptosDTO.crypto?.id!!, quantityOut = totalCoinsBurned)
                    tradeDao?.insertUserTrade(trade)

                    Toast.makeText(this, getString(R.string.trade_has_been_succssfully), Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun setupUI() {
        cryptosDTO = intent.getParcelableExtra("crypto")

        quantityCoinInDouble = cryptosDTO.userWallet?.quantity
        valueCoinInDouble = cryptosDTO.crypto?.value

        coinIn.text = "${cryptosDTO.crypto?.name}(${cryptosDTO.crypto?.initials})"
        quantityCoinIn.text = quantityCoinInDouble?.toBrazilianFormat()
        valueCoinIn.text = valueCoinInDouble?.toBRL()

        valueCoinInDouble?.let {  value ->
            totalCoinIn.text = (quantityCoinInDouble?.times(value))?.toBRL()
        }

        total.text = 0.0.toBRL()

        val cryptoDao = MainApp.database?.cryptoDao()
        cryptoList = cryptoDao?.getAllCryptos()

        cryptoList?.forEach { crypto ->
            if (cryptosDTO.crypto?.name != crypto.name) {
                cryptoNameList.add(crypto.name)
                cryptoMap[crypto.name] = crypto
            }
        }

        val adapterTrade = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cryptoNameList)
        adapterTrade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCoinIn?.adapter = adapterTrade
        spinnerCoinIn?.onItemSelectedListener = this

        quantityCoinOut.addTextChangedListener(this)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //TODO
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // On selecting a spinner item
        val item = parent?.getItemAtPosition(position).toString()
        cryptoSelected = cryptoMap[item]

        valueCoinOut.text = cryptoSelected?.value?.toBRL()
    }

    override fun afterTextChanged(s: Editable?) {
        //TODO
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //TODO
    }

    override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
        val quantity = string.toString().toDoubleOrNull()

        quantity?.let { quantityNonNull ->
            val totalValue = cryptoSelected?.value?.times(quantityNonNull)

            total.text = totalValue?.toBRL()
        } ?: run {
            total.text = 0.0.toBRL()
        }
    }
}
