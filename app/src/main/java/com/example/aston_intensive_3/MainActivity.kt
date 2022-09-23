package com.example.aston_intensive_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aston_intensive_3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonTask1.setOnClickListener {
            goToTask1()
        }
        binding.buttonTask2.setOnClickListener {
            goToTask2()
        }

    }

    private fun goToTask2() {
        val intent = Intent(this, Task2::class.java)
        startActivity(intent)
    }

    private fun goToTask1() {
        val intent = Intent(this, Task1::class.java)
        startActivity(intent)
    }
}