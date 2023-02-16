package com.myyour.e_comm_app.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseDTO(
    @JsonProperty("status") val status: String,
    @JsonProperty("error") val error: String? = null,
    @JsonProperty("data") val data: DataDTO,
)

data class Item(
    @JsonProperty("name") val name: String,
    @JsonProperty("price") val price: String,
    @JsonProperty("extra") val extra: String? = null,
    @JsonProperty("image") val image: String? = null,
)

data class DataDTO(
    @JsonProperty("items") val items: ArrayList<Item>? = null,
)
