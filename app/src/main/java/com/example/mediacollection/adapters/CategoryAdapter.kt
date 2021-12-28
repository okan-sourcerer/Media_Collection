package com.example.mediacollection.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mediacollection.R
import com.example.mediacollection.activities.ContentActivity
import com.example.mediacollection.model.CATEGORY
import com.example.mediacollection.model.CATEGORY_INTENT
import com.example.mediacollection.model.Category

class CategoryAdapter(private val categories: List<Category>)
    : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = categories.size


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var cardView: CardView = itemView.findViewById(R.id.categoryCard)
        private var imageView: ImageView = itemView.findViewById(R.id.imageCategory)
        private var textView: TextView = itemView.findViewById(R.id.categoryText)

        fun bind(position: Int){
            val category = categories[position]
            imageView.setImageResource(category.typeImage)
            textView.text = category.description

            cardView.setOnClickListener {
                val intent = Intent(itemView.context, ContentActivity::class.java)

                intent.putExtra(CATEGORY, position)

                itemView.context.startActivity(intent)
            }
        }
    }

}