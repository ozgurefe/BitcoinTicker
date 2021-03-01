package com.example.bitcointicker.di


import android.content.Context
import com.example.bitcointicker.data.local.AppDatabase
import com.example.bitcointicker.data.local.CoinDao
import com.example.bitcointicker.util.Constants
import com.example.bitcointicker.data.remote.CoinService
import com.example.bitcointicker.data.remote.CoinRemoteDataSource
import com.example.bitcointicker.data.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CoinService = retrofit.create(CoinService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(coinService: CoinService) = CoinRemoteDataSource(coinService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCoinDao(db: AppDatabase) = db.coinDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: CoinRemoteDataSource, localDataSource: CoinDao) = CoinRepository(remoteDataSource, localDataSource)
}