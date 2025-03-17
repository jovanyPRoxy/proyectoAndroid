package com.example.proyectokotlin


import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectokotlin.databinding.ActivityPractica2Binding
import com.example.proyectokotlin.databinding.ActivityPractica3Binding

class Practica3Activity : AppCompatActivity() {

    private lateinit var textTimeRemaining: TextView
    private lateinit var textCounter: TextView
    private lateinit var buttonStart: Button
    private lateinit var buttonPress: Button
    private lateinit var binding: ActivityPractica3Binding
    private var counter = 0
    private var timeLeftInMillis: Long = 5000 // 5 segundos

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPractica3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        textTimeRemaining = findViewById(R.id.text_time_remaining)
        textCounter = findViewById(R.id.text_counter)
        buttonStart = findViewById(R.id.button_start)
        buttonPress = findViewById(R.id.button_press)

        buttonPress.isEnabled = false // Deshabilitar el botón "Presionar" al inicio

        buttonStart.setOnClickListener {
            startTimer()
        }

        buttonPress.setOnClickListener {
            counter++
            textCounter.text = "Contador: $counter"
        }
    }

    private fun startTimer() {
        buttonStart.isEnabled = false
        buttonPress.isEnabled = true
        counter = 0
        textCounter.text = "Contador: 0"
        timeLeftInMillis = 5000 // Restablecer el tiempo aquí

        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                buttonPress.isEnabled = false
                buttonStart.isEnabled = true
                textTimeRemaining.text = "¡Tiempo terminado!"
            }
        }.start()
    }

    private fun updateCountDownText() {
        val seconds = (timeLeftInMillis / 1000)
        textTimeRemaining.text = "Tiempo restante: $seconds segundos"
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel() // Cancelar el timer si la actividad se destruye
    }
}