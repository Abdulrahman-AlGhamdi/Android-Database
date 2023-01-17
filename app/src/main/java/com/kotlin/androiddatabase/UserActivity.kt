package com.kotlin.androiddatabase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UserActivity : AppCompatActivity() {

    private val database = UserDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val email = intent.getStringExtra("email") ?: ""
        val user = findViewById<TextView>(R.id.user_info)
        val edit = findViewById<Button>(R.id.edit)
        val delete = findViewById<Button>(R.id.delete)
        val input = findViewById<EditText>(R.id.email_input)
        val userInfo = database.getUser(email)

        if (userInfo == null) {
            Toast.makeText(this, "No User Info", Toast.LENGTH_SHORT).show()
            finish()
        } else user.text = """
            User ID: ${userInfo.id}
            User Name: ${userInfo.name}
            User Email: ${userInfo.email}
        """.trimIndent()

        edit.setOnClickListener {
            val newEmail = input.text.toString()
            database.changeUser(newEmail = newEmail, oldEmail = userInfo!!.email)
            finish()
        }

        delete.setOnClickListener {
            database.removeUser(userInfo!!.id)
            finish()
        }
    }
}