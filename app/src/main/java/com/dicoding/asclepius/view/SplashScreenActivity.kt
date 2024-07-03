package com.dicoding.asclepius.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val imageViewLogo = findViewById<ImageView>(R.id.imageViewLogo)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation)
        imageViewLogo.startAnimation(fadeInAnimation)

        Handler().postDelayed({
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}