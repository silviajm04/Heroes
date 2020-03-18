package com.example.heroes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image.view.*

class HeroImageAdapter (val imageSuperHero: ArrayList<ImageResponse>) : RecyclerView.Adapter<HeroImageAdapter.ViewHolder>() {
    var arrayimages = imageSuperHero
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroImageAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return ViewHolder(layoutInflater.inflate(R.layout.item_image, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayimages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = arrayimages[position]
        holder.bind(item)
    }

     fun setItems(arrayimagesUpdate : ArrayList<ImageResponse>){
        arrayimages = arrayimagesUpdate
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var context: Context = view.context
        fun bind( dataHero: ImageResponse) {
            itemView.imageViewHero.loadUrl(dataHero.url)
            itemView.nameHero.text = dataHero.name

            itemView.setOnClickListener {
                val intent = Intent(this.context, SuperheroDetails::class.java)
                intent.putExtra("idHero",(dataHero.id).toString())
                intent.putExtra("urlHero",dataHero.url)
                this.context.startActivity(intent)
            }
        }
    }

}