package com.kotlin.androiddatabase

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get

class MainActivity : AppCompatActivity() {

    private val database = UserDatabase(this)
    private lateinit var usersList: ListView
    private lateinit var userInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersList = findViewById(R.id.users)
        val addButton = findViewById<Button>(R.id.add)
        val userInput = findViewById<EditText>(R.id.email)

        listAdapter()

        usersList.setOnItemClickListener { adapter, _, position, _ ->
            Log.d("TAG", "onCreate: ${adapter.count}")
            Log.d("TAG", "onCreate: $position")
            Log.d("TAG", "onCreate: ${adapter.getItemAtPosition(position)}")
        }

        addButton.setOnClickListener {
            val name = "Fahd"
            val email = userInput.text.toString()

            database.insertUser(name = name, email = email)
            userInput.setText("")

            listAdapter()
        }
    }

    private fun listAdapter() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            database.getUsers()
        )

        usersList.adapter = adapter
    }
}