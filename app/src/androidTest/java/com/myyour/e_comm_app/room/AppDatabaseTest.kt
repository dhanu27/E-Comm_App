package com.myyour.e_comm_app.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    // get reference to the AppDatabase and ProductDAO class
    private lateinit var db: AppDatabase
    private lateinit var dao: ProductDAO

    @Before
    fun setUp() {
        // get context -- since this is an instrumental test it requires
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
            .build()
        dao = db.productDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndGetProduct() = runBlocking {
        val product = ProductEntity(
            id = 10,
            name = "TestItem",
            price = "1202",
            extra = "Shipping To Shopping"
        )
        dao.insertProduct(product)
        delay(100L)
        val products = dao.getAll()
        assertThat(products.contains(product)).isTrue()
    }

    @Test
    fun insertAndDeleteProduct() = runBlocking {
        val product = ProductEntity(
            id = 11,
            name = "TestItem",
            price = "1202",
            extra = "Shipping To Shopping"
        )
        dao.insertProduct(product)
        delay(100L)
        dao.delete(product)
        val products = dao.getAll()
        assertThat(!products.contains(product)).isTrue()
    }
}