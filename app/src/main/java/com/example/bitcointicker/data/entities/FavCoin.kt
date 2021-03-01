package com.example.bitcointicker.data.entities

data class FavCoin(
        val userId:String,
        var documentId:String?,
        val coinId : String?,
        val name : String?,
        val currentPrice : Double?,
        val refreshInterval: Long?
)
