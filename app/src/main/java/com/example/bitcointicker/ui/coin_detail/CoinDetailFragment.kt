package com.example.bitcointicker.ui.coin_detail

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bitcointicker.BaseFragment
import com.example.bitcointicker.R
import com.example.bitcointicker.data.entities.CoinDetail
import com.example.bitcointicker.data.entities.FavCoin
import com.example.bitcointicker.databinding.FragmentCoinDetailBinding
import com.example.bitcointicker.util.Constants
import com.example.bitcointicker.util.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment : BaseFragment<FragmentCoinDetailBinding>() {
    private val viewModel: CoinDetailViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = super.onCreateView(inflater, container, savedInstanceState)?.rootView

        arguments?.getString(Constants.COIN_ID)?.let {
            viewModel.getCoinDetail(it)
        }

        init()
        listeners()
        observers()

        return mView
    }

    private fun init(){
        auth = Firebase.auth
    }

    private fun observers(){
        viewModel.liveDataCoinDetail.observe(viewLifecycleOwner, {
            if (it.status == Status.SUCCESS){
                viewModel.coinDetail = it.data
                dataBinding.coinDetail = it.data
            }
        })
    }

    private fun listeners(){
        dataBinding.buttonAddFavList.setOnClickListener {
            isLogin()
        }
    }

    private fun isLogin(){
        val currentUser = auth.currentUser
        if(currentUser != null ){
            if (dataBinding.editTextRefreshInterval.text.toString().isNotEmpty()){
                val refreshInterval =  Integer.valueOf(dataBinding.editTextRefreshInterval.text.toString())*1000L
                viewModel.setFavCoin(FavCoin(currentUser.uid,null,viewModel.coinDetail?.id,viewModel.coinDetail?.name,viewModel.coinDetail?.market_data?.current_price?.usd,refreshInterval))
                dataBinding.buttonAddFavList.isEnabled = false
                dataBinding.editTextRefreshInterval.text.clear()
            }
            else{
                Toast.makeText(requireContext(),getText(R.string.warning_empty_refresh_interval),Toast.LENGTH_SHORT).show()
            }
        }
        else{
            navigateAuth()
        }
    }

    private fun navigateAuth(){
        findNavController().navigate(R.id.action_fragment_coin_detail_to_dialog_fragment_login)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_coin_detail
}