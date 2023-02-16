package com.myyour.e_comm_app.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myyour.e_comm_app.api.ProductService
import com.myyour.e_comm_app.api.RetroFitHelper
import com.myyour.e_comm_app.model.Item
import com.myyour.e_comm_app.model.ResponseDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton



class ProductRepository @Inject constructor(@ApplicationContext context: Context){
    private val productService: ProductService =
        RetroFitHelper.getRetroFitInstance().create(ProductService::class.java)
    private val productsLiveData = MutableLiveData<ArrayList<Item>>()
    val products: LiveData<ArrayList<Item>>
    get() = productsLiveData

    suspend fun getProductList() {
        val response = productService.getProductList();
        if (response?.body() != null) {
            val responseBody = response.body() as ResponseDTO
            productsLiveData.postValue(responseBody.data.items!!);
        }
    }

}