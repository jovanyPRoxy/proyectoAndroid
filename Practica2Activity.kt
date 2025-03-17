package com.example.proyectokotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectokotlin.databinding.ActivityPractica2Binding

class Practica2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityPractica2Binding
    private var clickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPractica2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonClick.setOnClickListener {
            clickCount++
            binding.textClickCount.text = "Clicks: $clickCount"
        }
    }
}