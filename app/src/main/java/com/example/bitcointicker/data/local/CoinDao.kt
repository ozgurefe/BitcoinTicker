package com.example.bitcointicker.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bitcointicker.data.entities.Coin

@Dao
interface CoinDao {

    @Query("SELECT * FROM coin")
    fun getAllCoins() : LiveData<List<Coin>>

    @Query("SELECT * FROM coin WHERE id = :id")
    fun getCoin(id: String): LiveData<Coin>

    @Query("SELECT * FROM coin WHERE name LIKE '%' || :searchKey || '%' or symbol LIKE '%' || :searchKey || '%'")
    fun searchCoin(searchKey : String): LiveData<List<Coin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<Coin>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: Coin?)

}