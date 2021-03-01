package com.example.bitcointicker.ui.coin_fav

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitcointicker.data.entities.CoinDetail
import com.example.bitcointicker.data.entities.FavCoin
import com.example.bitcointicker.data.repository.CoinRepository
import com.example.bitcointicker.util.Resource
import com.example.bitcointicker.util.Status
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteCoinViewModel @Inject constructor(
        private val coinRepository: CoinRepository
) : ViewModel() {

    var arrayListFavCoin = ArrayList<FavCoin>()
    val firebaseFireStore = Firebase.firestore
    lateinit var registration:ListenerRegistration

    private val _liveDataFavouriteCoin = MutableLiveData<Resource<ArrayList<FavCoin>>>()
    val liveDataFavouriteCoin : LiveData<Resource<ArrayList<FavCoin>>> get() = _liveDataFavouriteCoin

    private fun realTimeFavouriteCoin(){
        val query = firebaseFireStore.collection("fav_coin")
        registration = query.addSnapshotListener { snapshots, e ->
            snapshots?.forEach { document->
                val favCoin = documentToFavCoin(document)
                arrayListFavCoin.filter { it.coinId == favCoin.coinId }.forEach {
                    arrayListFavCoin.remove(it)
                }
                arrayListFavCoin.add(favCoin)
                _liveDataFavouriteCoin.value = Resource.success(arrayListFavCoin)
            }
        }
    }

    fun getCoinDetail(favCoin: FavCoin){
        viewModelScope.launch {
            favCoin.coinId?.let {
                coinRepository.getCoinDetail(it).let {
                    if (it.status == Status.SUCCESS){
                        setFavCoin(FavCoin(favCoin.userId,favCoin.documentId,it.data?.id,it.data?.name,it.data?.market_data?.current_price?.usd,favCoin.refreshInterval))
                    }
                }
            }
        }
    }

    fun setFavCoin(favCoin:FavCoin){
        favCoin.documentId?.let {
            firebaseFireStore.collection("fav_coin")
                    .document(it)
                    .set(favCoin)
        }
    }

    fun getFavouriteCoin(){
        firebaseFireStore.collection("fav_coin")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val favCoin = documentToFavCoin(document)
                    arrayListFavCoin.add(favCoin)
                    viewModelScope.launch {
                        refreshHandler(favCoin)
                    }
                }
                _liveDataFavouriteCoin.value = Resource.success(arrayListFavCoin)
                realTimeFavouriteCoin()
            }
            .addOnFailureListener { exception ->
                _liveDataFavouriteCoin.value = Resource.error(exception.toString(), null)
            }
    }

    private fun refreshHandler(favCoin: FavCoin){
        updateFavCoin(favCoin)
        favCoin.refreshInterval?.let{
            Handler(Looper.getMainLooper()).postDelayed({
                viewModelScope.launch {
                    refreshHandler(favCoin)
                }
            }, it)
        }
    }

    private fun documentToFavCoin(document:QueryDocumentSnapshot):FavCoin{
        val hashmap = document.data
        hashmap.put("id", document.id)
        val data = Gson().toJson(hashmap)
        val favCoin = Gson().fromJson(data, FavCoin::class.java)
        favCoin.documentId = document.id
        return favCoin
    }

    private fun updateFavCoin(favCoin: FavCoin){
         getCoinDetail(favCoin)
    }

    override fun onCleared() {
        super.onCleared()
        registration.remove()
    }

}