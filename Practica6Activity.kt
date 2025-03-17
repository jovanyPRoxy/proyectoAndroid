package com.example.proyectokotlin

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectokotlin.databinding.ActivityPractica6Binding

class Practica6Activity : AppCompatActivity() {
    private lateinit var btnPlay: Button
    private lateinit var btnIncrease: Button
    private lateinit var btnScore: Button
    private lateinit var tvTimer: TextView
    private lateinit var etName: EditText
    private lateinit var tvScore: TextView
    private var score = 0
    private lateinit var dbHelper: DBHelper
    private var timer: CountDownTimer? = null
    private lateinit var binding: ActivityPractica6Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPractica6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        btnPlay = findViewById(R.id.btnPlay)
        btnIncrease = findViewById(R.id.btnIncrease)
        btnScore = findViewById(R.id.btnScore)
        tvTimer = findViewById(R.id.tvTimer)
        etName = findViewById(R.id.etName)
        tvScore = findViewById(R.id.tvScore)
        dbHelper = DBHelper(this)

        btnIncrease.isEnabled = false

        btnPlay.setOnClickListener {
            startGame()
        }

        btnIncrease.setOnClickListener {
            if (timer != null) {
                score++
                tvScore.text = "Puntaje: $score"
            }
        }

        btnScore.setOnClickListener {
            showScores()
        }
    }

    private fun startGame() {
        score = 0
        tvScore.text = "Puntaje: $score"
        btnPlay.isEnabled = false
        btnIncrease.isEnabled = true
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.text = "Tiempo: ${millisUntilFinished / 1000}"
            }
            override fun onFinish() {
                btnPlay.isEnabled = true
                btnIncrease.isEnabled = false
                saveScore()
            }
        }.start()
    }

    private fun saveScore() {
        val name = etName.text.toString()
        if (name.isNotEmpty()) {
            dbHelper.insertScore(name, score)
            Toast.makeText(this, "Puntaje guardado!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Ingresa tu nombre", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showScores() {
        val scores = dbHelper.getScores()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Puntajes")
        builder.setMessage(scores)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }
}

class DBHelper(context: Context) : SQLiteOpenHelper(context, "scores.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE scores (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, score INTEGER)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun insertScore(name: String, score: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("score", score)
        }
        db.insert("scores", null, values)
        db.close()
    }

    fun getScores(): String {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT name, score FROM scores ORDER BY score DESC", null)
        val result = StringBuilder()
        while (cursor.moveToNext()) {
            result.append("${cursor.getString(0)}: ${cursor.getInt(1)}\n")
        }
        cursor.close()
        db.close()
        return result.toString()
    }
}