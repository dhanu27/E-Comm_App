package com.myyour.e_comm_app.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseDTO(
    @JsonProperty("status") val status: String,
    @JsonProperty("error") val error: String? = null,
    @JsonProperty("data") val data: DataDTO,
)


@JsonIgnoreProperties(ignoreUnknown = true)
data class Item(
    @JsonProperty("name") val name: String,
    @JsonProperty("price") val price: String,
    @JsonProperty("extra") val extra: String?,
    @JsonProperty("image") val image: String?,
)
data class DataDTO(
    @JsonProperty("items") val items: List<Item>?,
)
