package com.example.bitcointicker.ui.coin_fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitcointicker.BaseFragment
import com.example.bitcointicker.R
import com.example.bitcointicker.data.entities.FavCoin
import com.example.bitcointicker.databinding.FragmentFavouriteCoinBinding
import com.example.bitcointicker.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteCoinFragment : BaseFragment<FragmentFavouriteCoinBinding>() {

    val favCoinListAdapter = FavouriteCoinListAdapter()
    private val viewModel: FavouriteCoinViewModel by viewModels()
    var arrayListFavCoin = ArrayList<FavCoin>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView =  super.onCreateView(inflater, container, savedInstanceState)?.rootView

        prepareFavCoinRecyclerView()
        observers()
        viewModel.getFavouriteCoin()

        return mView
    }

    private fun observers(){
        viewModel.liveDataFavouriteCoin.observe(viewLifecycleOwner, {
            if (it.status == Status.SUCCESS){
                it.data?.let { it1 ->
                    clearFavoriteCoin()
                    setFavouriteCoin(it1)
                }
            }
        })
    }

    private fun setFavouriteCoin(arrayList: ArrayList<FavCoin>){
        arrayListFavCoin.addAll(arrayList)
        favCoinListAdapter.notifyDataSetChanged()
    }

    private fun clearFavoriteCoin(){
        arrayListFavCoin.clear()
        favCoinListAdapter.notifyDataSetChanged()
    }

    private fun prepareFavCoinRecyclerView(){
        dataBinding.recyclerViewFavouriteCoin.layoutManager = LinearLayoutManager(requireContext())
        dataBinding.recyclerViewFavouriteCoin.adapter = favCoinListAdapter
        favCoinListAdapter.submitList(arrayListFavCoin)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_favourite_coin
}