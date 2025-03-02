package com.example.easydoc_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.mindrot.jbcrypt.BCrypt

class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)
        registerLink = findViewById(R.id.registerLink)

        loginButton.setOnClickListener {
        }

        registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleLogin() {
        val userEmail = email.text.toString().trim()
        val userPassword = password.text.toString().trim()

        if (userEmail.isEmpty()) {
            showToast("E-Mail darf nicht leer sein!")
            return
        }

        if (userPassword.isEmpty()) {
            showToast("Passwort darf nicht leer sein!")
            return
        }

        val sharedPref = getSharedPreferences("user_data", MODE_PRIVATE)
        val savedEmail = sharedPref.getString("email", null)
        val savedHashedPassword = sharedPref.getString("password", null)

        if (savedEmail == userEmail && savedHashedPassword != null && BCrypt.checkpw(userPassword, savedHashedPassword)) {
            showToast("Login erfolgreich!")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            showToast("Falsche Anmeldeinformationen!")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

