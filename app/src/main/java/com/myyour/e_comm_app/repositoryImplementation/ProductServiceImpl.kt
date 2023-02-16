package com.myyour.e_comm_app.repositoryImplementation

import com.myyour.e_comm_app.api.ProductService
import com.myyour.e_comm_app.model.ResponseDTO
import retrofit2.Response

class ProductServiceImpl(): ProductService {
    override suspend fun getProductList(): Response<ResponseDTO> {
        TODO("Not yet implemented")
    }

}
