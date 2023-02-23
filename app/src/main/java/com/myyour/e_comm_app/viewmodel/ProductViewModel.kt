package com.myyour.e_comm_app.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.myyour.e_comm_app.R
import com.myyour.e_comm_app.Utils.NetworkResult
import com.myyour.e_comm_app.Utils.enums.SwipeGestures
import com.myyour.e_comm_app.model.DataDTO

import com.myyour.e_comm_app.model.Item
import com.myyour.e_comm_app.model.ResponseDTO
import com.myyour.e_comm_app.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {
    private var _products: MutableLiveData<NetworkResult<List<Item>>> =
        MutableLiveData<NetworkResult<List<Item>>>()
    val products = _products

    private var mSwipeGesture: MutableLiveData<Pair<Int, SwipeGestures>> =
        MutableLiveData<Pair<Int, SwipeGestures>>()
    val swipeGesture = mSwipeGesture

    private var _isReFreshing: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>()
    val isReFreshing = _isReFreshing


    init {
        getProductList()
    }

     fun getProductList() {
        viewModelScope.launch(Dispatchers.IO) {
            _products.postValue(NetworkResult.Loading())
            val result = productRepository.getProductList()
            _products.postValue(result)
        }
    }

    fun getProductListOnSearch(searchString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = productRepository.getProductListByName(searchString)
            _products.postValue(result)
        }
    }
    fun swipeRight(routeIndex: Int) {
        mSwipeGesture.postValue(Pair(routeIndex, SwipeGestures.Right))
    }
    fun swipeLeft(routeIndex: Int) {
        mSwipeGesture.postValue(Pair(routeIndex, SwipeGestures.Left))
    }
    fun refreshProductList() {
        _isReFreshing.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = productRepository.getProductList()
            _products.postValue(result)
            _isReFreshing.postValue(false)
        }
    }
}