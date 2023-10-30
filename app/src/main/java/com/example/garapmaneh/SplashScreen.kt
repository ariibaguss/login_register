package com.example.garapmaneh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.garapmaneh.databinding.ActivitySplashscreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding : ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            // Ini pindah ke MainActivity_Login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3000) // Delay selama 3 detik
    }
}
