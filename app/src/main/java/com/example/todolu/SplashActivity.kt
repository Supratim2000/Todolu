package com.example.todolu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class SplashActivity : AppCompatActivity() {

    private lateinit var welcomeTV: TextView

    private val SPLASH_DURATION: Long = 6000
    private val SPLASH_TEXT_ANIMATION_DURATION: Long = 1500
    private val SPLASH_TEXT_ANIMATION_REPEATE: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        welcomeTV = findViewById(R.id.welcomeTV)

        textEffect()

        val handler: Handler = Handler()
        handler.postDelayed(object: Runnable {
            override fun run() {
                val splashIntent: Intent = Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(splashIntent)
                finish()
            }
        },SPLASH_DURATION)
    }
    private fun textEffect(): Unit {
        YoYo.with(Techniques.BounceInRight)
            .duration(SPLASH_TEXT_ANIMATION_DURATION)
            .repeat(SPLASH_TEXT_ANIMATION_REPEATE)
            .playOn(welcomeTV)
    }
}