package com.example.myadvancedapp

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myadvancedapp.data.User
import com.example.myadvancedapp.db.DatabaseHelper
import com.google.android.material.textfield.TextInputEditText

class EditUserActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etUsername: TextInputEditText
    private lateinit var etWebsite: TextInputEditText
    private lateinit var btnSave: Button
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val themeId = sharedPreferences.getInt("selected_theme", R.style.Theme_MyAdvancedApp)
        setTheme(themeId)
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        dbHelper = DatabaseHelper(this)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etUsername = findViewById(R.id.etUsername)
        etWebsite = findViewById(R.id.etWebsite)
        btnSave = findViewById(R.id.btnSave)

        // Populate fields from Intent extras
        userId = intent.getIntExtra("id", -1)
        etName.setText(intent.getStringExtra("name"))
        etEmail.setText(intent.getStringExtra("email"))
        etUsername.setText(intent.getStringExtra("username"))
        etWebsite.setText(intent.getStringExtra("website"))

        btnSave.setOnClickListener {
            saveUser()
        }
    }

    private fun saveUser() {
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val username = etUsername.text.toString()
        val website = etWebsite.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty() && username.isNotEmpty()) {
            val user = User(userId, name, username, email, website)
            // Update in DB (using updateUser)
            dbHelper.updateUser(user)
            
            Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
        }
    }
}