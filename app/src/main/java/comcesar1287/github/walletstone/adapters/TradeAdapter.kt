package comcesar1287.github.walletstone.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import comcesar1287.github.walletstone.R
import comcesar1287.github.walletstone.database.models.Crypto
import comcesar1287.github.walletstone.database.models.Trade
import comcesar1287.github.walletstone.extensions.toBrazilianFormat
import kotlinx.android.synthetic.main.item_trade.view.*

class TradeAdapter(private var list: List<Trade>, private var map: Map<Long, Crypto>) : RecyclerView.Adapter<TradeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trade, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], map)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(trade: Trade, map: Map<Long, Crypto>) = with(itemView) {
            val coinIn = map[trade.coinIdIn]
            val coinOut = map[trade.coinIdOut]

            itemView.nameCoinIn.text = "${coinIn?.name} (${coinIn?.initials})"
            itemView.quantityCoinIn.text = trade.quantityIn.toBrazilianFormat()

            itemView.nameCoinOut.text = "${coinOut?.name} (${coinOut?.initials})"
            itemView.quantityCoinOut.text = trade.quantityOut.toBrazilianFormat()
        }
    }
}