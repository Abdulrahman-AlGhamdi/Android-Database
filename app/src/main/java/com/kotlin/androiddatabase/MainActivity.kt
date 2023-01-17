package com.kotlin.androiddatabase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val database = UserDatabase(this)
    private lateinit var adapter: UserRecyclerView
    private lateinit var usersList: RecyclerView
    private lateinit var userInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersList = findViewById(R.id.users)
        userInput = findViewById(R.id.email)
        val addButton = findViewById<Button>(R.id.add)

        listAdapter()

        addButton.setOnClickListener {
            val name = "Fahd"
            val email = userInput.text.toString()

            database.insertUser(name = name, email = email)
            userInput.setText("")

            listAdapter()
        }
    }

    private fun listAdapter() {
        adapter = UserRecyclerView(database.getUsers())

        usersList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
//        listAdapter()
    }
}