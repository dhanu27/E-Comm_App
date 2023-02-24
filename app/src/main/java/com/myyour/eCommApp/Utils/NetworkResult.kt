package com.myyour.eCommApp.Utils

sealed class NetworkResult<T>(val  data:T?=null, val msg:String?=null) {
    class Loading<T> : NetworkResult<T>()
    class Error<T>(msg:String?=null,data: T?=null) : NetworkResult<T>(data,msg)
    class Loaded<T>(data: T) : NetworkResult<T>(data)
}
