package com.example.project2

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [bottom.newInstance] factory method to
 * create an instance of this fragment.
 */
class bottom : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState )
        //give the clock its roation powers
        var rotateThis= AnimationUtils.loadAnimation(context,R.anim.rotation)
        val clockWheel = view.findViewById<ImageView>(R.id.imageView)
        //start the rotations.
        clockWheel.startAnimation(rotateThis)

        //set up the date variables
        val sdf = SimpleDateFormat("yyyy-dd-mm")
        val currentDate = sdf.format(Date())
        var dateVar = view.findViewById<TextView>(R.id.date)
        dateVar!!.setText(currentDate)

        //set up the background colors
        val img = view.findViewById<ConstraintLayout>(R.id.ConstraintLayout)
        val animationDrawable2: AnimationDrawable = img.background as AnimationDrawable
        animationDrawable2?.setEnterFadeDuration(2500)
        animationDrawable2?.setExitFadeDuration(5000)
        animationDrawable2?.start()
    }
}