package comcesar1287.github.walletstone.api.models

data class Value(
        val paridadeCompra: Double,
        val paridadeVenda: Double,
        val cotacaoCompra: Double,
        val cotacaoVenda: Double,
        val dataHoraCotacao: String,
        val tipoBoletim: String
)