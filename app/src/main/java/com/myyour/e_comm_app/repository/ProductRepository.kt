package com.myyour.e_comm_app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myyour.e_comm_app.Utils.InternetUtils
import com.myyour.e_comm_app.Utils.NetworkResult
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
    private val productsLiveData = MutableLiveData<NetworkResult<List<Item>>>()
    val products: LiveData<NetworkResult<List<Item>>>
        get() = productsLiveData


    suspend fun getProductList() {
        productsLiveData.postValue(NetworkResult.Loading())
//      When user is online call APi else getData from DB
        if (InternetUtils.isOnline(applicationContext)) {
            val response = productService.getProductList();
            if (response?.body() != null) {
                val responseBody = response.body() as ResponseDTO
                if(!responseBody.error.isNullOrEmpty()){
                    productsLiveData.postValue(NetworkResult.Error(responseBody.error));
                }
                val productList: List<Item> = responseBody.data.items!!;

                productsLiveData.postValue(NetworkResult.Loaded(productList));
                setDataIntoLocalDB(productList)
            } else {
                productsLiveData.postValue(NetworkResult.Error(response.errorBody().toString()));
                Toast.makeText(applicationContext, "Something Went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            val itemsList: List<Item> = getProductsListFromLocalDB()
            productsLiveData.postValue(NetworkResult.Loaded(itemsList));
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

    private fun getProductsListFromLocalDB(): List<Item> {
        val productsList = appDatabase.productDao().getAll()
        return mapModelToEntity(productsList);
    }

    fun getProductsByName(searchString: String) {
        val productRows: List<ProductEntity> =
            appDatabase.productDao().findProductsByName(searchString)
        val productList = mapModelToEntity(productRows);
        productsLiveData.postValue(NetworkResult.Loaded(productList));
    }

    private fun mapModelToEntity(productList: List<ProductEntity>): List<Item> {
        val productList: List<Item> = productList.map {
            Item(
                name = it.name,
                price = it.price, extra = it.extra, image = it.image
            )
        }
        return productList;
    }


}