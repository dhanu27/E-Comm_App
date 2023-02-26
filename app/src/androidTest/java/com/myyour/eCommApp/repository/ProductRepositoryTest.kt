package com.myyour.eCommApp.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.myyour.eCommApp.Utils.Constants
import com.myyour.eCommApp.Utils.InternetUtils
import com.myyour.eCommApp.Utils.NetworkResult
import com.myyour.eCommApp.api.ProductService
import com.myyour.eCommApp.model.DataDTO
import com.myyour.eCommApp.model.ResponseDTO
import com.myyour.eCommApp.room.AppDatabase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


class ProductRepositoryTest {

    @Mock
    private lateinit var productService: ProductService

    @Mock
    private lateinit var appDatabase: AppDatabase
    @Mock
    private lateinit var internetUtils: InternetUtils

    private lateinit var context: Context;

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
      MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test_getProductList_ExpectedEmptyList`() = runTest {
        val data = DataDTO(items = emptyList())
        val response = ResponseDTO(status = "Success", data = data)
        Mockito.`when`(productService.getProductList(Constants.versionId))
            .thenReturn(Response.success(response))
        val sut = ProductRepository(context, productService, appDatabase)
        val result = sut.getProductList()
        assertEquals(true, result is NetworkResult.Loaded)
        assertEquals(0, result.data!!.size)
    }

    @Test
    fun `test_getProductListOffline_ExpectedEmptyList`() = runTest {
        val data = DataDTO(items = emptyList())
        val response = ResponseDTO(status = "Success", data = data)
        Mockito.`when`(internetUtils.isOnline(context)).thenReturn(false)
        Mockito.`when`(productService.getProductList(Constants.versionId))
            .thenReturn(Response.success(response))
        val sut = ProductRepository(context, productService, appDatabase)
        val result = sut.getProductList()
        assertEquals(true, result is NetworkResult.Loaded)
        assertEquals(0, result.data!!.size)
    }
}