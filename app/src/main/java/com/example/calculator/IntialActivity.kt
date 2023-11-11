package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculator.databinding.ActivityIntialBinding

class IntialActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIntialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding : ActivityIntialBinding = ActivityIntialBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.submitButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("EXTRA_MESSAGE", "Hello, ${binding.editText.text}")
            startActivity(intent)
        }
    }
}