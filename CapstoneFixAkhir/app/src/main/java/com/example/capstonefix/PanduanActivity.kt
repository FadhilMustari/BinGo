package com.example.capstonefix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstonefix.databinding.ActivityPanduanBinding
import com.example.capstonefix.ui.Dashboard.dashboard.DashboardActivity

class PanduanActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPanduanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanduanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener{
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
}