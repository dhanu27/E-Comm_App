package com.myyour.eCommApp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myyour.eCommApp.Utils.NetworkResult
import com.myyour.eCommApp.getOrAwaitValue
import com.myyour.eCommApp.model.Item
import com.myyour.eCommApp.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

internal class ProductViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test_get_emptyList_products`() = runTest {
        Mockito.`when`(productRepository.getProductList())
            .thenReturn(NetworkResult.Loaded(emptyList()))
        val sut = ProductViewModel(productRepository)
        val job = sut.getProductList()
        job.join()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = sut.products.getOrAwaitValue()
        Assert.assertEquals(0, result.data!!.size)
    }

    @Test
    fun `test_get_notAEmptyList_products`() = runTest {
        val itemsList = listOf(
            Item("Item 15", "125.0", "Ship to shopping", null),
            Item("Item 15", "125.0", "Ship to shopping", null)
        )

        Mockito.`when`(productRepository.getProductList())
            .thenReturn(NetworkResult.Loaded(itemsList))
        val sut = ProductViewModel(productRepository)
        val job = sut.getProductList()
        job.join()
        testDispatcher.scheduler.advanceUntilIdle()

        val result = sut.products.getOrAwaitValue()
        Assert.assertEquals(2, result.data!!.size)
    }


    @Test
    fun `test_get_error_products`() = runTest {
        val msgString = "Something Went Wrong"
        Mockito.`when`(productRepository.getProductList())
            .thenReturn(NetworkResult.Error(msgString))
        val sut = ProductViewModel(productRepository)
        val job = sut.getProductList()
        job.join()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = sut.products.getOrAwaitValue()
        Assert.assertEquals(true, result is NetworkResult.Error)
    }

    @Test
    fun `test_searchByProductName_expectedOneProduct`() = runTest {
        val searchString = "Item 1"
        val itemsList = listOf(
            Item("Item 1", "125.0", "Ship to shopping", null),
        )
        Mockito.`when`(productRepository.getProductListByName(searchString))
            .thenReturn(NetworkResult.Loaded(itemsList))

        val sut = ProductViewModel(productRepository)
        val job = sut.getProductListOnSearch(searchString)
        job.join()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = sut.products.getOrAwaitValue()
        Assert.assertEquals(1, result.data!!.size)
    }

}