package com.example.garapmaneh

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.garapmaneh.database.User
import com.example.garapmaneh.database.UserRoomDatabase
import com.example.garapmaneh.databinding.ActivityLoginBinding
import com.example.garapmaneh.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var username: EditText
    private lateinit var pass: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        username = binding.username
        pass = binding.pass
        button = binding.button

        button.setOnClickListener {
            val inputUsername = username.text.toString()
            val inputPassword = pass.text.toString()
            authenticateUser(inputUsername, inputPassword)
        }

        val registerTextView = binding.daftar
        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }

    private fun authenticateUser(inputUsername: String, inputPassword: String) {
        val userLiveData = getUserFromDatabase(inputUsername)
        userLiveData.observe(this, Observer { user ->
            when {
                user != null && user.password == inputPassword -> {
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                    SharedPreferencesUtil.saveLoggedInUser(this, inputUsername, user.email)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    object SharedPreferencesUtil {
        fun saveLoggedInUser(context: Context, username: String, email: String?) {
            val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("userName", username)
            editor.putString("userEmail", email)
            editor.apply()
        }

        fun getLoggedInUser(context: Context): Pair<String?, String?> {
            val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("userName", null)
            val email = sharedPreferences.getString("userEmail", null)
            return Pair(username, email)
        }
    }

    private fun getUserFromDatabase(username: String): LiveData<User> {
        val userDatabase = UserRoomDatabase.getDatabase(applicationContext)
        return userDatabase.UserDao().getUserByUsername(username)
    }
}

