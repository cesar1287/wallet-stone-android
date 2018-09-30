package comcesar1287.github.walletstone.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import comcesar1287.github.walletstone.R
import comcesar1287.github.walletstone.dto.CryptosDTO
import kotlinx.android.synthetic.main.item_crypto.view.*

class CryptosAdapter(val context: Context, var list: List<CryptosDTO>) : RecyclerView.Adapter<CryptosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_crypto, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(cryptosDTO: CryptosDTO) = with(itemView) {
            val resourceId = context.resources.getIdentifier(cryptosDTO.crypto?.icon, "drawable",
                    context.packageName)
            itemView.icon.setImageResource(resourceId)

            itemView.initials.text = cryptosDTO.crypto?.initials

            cryptosDTO.userWallet?.quantity?.let { quantity ->
                itemView.value.text = (cryptosDTO.crypto?.value?.times(quantity)).toString()
                itemView.quantity.text = quantity.toString()
            } ?: context.getString(R.string.label_zero_value)

            itemView.price.text = cryptosDTO.crypto?.value.toString()
        }
    }
}