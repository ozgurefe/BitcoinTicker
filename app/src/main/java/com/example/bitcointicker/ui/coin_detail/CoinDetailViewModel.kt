package com.example.bitcointicker.ui.coin_detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitcointicker.data.entities.CoinDetail
import com.example.bitcointicker.data.entities.FavCoin
import com.example.bitcointicker.data.repository.CoinRepository
import com.example.bitcointicker.util.Resource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(private val coinRepository: CoinRepository) : ViewModel() {
    val firebaseFireStore = Firebase.firestore
    var coinDetail: CoinDetail? = null

    val TAG = "fav_coin"

    private val _liveDataCoinDetail = MutableLiveData<Resource<CoinDetail>>()
    val liveDataCoinDetail : LiveData<Resource<CoinDetail>> get() = _liveDataCoinDetail

    fun getCoinDetail(id:String){
        viewModelScope.launch {
            _liveDataCoinDetail.value = coinRepository.getCoinDetail(id)
        }
    }

    fun setFavCoin(favCoin:FavCoin){
        firebaseFireStore.collection("fav_coin")
            .add(favCoin)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}