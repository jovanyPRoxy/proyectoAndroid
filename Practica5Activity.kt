package com.example.proyectokotlin

import DatabaseHelper
import android.content.ContentValues
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectokotlin.databinding.ActivityPractica5Binding

class Practica5Activity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var listViewNames: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val namesList = mutableListOf<Pair<Int, String>>()
    private lateinit var binding: ActivityPractica5Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPractica5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)
        listViewNames = findViewById(R.id.listViewNames)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, namesList.map { it.second })
        listViewNames.adapter = adapter
        listViewNames.choiceMode = ListView.CHOICE_MODE_SINGLE

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            saveName(name)
            updateListView()
        }

        buttonDelete.setOnClickListener {
            val position = listViewNames.checkedItemPosition
            if (position != ListView.INVALID_POSITION) {
                val id = namesList[position].first
                deleteName(id)
                updateListView()
            }
        }

        updateListView()
    }

    private fun saveName(name: String) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, name)
        }
        db.insert(DatabaseHelper.TABLE_NAME, null, values)
    }

    private fun deleteName(id: Int) {
        databaseHelper.deleteName(id)
    }

    private fun updateListView() {
        namesList.clear()
        namesList.addAll(databaseHelper.getAllNames())
        adapter.clear()
        adapter.addAll(namesList.map { it.second })
        adapter.notifyDataSetChanged()
    }
}