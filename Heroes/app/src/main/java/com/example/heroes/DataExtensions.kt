package com.example.heroes

import android.widget.ImageView
import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun ImageView.loadUrl(url: String) {
    Picasso.get().load(url).into(this)
}
 fun getRetrofit(): Retrofit {
    var url = Constans().BASE_URL
    return Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

