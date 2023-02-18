package com.myyour.e_comm_app.room

import androidx.room.*

@Dao
interface ProductDAO {
    @Query("SELECT * FROM product")
    fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM product WHERE name LIKE :search")
    fun findProductsByName(search: String?): List<ProductEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(product: List<ProductEntity>)

    @Delete
    fun delete(item: ProductEntity)

    @Query("DELETE FROM product")
    fun deleteAll()
}