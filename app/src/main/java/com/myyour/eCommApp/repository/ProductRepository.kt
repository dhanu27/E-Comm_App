package com.myyour.eCommApp.repository

import android.content.Context
import android.widget.Toast
import com.myyour.eCommApp.Utils.Constants.versionId
import com.myyour.eCommApp.Utils.InternetUtils
import com.myyour.eCommApp.Utils.NetworkResult
import com.myyour.eCommApp.api.ProductService
import com.myyour.eCommApp.model.Item
import com.myyour.eCommApp.model.ResponseDTO
import com.myyour.eCommApp.room.AppDatabase
import com.myyour.eCommApp.room.ProductEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProductRepository @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val mProductService: ProductService,
    private val mAppDatabase: AppDatabase
) {
    suspend fun getProductList(): NetworkResult<List<Item>> {
//      When user is online call APi else getData from DB
        if (InternetUtils.isOnline(applicationContext)) {

            try {
                val response = mProductService.getProductList(versionId)
                if (response.body() != null) {
                    val responseBody = response.body() as ResponseDTO

                    if (!responseBody.error.isNullOrEmpty()) {
                        return NetworkResult.Error(responseBody.error)
                    }

                    val productList: List<Item> = responseBody.data.items!!
//                    // Reset the DB on another thread and current thread return data from api
                    CoroutineScope(Dispatchers.IO).launch {
                        setDataIntoLocalDB(productList)
                    }
                    return NetworkResult.Loaded(productList)
                } else {

                    Toast.makeText(applicationContext, "Something Went wrong", Toast.LENGTH_SHORT)
                        .show()
                    return NetworkResult.Error(response.errorBody().toString())
                }
            } catch (e: Exception) {
                return NetworkResult.Error("Something Went Wrong")
            }
        } else {
            val itemsList = getProductsListFromLocalDB()
            return NetworkResult.Loaded(itemsList)
        }
    }

    private suspend fun setDataIntoLocalDB(productList: List<Item>) {
//        First delete all items from list to remove redundancy
        mAppDatabase.productDao().deleteAll()
        // Insert Item into product entity
        val productRows: List<ProductEntity> = productList.map {
            ProductEntity(
                name = it.name,
                price = it.price, extra = it.extra, image = it.image
            )
        }
        mAppDatabase.productDao().insertAll(productRows)
    }

    private suspend fun getProductsListFromLocalDB(): List<Item> {
        val productsList = mAppDatabase.productDao().getAll()
        return mapEntityToModel(productsList)
    }

    private fun mapEntityToModel(productList: List<ProductEntity>): List<Item> {
        val newProductList: List<Item> = productList.map {
            Item(
                name = it.name,
                price = it.price, extra = it.extra, image = it.image
            )
        }
        return newProductList
    }

    suspend fun getProductListByName(name: String): NetworkResult<List<Item>> {
        return try {
            val productsList = mAppDatabase.productDao().findProductsByName("%$name%")
            NetworkResult.Loaded(mapEntityToModel(productsList))
        } catch (e: Exception) {
            NetworkResult.Loaded(getProductsListFromLocalDB())
        }
    }

}