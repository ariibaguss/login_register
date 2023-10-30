package com.example.garapmaneh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.garapmaneh.database.User
import com.example.garapmaneh.database.UserDao
import com.example.garapmaneh.database.UserRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        // Inisialisasi userDao dari database Room
        userDao = UserRoomDatabase.getDatabase(this).UserDao()

        val LoginTextView: TextView = findViewById(R.id.masuk)

        LoginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finish()
        }

        val btnRegister = findViewById<Button>(R.id.buttondaftar)
        val etUsername = findViewById<EditText>(R.id.nama)
        val etEmail = findViewById<EditText>(R.id.email2)
        val etPassword = findViewById<EditText>(R.id.pass2)
        val etGithub = findViewById<EditText>(R.id.git)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val github = etGithub.text.toString()

            // Simpan data ke database Room
            val user = User(username = username, email = email, password = password, github = github)

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    userDao.insert(user)

                    runOnUiThread {
                        // Setelah operasi database selesai, tampilkan pesan "Daftar Berhasil"
                        Toast.makeText(this@RegisterActivity, "Daftar Berhasil", Toast.LENGTH_SHORT).show()
                    }

                    // Setelah operasi database selesai, beralih ke LoginActivity
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                } catch (e: Exception) {
                    // Tangani kesalahan di sini, seperti mencetak pesan kesalahan atau menampilkan pesan kesalahan ke pengguna
                    e.printStackTrace()
                }
            }
        }
    }
}
