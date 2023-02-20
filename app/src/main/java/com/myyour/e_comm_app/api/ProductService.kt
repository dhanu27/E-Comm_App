package com.myyour.e_comm_app.api

import com.myyour.e_comm_app.model.ResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("v3/{versionId}")
    suspend fun getProductList(@Path("versionId") versionId: String) : Response<ResponseDTO>
}