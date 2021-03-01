package com.example.bitcointicker.ui.coin_fav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcointicker.data.entities.FavCoin
import com.example.bitcointicker.databinding.ItemFavCoinBinding

class FavouriteCoinListAdapter : ListAdapter<FavCoin, FavouriteCoinListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ViewHolder  = ViewHolder.create(
        LayoutInflater.from(parent.context),parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))


    class ViewHolder(val binding: ItemFavCoinBinding) : RecyclerView.ViewHolder(binding.root){
        companion object {
            fun create(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
                val itemBinding = ItemFavCoinBinding.inflate(inflater, parent, false)
                return ViewHolder(
                    itemBinding
                )
            }
        }

        fun bind(favCoin: FavCoin?) {
            binding.favCoin = favCoin
            binding.executePendingBindings()
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavCoin>() {
            override fun areItemsTheSame(
                oldItem: FavCoin,
                newItem: FavCoin
            ): Boolean = oldItem.coinId == newItem.coinId

            override fun areContentsTheSame(
                oldItem: FavCoin,
                newItem: FavCoin
            ): Boolean = oldItem == newItem
        }
    }

}