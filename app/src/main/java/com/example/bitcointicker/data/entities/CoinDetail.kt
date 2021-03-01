package com.example.bitcointicker.data.entities

data class CoinDetail(
    val description: Description?,
    val hashing_algorithm: String?,
    val id: String?,
    val image: Image?,
    val name: String?,
    val symbol: String?,
    val market_data: MarketData
)