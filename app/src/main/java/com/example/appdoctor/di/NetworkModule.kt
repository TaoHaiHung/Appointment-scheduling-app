package com.example.appdoctor.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.appdoctor.db.DataRemoteAPI
import com.example.appdoctor.utils.Constant
import com.example.appdoctor.db.APIServices

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(createGson()))
            .baseUrl(Constant.BASE_URL)
            .build()
    }

    private fun createGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideAppServiceApi(retrofit: Retrofit): APIServices {
        return retrofit.create(APIServices::class.java)
    }

    @Singleton
    @Provides
    fun provideAppRemoteDataSource(appServiceApi: APIServices): DataRemoteAPI {
        return DataRemoteAPI(appServiceApi)
    }
}