package comcesar1287.github.walletstone.api.callbacks

import comcesar1287.github.walletstone.api.models.MercadoBitcoin
import retrofit2.Call
import retrofit2.http.GET

interface CallbackMercadoBitcoin {

    @GET("ticker/")
    fun getDataCryptoMercadoBitcoin(): Call<MercadoBitcoin>
}