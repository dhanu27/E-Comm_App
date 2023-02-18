package com.myyour.e_comm_app
import android.content.Context
import com.myyour.e_comm_app.api.ProductService
import com.myyour.e_comm_app.api.RetroFitHelper
import com.myyour.e_comm_app.room.AppDatabase
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

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext applicationContext:Context):Context = applicationContext

}