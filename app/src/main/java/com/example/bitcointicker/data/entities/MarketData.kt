package com.example.bitcointicker.data.entities

import com.example.bitcointicker.data.entities.CurrentPrice

data class MarketData(
    val current_price: CurrentPrice,
    val price_change_percentage_24h:Double?
    )
