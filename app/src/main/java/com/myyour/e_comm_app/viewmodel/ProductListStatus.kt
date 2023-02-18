package com.myyour.e_comm_app.viewmodel

import com.myyour.e_comm_app.model.Item

sealed class ProductListStatus {
    data class isLoading(var loading: Boolean) : ProductListStatus()
    data class error(val error: String) : ProductListStatus()
    data class loaded(val items: List<Item>) : ProductListStatus()
    data class noItemFound(val msg: String):ProductListStatus()
}
