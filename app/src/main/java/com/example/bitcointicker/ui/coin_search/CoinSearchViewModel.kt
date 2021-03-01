package com.example.bitcointicker.ui.coin_search

import androidx.lifecycle.*
import com.example.bitcointicker.data.entities.Coin
import com.example.bitcointicker.data.entities.CoinDetail
import com.example.bitcointicker.data.local.CoinDao
import com.example.bitcointicker.data.repository.CoinRepository
import com.example.bitcointicker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinSearchViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    private val coinDao: CoinDao
    ) : ViewModel() {
    private val _seachKey = MutableLiveData<String>()

    val liveDataCoinList = coinRepository.getCoinList()

    private val _liveDataSearchCoin = _seachKey.switchMap {
        coinDao.searchCoin(it)
    }
    val liveDataSearchCoin : LiveData<List<Coin>>  = _liveDataSearchCoin

    fun searchCoin(searchKey:String){
        _seachKey.value = searchKey
    }

}