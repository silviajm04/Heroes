package com.example.heroes

import com.google.gson.annotations.SerializedName

data class BiographyHeroResponse(
    @SerializedName("response") var response :String,
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("full-name") var fullName: String,
    @SerializedName("alter-egos") var alterEgos: String,
    @SerializedName("place-of-birth") var placeOfBirth: String,
    @SerializedName("first-appearance") var firstAppearance: String,
    @SerializedName("publisher") var publisher: String,
    @SerializedName("alignment") var alignment: String)