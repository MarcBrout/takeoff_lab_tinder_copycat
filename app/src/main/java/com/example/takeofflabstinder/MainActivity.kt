package com.example.takeofflabstinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.takeofflabstinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)



        setContentView(binding.root)
    }
}