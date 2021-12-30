package com.example.mediacollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mediacollection.R
import com.example.mediacollection.model.ALL
import com.example.mediacollection.utils.UtilHandler
import com.example.mediacollection.model.Content
import com.example.mediacollection.model.POSITION
import com.example.mediacollection.model.TYPE

class DetailFragment: Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var nameText: TextView
    private lateinit var typeText: TextView
    private lateinit var producerText: TextView
    private lateinit var linkText: TextView
    private lateinit var editButton: Button

    private lateinit var type: String
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get information that is passed to ContentDetailActivity from fragment and instantiate views

        type = requireArguments().getString(TYPE)!!
        position = requireArguments().getInt(POSITION)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)


        instantiateUI(view)

        return view
    }

    private fun instantiateUI(view: View){
        imageView = view.findViewById(R.id.detailImage)
        nameText = view.findViewById(R.id.detailNameText)
        typeText = view.findViewById(R.id.detailTextType)
        producerText = view.findViewById(R.id.detailProducerText)
        linkText = view.findViewById(R.id.detailLinks)
        editButton = view.findViewById(R.id.detailEditButton)

        // get content
        val content: Content = UtilHandler.getInstance(requireContext()).getContent(type)[position]
        content.image?.let { imageView.setImageURI(it)} // instantiate image from uri
        nameText.text = content.name
        typeText.text = content.type
        producerText.text = content.producer
        linkText.text =  content.links
        //linkText.movementMethod = LinkMovementMethod.getInstance()

        editButton.setOnClickListener{
            //TODO:: DO this
        }
    }
}