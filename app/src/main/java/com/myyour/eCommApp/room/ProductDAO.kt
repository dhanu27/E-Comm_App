package com.myyour.eCommApp.room

import androidx.room.*

@Dao
interface ProductDAO {
    @Query("SELECT * FROM product")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM product WHERE name LIKE :search")
    suspend fun findProductsByName(search: String?): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(product: List<ProductEntity>)

    @Delete
    suspend fun delete(item: ProductEntity)

    @Query("DELETE FROM product")
    suspend fun deleteAll()
}