package com.example.easydoc_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString().trim()

            when {
                userEmail.isEmpty() -> {
                    Toast.makeText(this, "E-Mail darf nicht leer sein!", Toast.LENGTH_SHORT).show()
                }
                userPassword.isEmpty() -> {
                    Toast.makeText(this, "Passwort darf nicht leer sein!", Toast.LENGTH_SHORT).show()
                }
                !userEmail.contains("@") || !userEmail.contains(".") -> {
                    Toast.makeText(this, "Bitte eine gültige E-Mail-Adresse eingeben!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Hier könnte die Login-Logik kommen (z. B. API-Call)
                    Toast.makeText(this, "Login erfolgreich!", Toast.LENGTH_SHORT).show()
                    // Intent für die Weiterleitung nach erfolgreichem Login
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
