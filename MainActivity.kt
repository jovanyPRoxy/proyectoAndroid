package com.example.proyectokotlin


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectokotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPractica1.setOnClickListener {
            startActivity(Intent(this, Practica1Activity::class.java))
        }

        binding.buttonPractica2.setOnClickListener {
            startActivity(Intent(this, Practica2Activity::class.java))
        }

        binding.buttonPractica3.setOnClickListener {
            startActivity(Intent(this, Practica3Activity::class.java))
        }

        binding.buttonPractica4.setOnClickListener {
            startActivity(Intent(this, Practica4Activity::class.java))
        }

        binding.buttonPractica5.setOnClickListener {
            startActivity(Intent(this, Practica5Activity::class.java))
        }

        binding.buttonPractica6.setOnClickListener {
            startActivity(Intent(this, Practica6Activity::class.java))
        }
    }
}