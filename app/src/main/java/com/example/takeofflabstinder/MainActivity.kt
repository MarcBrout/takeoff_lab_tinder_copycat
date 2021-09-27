package com.example.takeofflabstinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.takeofflabstinder.databinding.ActivityMainBinding
import com.example.takeofflabstinder.profiles.ProfilesFragment
import com.example.takeofflabstinder.utils.with

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        supportFragmentManager
            .with(ProfilesFragment())
            .into(binding.fullscreenFragmentHolder.id)
            .push()

        setContentView(binding.root)
    }
}