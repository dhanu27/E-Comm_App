package com.myyour.e_comm_app.Utils

import com.myyour.e_comm_app.model.Item

sealed class NetworkResult<T>(val  data:T?=null, val msg:String?=null) {
    class Loading<T> : NetworkResult<T>()
    class Error<T>(msg:String?=null,data: T?=null) : NetworkResult<T>(data,msg)
    class Loaded<T>(data: T) : NetworkResult<T>(data)
}