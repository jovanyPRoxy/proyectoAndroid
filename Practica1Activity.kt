package com.example.proyectokotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectokotlin.databinding.ActivityPractica1Binding

class Practica1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityPractica1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPractica1Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}