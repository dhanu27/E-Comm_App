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
import java.util.*
import javax.inject.Inject


/**
 * Product repository
 * Its wrapper class of data or model layer all calls to data took from here
 * @property applicationContext -> Require application context for internet is there or not
 * @property mProductService -> Instance of Product Service need to call getProductList
 * @property mAppDatabase -> Database Instance
 */
class ProductRepository @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val mProductService: ProductService,
    private val mAppDatabase: AppDatabase
) {
    /**
     * Get product list
     * Function check internet connection and basis of that call api of
     * get data from DB . Adn update the DB
     * Return NetworkResult class instance with state one of the state from
     * Loaded or Error
     */
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
    /**
       First delete all items from list to remove redundancy in data
       an then insert new data of class Item into product entity table
       by converting Item object to ProductEntity
     */
    private suspend fun setDataIntoLocalDB(productList: List<Item>) {

        mAppDatabase.productDao().deleteAll()
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

    /**
     * Get product list by searching the name  in the DB
     */
    suspend fun getProductListByName(name: String): NetworkResult<List<Item>> {
        return try {
            val productsList = mAppDatabase.productDao().findProductsByName("%$name%")
            NetworkResult.Loaded(mapEntityToModel(productsList))
        } catch (e: Exception) {
            NetworkResult.Loaded(getProductsListFromLocalDB())
        }
    }

}