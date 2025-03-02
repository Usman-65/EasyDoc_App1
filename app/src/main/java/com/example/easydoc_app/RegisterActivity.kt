package com.example.easydoc_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easydoc_app.databinding.ActivityRegisterBinding
import org.mindrot.jbcrypt.BCrypt

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener { handleRegistration() }

        binding.loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun handleRegistration() {
        val userEmail = binding.email.text.toString().trim()
        val userPassword = binding.password.text.toString().trim()
        val confirmPassword = binding.confirmPassword.text.toString().trim()

        when {
            userEmail.isEmpty() -> showToast("E-Mail darf nicht leer sein!")
            !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches() ->
                showToast("Bitte eine gültige E-Mail-Adresse eingeben!")
            userPassword.isEmpty() -> showToast("Passwort darf nicht leer sein!")
            userPassword.length < 6 -> showToast("Passwort muss mindestens 6 Zeichen lang sein!")
            userPassword != confirmPassword -> showToast("Passwörter stimmen nicht überein!")
            else -> {
                //  Passwort hashen mit BCrypt
                val hashedPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt())

                // Speichert die Benutzerdaten
                val sharedPref = getSharedPreferences("user_data", MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("email", userEmail)
                    putString("password", hashedPassword)
                    apply()
                }

                // Registrierung erfolgreich
                showToast("Registrierung erfolgreich!")
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
