package com.example.myadvancedapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myadvancedapp.data.User

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MyAdvancedApp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_WEBSITE = "website"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT,"
                + COLUMN_USERNAME + " TEXT," + COLUMN_EMAIL + " TEXT,"
                + COLUMN_WEBSITE + " TEXT" + ")")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun updateUser(user: User): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, user.name)
        values.put(COLUMN_USERNAME, user.username)
        values.put(COLUMN_EMAIL, user.email)
        values.put(COLUMN_WEBSITE, user.website)

        // updating row
        return db.update(TABLE_USERS, values, "$COLUMN_ID = ?",
                arrayOf(user.id.toString()))
    }

    fun insertUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID, user.id)
        values.put(COLUMN_NAME, user.name)
        values.put(COLUMN_USERNAME, user.username)
        values.put(COLUMN_EMAIL, user.email)
        values.put(COLUMN_WEBSITE, user.website)

        db.insertWithOnConflict(TABLE_USERS, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    fun insertUsers(users: List<User>) {
        val db = this.writableDatabase
        db.beginTransaction()
        try {
            for (user in users) {
                val values = ContentValues()
                values.put(COLUMN_ID, user.id)
                values.put(COLUMN_NAME, user.name)
                values.put(COLUMN_USERNAME, user.username)
                values.put(COLUMN_EMAIL, user.email)
                values.put(COLUMN_WEBSITE, user.website)
                db.insertWithOnConflict(TABLE_USERS, null, values, SQLiteDatabase.CONFLICT_REPLACE)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun getAllUsers(): List<User> {
        val userList = ArrayList<User>()
        val selectQuery = "SELECT  * FROM $TABLE_USERS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val user = User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEBSITE))
                )
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }
    
    fun deleteUser(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_USERS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }
}