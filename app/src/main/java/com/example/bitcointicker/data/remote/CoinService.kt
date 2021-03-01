package com.example.bitcointicker.data.remote


import com.example.bitcointicker.data.entities.Coin
import com.example.bitcointicker.data.entities.CoinDetail
import retrofit2.Response
import retrofit2.http.*

interface CoinService {
    @GET("coins/list")
    suspend fun getCoinList(@Query("include_platform") include_platform:Boolean):Response<ArrayList<Coin>>

    @GET("coins/{id}")
    suspend fun getCoinDetail(@Path("id") id:String):Response<CoinDetail>
}