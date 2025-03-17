package com.example.proyectokotlin


import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectokotlin.databinding.ActivityPractica2Binding
import com.example.proyectokotlin.databinding.ActivityPractica4Binding
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class Practica4Activity : AppCompatActivity() {

    private lateinit var textTimeRemaining: TextView
    private lateinit var textCounter: TextView
    private lateinit var buttonStart: Button
    private lateinit var buttonPress: Button
    private lateinit var buttonShowScores: Button
    private lateinit var textScores: TextView
    private lateinit var binding: ActivityPractica4Binding

    private var counter = 0
    private var timeLeftInMillis: Long = 5000 // 5 segundos

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPractica4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        textTimeRemaining = findViewById(R.id.text_time_remaining)
        textCounter = findViewById(R.id.text_counter)
        buttonStart = findViewById(R.id.button_start)
        buttonPress = findViewById(R.id.button_press)
        buttonShowScores = findViewById(R.id.button_show_scores)
        textScores = findViewById(R.id.text_scores)

        buttonPress.isEnabled = false // Deshabilitar el botón "Presionar" al inicio

        buttonStart.setOnClickListener {
            startTimer()
        }

        buttonPress.setOnClickListener {
            counter++
            textCounter.text = "Contador: $counter"
        }

        buttonShowScores.setOnClickListener {
            showScores()
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
                saveScore(counter) // Guardar el score al finalizar el tiempo
            }
        }.start()
    }

    private fun updateCountDownText() {
        val seconds = (timeLeftInMillis / 1000).toInt()
        textTimeRemaining.text = "Tiempo restante: $seconds segundos"
    }

    private fun saveScore(score: Int) {
        val filename = "scores.txt"
        val currentTime = System.currentTimeMillis()
        val dateTime = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date(currentTime))
        val fileContents = "$score - $dateTime\n" // Score, fecha y hora en una nueva línea

        try {
            val fileOutputStream: FileOutputStream = openFileOutput(filename, Context.MODE_APPEND)
            fileOutputStream.write(fileContents.toByteArray())
            fileOutputStream.close()
            Toast.makeText(this, "Score guardado", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error al guardar el score", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showScores() {
        val filename = "scores.txt"
        val stringBuilder = StringBuilder()

        try {
            val fileInputStream: FileInputStream = openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String? = bufferedReader.readLine()

            while (line != null) {
                stringBuilder.append(line).append("\n")
                line = bufferedReader.readLine()
            }

            fileInputStream.close()
            textScores.text = stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            textScores.text = "No hay scores guardados."
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel() // Cancelar el timer si la actividad se destruye
    }
}