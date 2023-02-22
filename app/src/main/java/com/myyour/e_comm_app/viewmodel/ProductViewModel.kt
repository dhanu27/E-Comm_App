package com.myyour.e_comm_app.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myyour.e_comm_app.Utils.NetworkResult
import com.myyour.e_comm_app.model.DataDTO

import com.myyour.e_comm_app.model.Item
import com.myyour.e_comm_app.model.ResponseDTO
import com.myyour.e_comm_app.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {
    private var _products:MutableLiveData<NetworkResult<List<Item>>> =
        MutableLiveData<NetworkResult<List<Item>>>()
    val products = _products

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

    fun getProductListOnSearch(searchString:String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = productRepository.getProductListByName(searchString)
            _products.postValue(result)
        }
    }
}