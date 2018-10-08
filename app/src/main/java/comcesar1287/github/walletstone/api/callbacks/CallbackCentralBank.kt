package comcesar1287.github.walletstone.api.callbacks

import comcesar1287.github.walletstone.api.models.CentralBank
import comcesar1287.github.walletstone.api.models.MercadoBitcoin
import retrofit2.Call
import retrofit2.http.GET

interface CallbackCentralBank {

    @GET("CotacaoMoedaDia(moeda=@moeda,dataCotacao=@dataCotacao)?%40moeda=%27USD%27&%40dataCotacao=%2704-04-2018%27&%24format=json")
    fun getDataCryptoCentralBank(): Call<CentralBank>
}