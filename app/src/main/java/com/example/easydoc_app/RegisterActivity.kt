package com.example.easydoc_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.easydoc_app.data.model.User

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val email = findViewById<EditText>(R.id.etRegisterEmail)
        val password = findViewById<EditText>(R.id.etRegisterPassword)
        val registerButton = findViewById<Button>(R.id.btnRegister)
        // Fehlender Click-Listener für "Zurück zum Login"
        val tvBackToLogin = findViewById<TextView>(R.id.tvBackToLogin)
        tvBackToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        registerButton.setOnClickListener {
            val mail = email.text.toString().trim()
            val pass = password.text.toString().trim()

            if (mail.isBlank() || pass.isBlank()) {
                Toast.makeText(this, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(mail, pass)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Bitte eine gültige E-Mail-Adresse eingeben", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "Das Passwort muss mindestens 6 Zeichen lang sein", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""
                    val user = User(userId, email, password)

                    db.collection("users").document(userId).set(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Registrierung war erfolgreich", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Fehler beim Speichern der Daten", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Fehler: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
