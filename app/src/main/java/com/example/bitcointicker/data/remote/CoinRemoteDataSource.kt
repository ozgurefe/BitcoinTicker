package com.example.bitcointicker.data.remote

import javax.inject.Inject

class CoinRemoteDataSource @Inject constructor(private val coinService:CoinService):BaseDataSource() {

    suspend fun getCoinList() = getResult { coinService.getCoinList(false) }

    suspend fun getCoinDetail(id:String) = getResult { coinService.getCoinDetail(id) }

}