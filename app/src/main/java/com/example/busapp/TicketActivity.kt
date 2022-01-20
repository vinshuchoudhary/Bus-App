package com.example.busapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.busapp.databinding.ActivityTicketBinding

class TicketActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.qrCode.setImageBitmap(SeatActivity.encoder.bitmap)
        binding.mainScreenBtn.setOnClickListener {
            startActivity(Intent(this,mainScreen::class.java))
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent =Intent(this,mainScreen::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

}