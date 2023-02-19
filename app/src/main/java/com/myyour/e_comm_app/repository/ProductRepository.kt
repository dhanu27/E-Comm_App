package com.myyour.e_comm_app.repository

import android.content.Context
import android.widget.Toast
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
    suspend fun getProductList(): NetworkResult<List<Item>> {
//      When user is online call APi else getData from DB
        if (InternetUtils.isOnline(applicationContext)) {
            val response = productService.getProductList()

            if (response.body() != null) {
                val responseBody = response.body() as ResponseDTO

                if (!responseBody.error.isNullOrEmpty()) {
                    return NetworkResult.Error(responseBody.error)
                }

                val productList: List<Item> = responseBody.data.items!!
                setDataIntoLocalDB(productList)
                return NetworkResult.Loaded(productList)
            } else {

                Toast.makeText(applicationContext, "Something Went wrong", Toast.LENGTH_SHORT)
                    .show()
                return NetworkResult.Error(response.errorBody().toString())
            }
        } else {
            val itemsList = getProductsListFromLocalDB()
            return NetworkResult.Loaded(itemsList)
        }
    }

    private suspend fun setDataIntoLocalDB(productList: List<Item>) {
//        First delete all items from list to remove redundancy
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

    private suspend fun getProductsListFromLocalDB(): List<Item> {
        val productsList = appDatabase.productDao().getAll()
        return mapModelToEntity(productsList)
    }

    private fun mapModelToEntity(productList: List<ProductEntity>): List<Item> {
        val newProductList: List<Item> = productList.map {
            Item(
                name = it.name,
                price = it.price, extra = it.extra, image = it.image
            )
        }
        return newProductList
    }

    //
//    suspend fun getProductsByName(searchString: String) {
//        val productRows: List<ProductEntity> =
//            appDatabase.productDao().findProductsByName(searchString)
//        val productList = mapModelToEntity(productRows)
//    }


}