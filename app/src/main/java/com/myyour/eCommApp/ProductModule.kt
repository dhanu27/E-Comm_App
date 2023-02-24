package com.myyour.eCommApp
import android.content.Context
import com.myyour.eCommApp.api.ProductService
import com.myyour.eCommApp.api.RetroFitHelper
import com.myyour.eCommApp.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofitInstance():ProductService =  RetroFitHelper.getRetroFitInstance().create(ProductService::class.java)

    @Provides
    @Singleton
    fun provideDataBaseInstance(@ApplicationContext applicationContext:Context):AppDatabase =   AppDatabase.getDatabaseInstance(applicationContext)

}