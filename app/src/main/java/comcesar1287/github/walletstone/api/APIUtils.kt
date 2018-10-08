package comcesar1287.github.walletstone.api

import comcesar1287.github.walletstone.api.callbacks.CallbackCentralBank
import comcesar1287.github.walletstone.api.callbacks.CallbackMercadoBitcoin

class APIUtils {

    companion object {
        private const val BASE_URL_MERCADO_BITCOIN = "https://www.mercadobitcoin.net/api/"
        private const val BASE_URL_CENTRAL_BANK = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/"

        fun getMercadoBitcoinApi(crypto: String): CallbackMercadoBitcoin {
            return APIService.getClient("$BASE_URL_MERCADO_BITCOIN$crypto/").create(CallbackMercadoBitcoin::class.java)
        }

        fun getCentralBankApi(): CallbackCentralBank {
            return APIService.getClient(BASE_URL_CENTRAL_BANK).create(CallbackCentralBank::class.java)
        }
    }
}