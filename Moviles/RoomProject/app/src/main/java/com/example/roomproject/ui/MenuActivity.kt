package com.example.roomproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roomproject.R
import com.example.roomproject.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}