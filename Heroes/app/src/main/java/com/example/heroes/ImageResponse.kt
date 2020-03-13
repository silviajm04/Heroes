package com.example.heroes

import com.google.gson.annotations.SerializedName

data class ImageResponse (
    @SerializedName("response") var response :String,
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("url") var url: String)