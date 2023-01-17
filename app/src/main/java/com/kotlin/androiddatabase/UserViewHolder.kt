package com.kotlin.androiddatabase

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name = view.findViewById<TextView>(R.id.name)
    private val email = view.findViewById<TextView>(R.id.email)
    private val root = view.findViewById<ConstraintLayout>(R.id.root)

    fun bind(user: UserModel) {
        name.text = user.name
        email.text = user.email

        root.setOnClickListener {
            Log.d("TAG", "bind: CLICKED!!")
        }
    }
}