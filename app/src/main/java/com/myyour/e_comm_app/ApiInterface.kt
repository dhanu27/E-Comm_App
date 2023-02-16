package com.myyour.e_comm_app
import com.myyour.e_comm_app.model.ResponseDTO
import retrofit2.Call
import retrofit2.http.GET;
import retrofit2.http.Path;

interface ApiInterface {
    @GET("v3/b6a30bb0-140f-4966-8608-1dc35fa1fadc")
    fun getProductList(): Call<ResponseDTO>
}