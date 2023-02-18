package com.myyour.e_comm_app.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.myyour.e_comm_app.model.Item
import com.myyour.e_comm_app.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProductList()
        }
    }
    val products : LiveData<List<Item>>
    get() = productRepository.products
}