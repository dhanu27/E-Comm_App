package com.myyour.eCommApp.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myyour.eCommApp.Utils.NetworkResult
import com.myyour.eCommApp.Utils.enums.SwipeGestures
import com.myyour.eCommApp.model.Item
import com.myyour.eCommApp.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {
    private var mProducts: MutableLiveData<NetworkResult<List<Item>>> =
        MutableLiveData<NetworkResult<List<Item>>>()
    val products = mProducts

    private var mSwipeGesture: MutableLiveData<Pair<Int, SwipeGestures>> =
        MutableLiveData<Pair<Int, SwipeGestures>>()
    val swipeGesture = mSwipeGesture

    private var mIsRefreshing: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>()
    val isRefreshing = mIsRefreshing

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    init {
        getProductList()
    }

     fun getProductList(): Job {
        val job = viewModelScope.launch(dispatcher) {
            mProducts.postValue(NetworkResult.Loading())
            val result = productRepository.getProductList()
            mProducts.postValue(result)
        }
       return job
    }

    fun getProductListOnSearch(searchString: String) {
        viewModelScope.launch(dispatcher) {
            val result = productRepository.getProductListByName(searchString)
            mProducts.postValue(result)
        }
    }
    fun swipeRight(routeIndex: Int) {
        mSwipeGesture.postValue(Pair(routeIndex, SwipeGestures.Right))
    }
    fun swipeLeft(routeIndex: Int) {
        mSwipeGesture.postValue(Pair(routeIndex, SwipeGestures.Left))
    }
    fun refreshProductList() {
        mIsRefreshing.postValue(true)
        viewModelScope.launch() {
            val result = productRepository.getProductList()
            mProducts.postValue(result)
            mIsRefreshing.postValue(false)
        }
    }
}