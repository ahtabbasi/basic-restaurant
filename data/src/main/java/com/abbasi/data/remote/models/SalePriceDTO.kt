package com.abbasi.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SalePriceDTO(
    @SerialName("amount")
    val amount: String,
    @SerialName("currency")
    val currency: String
)