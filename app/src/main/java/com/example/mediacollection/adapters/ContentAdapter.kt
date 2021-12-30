package com.example.mediacollection.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.util.Util
import com.example.mediacollection.R
import com.example.mediacollection.activities.ContentDetailActivity
import com.example.mediacollection.model.*
import com.example.mediacollection.utils.UtilHandler

class ContentAdapter(
    var contents: List<Content>, private val type:String
    ): RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = contents.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var image: ImageView = itemView.findViewById(R.id.imageContentType)
        private var name: TextView = itemView.findViewById(R.id.textItemName)
        private var producer: TextView = itemView.findViewById(R.id.textProducer)

        // Change contents of the cardview
        fun bind(position: Int){

            val content = contents[position]
            // This will prevent previous assets to be used again

            if (content.image != null){
                val bitmap = UtilHandler.getInstance(itemView.context).loadImage(content.image!!.path!!)
                image.setImageBitmap(bitmap)
            }

            name.text = content.name
            producer.text = content.producer

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ContentDetailActivity::class.java)
                // instead of passing individual values, we will use our Util class

                intent.putExtra(TYPE, type)
                intent.putExtra(POSITION, position)
                itemView.context.startActivity(intent)
            }
        }
    }

}