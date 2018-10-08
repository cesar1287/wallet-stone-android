package comcesar1287.github.walletstone.api.models

data class Ticker(
        val high: Double,
        val low: Double,
        val vol: Double,
        val last: Double,
        val buy: Double,
        val sell: Double,
        val date: Int
)