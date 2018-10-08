package comcesar1287.github.walletstone.api

import comcesar1287.github.walletstone.api.callbacks.CallbackMercadoBitcoin

class APIUtils {

    companion object {
        private const val BASE_URL_MERCADO_BITCOIN = "https://www.mercadobitcoin.net/api/"

        fun getMercadoBitcoinApi(crypto: String): CallbackMercadoBitcoin {
            return APIService.getClient("$BASE_URL_MERCADO_BITCOIN$crypto/").create(CallbackMercadoBitcoin::class.java)
        }
    }
}