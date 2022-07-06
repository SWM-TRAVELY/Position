package com.leeseungyun1020.position

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leeseungyun1020.position.databinding.ActivityPermissionBinding

class PermissionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPermissionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageID = intent.getIntExtra(IMAGE_ID, R.drawable.ic_outline_priority_high_24)
        binding.permissionImage.setImageResource(imageID)

        val description = intent.getStringExtra(DESC)
        binding.permissionText.text = description


    }
}