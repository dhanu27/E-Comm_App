package com.myyour.e_comm_app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myyour.e_comm_app.Utils.NetworkResult
import com.myyour.e_comm_app.getOrAwaitValue
import com.myyour.e_comm_app.model.Item
import com.myyour.e_comm_app.repository.ProductRepository
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
  fun test_emptyList_GetProductList()= runTest{
    Mockito.`when`(productRepository.getProductList()).thenReturn(NetworkResult.Loaded(emptyList()))
    val sut = ProductViewModel(productRepository)
    sut.getProductList()
    testDispatcher.scheduler.advanceUntilIdle()
    val result = sut.products.getOrAwaitValue()
    Assert.assertEquals(0,result.data!!.size)
  }

  @Test
  fun test_ListContainItems_GetProductList()= runTest{
    val itemsList = listOf(Item("Item 15","125.0", "Ship to shopping",null),
    Item("Item 15","125.0", "Ship to shopping",null))

    Mockito.`when`(productRepository.getProductList()).thenReturn(NetworkResult.Loaded(itemsList))
    val sut = ProductViewModel(productRepository)
    sut.getProductList()
    testDispatcher.scheduler.advanceUntilIdle()
    val result = sut.products.getOrAwaitValue()
    Assert.assertEquals(2,result.data!!.size)
  }
}