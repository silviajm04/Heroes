package com.example.heroes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity(){
     var imagesHeroArray : ArrayList<ImageResponse> = ArrayList<ImageResponse>()
    lateinit var heroImageAdapter: HeroImageAdapter
    var init = 1
    var limit = 0
   // var idImageHero = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        downloadSuperHeroes()


        rvSuperheros.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isLastItemDisplaying(recyclerView)) { //Calling the method getdata again
                    downloadSuperHeroes()
                }
            }
        })


    }

    private fun isLastItemDisplaying(recyclerView: RecyclerView): Boolean {
        if (recyclerView.adapter!!.itemCount != 0) {
            val lastVisibleItemPosition =
                (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.adapter!!.itemCount - 1) return true
        }
        return false
    }




    private fun downloadSuperHeroes(){

        limit = limit + 10
        Log.d("-------------------LIMIT", limit.toString())
        Log.d("-------------------INIT", init.toString())
        for(numberId in init..limit) {
            getImageById((numberId).toString())

        }
        init = limit
    }


    private fun getImageById(idHero: String) {
        doAsync {
            val call = getRetrofit().create(APIService::class.java).getImageById("$idHero/image").execute()
            var imageHero = call.body() as ImageResponse
            var error = call.errorBody()
            uiThread {
                if(imageHero.response == "success") {
                    imagesHeroArray.add(imageHero)
                    initCharacter(imageHero)
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

    private fun initCharacter(imageHero: ImageResponse) {

        heroImageAdapter = HeroImageAdapter(imagesHeroArray)
        rvSuperheros.setHasFixedSize(true)
        rvSuperheros.layoutManager = LinearLayoutManager(this)
        rvSuperheros.adapter = heroImageAdapter

    }
}
