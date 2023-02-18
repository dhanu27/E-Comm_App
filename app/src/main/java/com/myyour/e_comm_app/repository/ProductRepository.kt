package com.myyour.e_comm_app.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myyour.e_comm_app.Utils.InternetUtils
import com.myyour.e_comm_app.api.ProductService
import com.myyour.e_comm_app.model.Item
import com.myyour.e_comm_app.model.ResponseDTO
import com.myyour.e_comm_app.room.AppDatabase
import com.myyour.e_comm_app.room.ProductEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ProductRepository @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val productService: ProductService,
    private val appDatabase: AppDatabase
) {
    private val productsLiveData = MutableLiveData<List<Item>>()
    val products: LiveData<List<Item>>
        get() = productsLiveData

    suspend fun getProductList() {
//        productsLiveData.postValue(isLoading);
//      When user is online call APi else getData from DB
        if (InternetUtils.isOnline(applicationContext)) {
            val response = productService.getProductList();
            if (response?.body() != null) {
                val responseBody = response.body() as ResponseDTO
                val productList: List<Item> = responseBody.data.items!!;

                productsLiveData.postValue(productList);
                setDataIntoLocalDB(productList)
            }else{
                //TODO:- Handle Error case
            }
        } else {
            val productsList = appDatabase.productDao().getAll()
            val itemsList: List<Item> = getProductsListFromLocalDB(productsList)
            productsLiveData.postValue(itemsList);
        }
    }

    private fun setDataIntoLocalDB(productList: List<Item>) {
//        First delete all items from list to remove redudancy
        appDatabase.productDao().deleteAll()
        // Insert Item into product entity
        val productRows: List<ProductEntity> = productList.map {
            ProductEntity(
                name = it.name,
                price = it.price, extra = it.extra, image = it.image
            )
        }
        appDatabase.productDao().insertAll(productRows)
    }

    private fun getProductsListFromLocalDB(productsList:List<ProductEntity>) : List<Item>{
        val itemsList: List<Item> = productsList.map {
            Item(
                name = it.name,
                price = it.price, extra = it.extra, image = it.image
            )
        }
        return itemsList;
    }

}