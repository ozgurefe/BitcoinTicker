package com.example.bitcointicker.data.repository

import com.example.bitcointicker.data.entities.CoinDetail
import com.example.bitcointicker.data.local.CoinDao
import com.example.bitcointicker.data.remote.CoinRemoteDataSource
import com.example.bitcointicker.util.Resource
import com.example.bitcointicker.util.performGetOperation
import retrofit2.Response
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val remoteDataSource: CoinRemoteDataSource,
    private val localDataSource: CoinDao
) {

    fun getCoinList() = performGetOperation(
        databaseQuery = { localDataSource.getAllCoins() },
        networkCall = { remoteDataSource.getCoinList() },
        saveCallResult = { localDataSource.insertAll(it) }
    )

    suspend fun getCoinDetail(id:String): Resource<CoinDetail> = remoteDataSource.getCoinDetail(id)

}