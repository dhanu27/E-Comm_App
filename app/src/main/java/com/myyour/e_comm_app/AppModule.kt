package com.myyour.e_comm_app

import com.myyour.e_comm_app.api.ProductService
import com.myyour.e_comm_app.api.RetroFitHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofitInstance():ProductService =  RetroFitHelper.getRetroFitInstance().create(ProductService::class.java)
}