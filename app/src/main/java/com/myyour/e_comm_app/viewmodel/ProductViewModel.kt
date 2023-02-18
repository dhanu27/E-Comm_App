package com.myyour.e_comm_app.viewmodel



import androidx.lifecycle.LiveData
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
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProductList()
        }
    }
    val products : LiveData<NetworkResult<List<Item>>>
    get() = productRepository.products
    fun getProductsByName(search:String){
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProductsByName(search)
        }
    }
}