package comcesar1287.github.walletstone.extensions

import java.text.NumberFormat
import java.util.*

fun Double.toBRL(): String {
    val ptBr = Locale("pt", "BR")
    return NumberFormat.getCurrencyInstance(ptBr).format(this)
}

fun Double.toBrazilianFormat(): String {
    val ptBr = Locale("pt", "BR")
    return NumberFormat.getNumberInstance(ptBr).format(this)
}