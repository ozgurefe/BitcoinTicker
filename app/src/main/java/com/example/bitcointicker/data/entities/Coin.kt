package com.example.bitcointicker.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "coin")
data class Coin(
        @PrimaryKey
        @field:SerializedName("id")
        @ColumnInfo(name = "id") var coinId: String,
        @ColumnInfo(name = "symbol") var symbol: String?,
        @ColumnInfo(name = "name") var name: String?
):Serializable