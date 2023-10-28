package com.example.garapmaneh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.LiveData
import com.example.garapmaneh.database.User
import com.example.garapmaneh.database.UserRoomDatabase
import com.example.garapmaneh.databinding.ActivityLoginBinding
import com.example.garapmaneh.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        email = binding.email
        pass = binding.pass
        button = binding.button

        button.setOnClickListener {
            val inputEmail = email.text.toString()
            val inputPassword = pass.text.toString()
            authenticateUser(inputEmail, inputPassword)
        }

        val registerTextView = binding.daftar
        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }

    private fun authenticateUser(inputEmail: String, inputPassword: String) {
        val userLiveData = getUserFromDatabase(inputEmail)
        userLiveData.observe(this) { user ->
            when {
                user != null && user.password == inputPassword -> {
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                    val preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                    val editor = preferences.edit()
                    editor.putString("userName", user.username)
                    editor.putString("userEmail", user.email)
                    editor.apply()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getUserFromDatabase(email: String): LiveData<User?> {
        val userDatabase = UserRoomDatabase.getDatabase(applicationContext)
        return userDatabase.UserDao().getUserByEmail(email)
    }
}
