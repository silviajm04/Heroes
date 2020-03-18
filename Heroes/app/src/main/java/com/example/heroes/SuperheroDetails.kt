package com.example.heroes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_superhero_details.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jetbrains.anko.yesButton

class SuperheroDetails : AppCompatActivity() {

    private var urlHero = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_superhero_details)

        val idHero = intent.getStringExtra("idHero")
        urlHero = intent.getStringExtra("urlHero")

        getInfoBiography(idHero)
    }

    private fun getInfoBiography(idHero: String) {
        doAsync {
            val call = getRetrofit().create(APIService::class.java).getBiographyHero("$idHero/biography").execute()
            val biographyDetail = call.body() as BiographyHeroResponse
            val error = call.errorBody()
            uiThread {
                if(biographyDetail.response == "success") {
                    initBiography(biographyDetail)
                }else{
                    showErrorDialog()
                    Log.d("TAG", "message: " + error.toString())
                }
            }
        }
    }

    private fun showErrorDialog() {
        alert("Ha ocurrido un error, inténtelo de nuevo.") {
            yesButton { }
        }.show()
    }

    private fun initBiography(biographyDetail: BiographyHeroResponse) {
        textViewAlignment.text = biographyDetail.alignment
        textViewAlterEgos.text = biographyDetail.alterEgos
        textViewFirstAppearance.text = biographyDetail.firstAppearance
        textViewFullName.text = biographyDetail.name
        textViewPlaceOfBirth.text = biographyDetail.placeOfBirth
        textViewPublisher.text = biographyDetail.publisher
        imageViewHero.loadUrl(urlHero)
    }
}
