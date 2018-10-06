package comcesar1287.github.walletstone.views.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import comcesar1287.github.walletstone.R
import comcesar1287.github.walletstone.database.models.Crypto
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
            val quantityCoinOutDouble = quantityCoinOut.text.toString().toDouble()
            val valueCoinOutDouble = cryptoSelected?.value


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
