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

/**
 * Product view model :- This class is layer b/w Model and View which holds the data from data layer
 * and pass it to the view .
 * Parameters it require are coming from DI(Hilt)
 * @property productRepository
 * @constructor Create empty Product view model
 */
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

    /**
     * Get product list it is use to call the getProductList func. of repo. which return data
     * And it return Job instance that needed only while
     * we testing to check something after action is complete
     */
    fun getProductList(): Job {
        val job = viewModelScope.launch(dispatcher) {
            mProducts.postValue(NetworkResult.Loading())
            val result = productRepository.getProductList()
            mProducts.postValue(result)
        }
       return job
    }

    /**
     * Get product list on search fun it call the getProductListByName func. and return product list
     *
     * @param searchString
     * @return It's a Job instance that needed only while
     * we testing to check something after action is complete
     */
    fun getProductListOnSearch(searchString: String):Job {
        return viewModelScope.launch(dispatcher) {
            val result = productRepository.getProductListByName(searchString)
            mProducts.postValue(result)
        }
    }

    /**
     * Swipe right - Update the right swipe gesture So mainActivity can also observe that
     * help navcontoller to do View changes
     * @param routeIndex -> Index of view
     */
    fun swipeRight(routeIndex: Int) {
        mSwipeGesture.postValue(Pair(routeIndex, SwipeGestures.Right))
    }

    /**
     * Swipe left - Update the left swipe gesture So mainActivity can also observe that
     * help navcontoller to do View changes
     * @param routeIndex -> Index of view
     */
    fun swipeLeft(routeIndex: Int) {
        mSwipeGesture.postValue(Pair(routeIndex, SwipeGestures.Left))
    }

    /**
     * Refresh product list - Call the api for product list on user swipe to refresh from top
     *
     */
    fun refreshProductList() {
        mIsRefreshing.postValue(true)
        viewModelScope.launch() {
            val result = productRepository.getProductList()
            mProducts.postValue(result)
            mIsRefreshing.postValue(false)
        }
    }
}