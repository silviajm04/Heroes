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

    lateinit var heroImageAdapter: HeroImageAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var imagesHeroArray : ArrayList<ImageResponse> = ArrayList<ImageResponse>()
    var init = 1
    var limit = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this)
        rvSuperheros.layoutManager = layoutManager
        downloadSuperHeroes()

        rvSuperheros.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = (recyclerView.layoutManager as LinearLayoutManager?)!!.childCount
                val pastVisibleItem: Int = (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstCompletelyVisibleItemPosition()
                val total: Int = recyclerView.adapter!!.itemCount

                if (dy > 0){
                    if (visibleItemCount + pastVisibleItem >= total) {

                        downloadSuperHeroes()
                    }
                }
            }
        })
    }

    private fun downloadSuperHeroes(){

        limit += 10
        for(numberId in init..limit) {
            getImageById((numberId).toString())
        }
        init = limit + 1
    }

    private fun getImageById(idHero: String) {
        doAsync {
            val call = getRetrofit().create(APIService::class.java).getImageById("$idHero/image").execute()
            val imageHero = call.body() as ImageResponse
            val error = call.errorBody()
            uiThread {
                if(imageHero.response == "success") {
                    imagesHeroArray.add(imageHero)
                    if(::heroImageAdapter.isInitialized){

                        updateView()
                    }else{
                        initCharacter(imageHero)
                    }
                }else{
                    // showErrorDialog()
                    Log.d("TAG", "message: " + error.toString())
                }
            }
        }
    }

    private fun showErrorDialog() {
        alert("Lo sentimos ha ocurrido un error") {
            yesButton { }
        }.show()
    }

    private fun initCharacter(imageHero: ImageResponse) {
        heroImageAdapter = HeroImageAdapter(imagesHeroArray)
        rvSuperheros.setHasFixedSize(true)
        rvSuperheros.layoutManager = LinearLayoutManager(this)
        rvSuperheros.adapter = heroImageAdapter
    }
    private fun updateView() {
        heroImageAdapter.setItems(imagesHeroArray)
        heroImageAdapter.notifyDataSetChanged()
    }
}


