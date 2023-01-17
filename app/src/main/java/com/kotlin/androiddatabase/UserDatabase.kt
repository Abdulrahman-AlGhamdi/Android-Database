package com.kotlin.androiddatabase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class UserDatabase(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val initTable = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_EMAIL TEXT)"

        db.execSQL(initTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"

        db.execSQL(dropTable)
        onCreate(db)
    }

    fun insertUser(name: String, email: String) {
        val db = writableDatabase
        val values = ContentValues()

        values.put(COLUMN_NAME, name)
        values.put(COLUMN_EMAIL, email)
        db.insert(TABLE_NAME, null, values)
    }

    fun getUser(email: String): UserModel? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = '$email'"
        val cursor = db.rawQuery(query, null)
        var userInfo: UserModel? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))

            userInfo = UserModel(id = id, name = name, email = email)
        }

        return userInfo
    }

    fun getUsers(): List<UserModel> {
        val db = readableDatabase
        val users = arrayListOf<UserModel>()
        val query = "SELECT * FROM $TABLE_NAME"

        try {
            val cursor = db.rawQuery(query, null)

            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))

                val userModel = UserModel(id = id, name = name, email = email)

                users.add(userModel)
            }

        } catch (exception: SQLiteException) {
            Log.d("exception", "getUsers: ${exception.message}")
        }

        return users
    }

    fun changeUser(newEmail: String, oldEmail: String) {
        val db = writableDatabase
        val values = ContentValues()
        val where = "$COLUMN_EMAIL = '$oldEmail'"

        values.put(COLUMN_EMAIL, newEmail)
        db.update(TABLE_NAME, values, where, null)
    }

    fun removeUser(id: Int) {
        val db = writableDatabase
        val where = "$COLUMN_ID = $id"

        db.delete(TABLE_NAME, where, null)
    }

    companion object {
        // Database Info
        const val DATABASE_NAME = "user_database"
        const val DATABASE_VERSION = 1

        // Table Info
        const val TABLE_NAME = "user_table"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
    }
}