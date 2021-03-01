package com.example.bitcointicker.ui.coin_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcointicker.data.entities.Coin
import com.example.bitcointicker.databinding.ItemCoinBinding

class CoinListAdapter : ListAdapter<Coin, CoinListAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var coinListener: CoinListener

    fun setCoinListener(coinListener: CoinListener){
        this.coinListener = coinListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ViewHolder  = ViewHolder.create(
        LayoutInflater.from(parent.context),parent,coinListener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))


    class ViewHolder(val binding: ItemCoinBinding, coinListener: CoinListener) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                coinListener.clickCoin(binding.coin)
            }
        }

        companion object {
            fun create(inflater: LayoutInflater, parent: ViewGroup, coinListener: CoinListener): ViewHolder {
                val itemBinding = ItemCoinBinding.inflate(inflater, parent, false)
                return ViewHolder(
                    itemBinding,
                    coinListener
                )
            }
        }

        fun bind(coin: Coin?) {
            binding.coin = coin
            binding.executePendingBindings()
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Coin>() {
            override fun areItemsTheSame(
                oldItem: Coin,
                newItem: Coin
            ): Boolean = oldItem.coinId == newItem.coinId

            override fun areContentsTheSame(
                oldItem: Coin,
                newItem: Coin
            ): Boolean = oldItem == newItem
        }
    }

    interface CoinListener{
        fun clickCoin(coin: Coin?)
    }

}