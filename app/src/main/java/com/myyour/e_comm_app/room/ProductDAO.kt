package com.myyour.e_comm_app.room

import androidx.room.*

@Dao
interface ProductDAO {
    @Query("SELECT * FROM product")
    fun getAll(): List<ProductEntity>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<Item>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): Item

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(product: List<ProductEntity>)

    @Delete
    fun delete(item: ProductEntity)

    @Query("DELETE FROM product")
    fun deleteAll()
}