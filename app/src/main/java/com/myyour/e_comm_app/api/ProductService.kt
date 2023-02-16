package com.myyour.e_comm_app.api

import com.myyour.e_comm_app.model.ResponseDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("v3/b6a30bb0-140f-4966-8608-1dc35fa1fadc")
    suspend fun getProductList() : Response<ResponseDTO>
}