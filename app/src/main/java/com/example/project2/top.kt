package com.example.project2

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
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
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [top.newInstance] factory method to
 * create an instance of this fragment.
 */
class top : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var mMediaPlayer: MediaPlayer? = null

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
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState )
        //find all the attributes so we can manipulate them latter
        //Buttons
       val treasureGameButton =  view.findViewById<Button>(R.id.btnTearsure)
        val startButton =  view.findViewById<Button>(R.id.btnstart)
        val stopButton= view.findViewById<Button>(R.id.btnstop)
        //imageViews
        val birds = view.findViewById<ImageView>(R.id.ivBirds)
        val clouds = view.findViewById<ImageView>(R.id.ivClouds)
        val sun = view.findViewById<ImageView>(R.id.ivSun)
        //constraint view
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.ConstraintFrameLayout)
        //text view
        val nameTextView = view.findViewById<TextView>(R.id.tvName)

        //set a drawable to the constraints background and start the fade of colors.
        val animationDrawable: AnimationDrawable = constraintLayout.background as AnimationDrawable
           animationDrawable.setEnterFadeDuration(2500)
            animationDrawable.setExitFadeDuration(5000)
            animationDrawable.start()
        //assign animation for playing
        var rightToLeft= AnimationUtils.loadAnimation(context,R.anim.right_to_left)
        var leftToRight= AnimationUtils.loadAnimation(context,R.anim.left_to_right)
        var zoomLeftToRight = AnimationUtils.loadAnimation(context,R.anim.left_to_right_zoom)
        var rotateIt = AnimationUtils.loadAnimation(context,R.anim.rotation)

        //set a onClick to this button and give it is indented job.
        //treasure game onclick start.
        treasureGameButton.setOnClickListener()
        {
                val intent = Intent (activity, GameScreen::class.java)
                startActivity(intent)
        }
        //start button onclick
        startButton.setOnClickListener() {
            //start the animations on all of the images and background color changes
            birds.startAnimation(zoomLeftToRight)
            clouds.startAnimation(leftToRight)
            sun.startAnimation(rightToLeft)
            //start background color changes
            animationDrawable.start()
            //start the sound
            playSound()
            //ensure when we start the buttons stop moving and go back to their normal positions.
            startButton.clearAnimation()
            stopButton.clearAnimation()
            treasureGameButton.clearAnimation()
            nameTextView.clearAnimation()
        }

        stopButton.setOnClickListener()
        {
            //clear the animations
            birds.clearAnimation()
            clouds.clearAnimation()
            sun.clearAnimation()
            animationDrawable.stop()
            //stop the music
            stopSound()
            //start the animation for the buttons and name
            startButton.startAnimation(rotateIt)
            stopButton.startAnimation(rotateIt)
            treasureGameButton.startAnimation(rotateIt)
            nameTextView.startAnimation(rotateIt)
        }

    }
    //create a function to take care of the play sound
    private fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(context, R.raw.sunshine)
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }
    //create a function to take care of the stop sound
   private fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

}