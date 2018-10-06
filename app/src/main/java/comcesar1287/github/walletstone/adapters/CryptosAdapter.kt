package comcesar1287.github.walletstone.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import comcesar1287.github.walletstone.R
import comcesar1287.github.walletstone.dto.CryptosDTO
import comcesar1287.github.walletstone.extensions.toBRL
import comcesar1287.github.walletstone.extensions.toBrazilianFormat
import comcesar1287.github.walletstone.views.activities.TradeActivity
import kotlinx.android.synthetic.main.item_crypto.view.*

class CryptosAdapter(var context: Context, private var list: List<CryptosDTO>) : RecyclerView.Adapter<CryptosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_crypto, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, list[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(context: Context, cryptosDTO: CryptosDTO) = with(itemView) {
            val resourceId = context.resources.getIdentifier(cryptosDTO.crypto?.icon, "drawable", context.packageName)
            itemView.icon.setImageResource(resourceId)

            itemView.initials.text = cryptosDTO.crypto?.initials

            var quantityCrypto = 0.0

            cryptosDTO.userWallet?.quantity?.let { quantity ->
                itemView.value.text = (cryptosDTO.crypto?.value?.times(quantity))?.toBRL()
                itemView.quantity.text = quantity.toBrazilianFormat()
                quantityCrypto = quantity
            } ?: context.getString(R.string.label_zero_value)

            itemView.price.text = cryptosDTO.crypto?.value?.toBRL()

            itemView.cryptoLayout.setOnClickListener {
                if (quantityCrypto > 0.0) {
                    val intent = Intent(context, TradeActivity::class.java)
                    intent.putExtra("crypto", cryptosDTO)
                    context.startActivity(intent)
                }
            }
        }
    }
}