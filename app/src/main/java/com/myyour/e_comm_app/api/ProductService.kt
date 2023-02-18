package com.myyour.e_comm_app.api

import com.myyour.e_comm_app.model.ResponseDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("v3/995ce2a0-1daf-4993-915f-8c198f3f752c")
    suspend fun getProductList() : Response<ResponseDTO>
}