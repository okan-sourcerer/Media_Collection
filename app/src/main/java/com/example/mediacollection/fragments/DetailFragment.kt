package com.example.mediacollection.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mediacollection.R
import com.example.mediacollection.UtilHandler
import com.example.mediacollection.model.Content

class DetailFragment(private val type: String, private val position: Int): Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var nameText: TextView
    private lateinit var typeText: TextView
    private lateinit var producerText: TextView
    private lateinit var linkText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        // get information that is passed to ContentDetailActivity from fragment and instantiate views

        instantiateUI(view)

        return view
    }

    private fun instantiateUI(view: View){
        imageView = view.findViewById(R.id.detailImage)
        nameText = view.findViewById(R.id.detailNameText)
        typeText = view.findViewById(R.id.detailTextType)
        producerText = view.findViewById(R.id.detailProducerText)
        linkText = view.findViewById(R.id.detailLinks)

        // get content
        val content: Content = UtilHandler.getContent(type)[position]
        content.image?.let { imageView.setImageResource(it)}
        nameText.text = content.name
        typeText.text = content.type
        producerText.text = content.producer
        linkText.text =  ""
        for (aLink in content.links){

            linkText.append(aLink)
            linkText.append("\n")

        }
        //linkText.movementMethod = LinkMovementMethod.getInstance()

    }
}