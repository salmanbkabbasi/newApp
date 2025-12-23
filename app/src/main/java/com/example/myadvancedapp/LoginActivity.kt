package com.example.myadvancedapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply theme before view creation
        sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val themeId = sharedPreferences.getInt("selected_theme", R.style.Theme_MyAdvancedApp)
        setTheme(themeId)

        super.onCreate(savedInstanceState)

        // Check login state
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        if (isLoggedIn) {
            navigateToMain()
            return
        }

        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Simulate authentication
                val editor = sharedPreferences.edit()
                editor.putBoolean("is_logged_in", true)
                editor.putString("username", username)
                editor.apply()
                navigateToMain()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}