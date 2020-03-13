package com.example.heroes

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    fun getImageById(@Url url:String): Call<ImageResponse>

    @GET
    fun getBiographyHero(@Url url:String): Call<BiographyHeroResponse>
}