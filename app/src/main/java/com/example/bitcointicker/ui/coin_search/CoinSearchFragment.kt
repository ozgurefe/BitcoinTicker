package com.example.bitcointicker.ui.coin_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitcointicker.BaseFragment
import com.example.bitcointicker.R
import com.example.bitcointicker.data.entities.Coin
import com.example.bitcointicker.databinding.FragmentCoinSearchBinding
import com.example.bitcointicker.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinSearchFragment : BaseFragment<FragmentCoinSearchBinding>(),CoinListAdapter.CoinListener {

    private val coinListAdapter = CoinListAdapter()
    private val viewModel:CoinSearchViewModel by viewModels()
    private val arrayListCoin = ArrayList<Coin>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = super.onCreateView(inflater, container, savedInstanceState)?.rootView

        prepareCoinRecyclerView()
        listeners()
        observers()

        return mView
    }

    private fun observers(){
        viewModel.liveDataCoinList.observe(viewLifecycleOwner, {
            it.data?.let { it1 ->
                setCoinList(it1)
            }
        })

        viewModel.liveDataSearchCoin.observe(viewLifecycleOwner, {
            clearCoinList()
            setCoinList(it)
        })
    }

    private fun clearCoinList(){
        arrayListCoin.clear()
        coinListAdapter.notifyDataSetChanged()
    }

    private fun setCoinList(listCoin:List<Coin>){
        arrayListCoin.addAll(listCoin)
        coinListAdapter.notifyDataSetChanged()
    }

    private fun listeners(){
        dataBinding.editTextSearch.addTextChangedListener {
            viewModel.searchCoin(it.toString())
        }

        dataBinding.buttonFavouriteCoin.setOnClickListener {
            navigateFavouriteCoin()
        }
    }

    private fun navigateFavouriteCoin(){
        findNavController().navigate(R.id.action_fragment_coin_search_to_fragment_fav_coin)
    }

    private fun prepareCoinRecyclerView(){
        coinListAdapter.setCoinListener(this)
        dataBinding.recyclerViewCoin.layoutManager = LinearLayoutManager(requireContext())
        dataBinding.recyclerViewCoin.adapter = coinListAdapter
        coinListAdapter.submitList(arrayListCoin)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_coin_search

    override fun clickCoin(coin: Coin?) {
        val bundle = bundleOf(Constants.COIN_ID to coin?.coinId)
        findNavController().navigate(R.id.action_fragment_coin_to_fragment_coin_detail,bundle)
    }
}