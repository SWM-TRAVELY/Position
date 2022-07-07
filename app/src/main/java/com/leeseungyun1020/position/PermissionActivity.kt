package com.leeseungyun1020.position

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

        val isMultiple = intent.getBooleanExtra(MULTILE, true)
        if (isMultiple) {
            val permissions = intent.getStringArrayExtra(PERMISSION)
            val permissionRequest = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        finish()
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        // Only approximate location access granted.
                    }
                    else -> {
                        // No location access granted.
                    }
                }
            }
            permissionRequest.launch(permissions)
            binding.permissionButton.setOnClickListener {
                permissionRequest.launch(permissions)
            }
        } else {
            val permission = intent.getStringExtra(PERMISSION)
            if (permission == null)
                finish()
            else {
                val requestPermissionLauncher =
                    registerForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) { isGranted: Boolean ->
                        if (isGranted) {
                            finish()
                        }
                    }
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    finish()
                } else {
                    requestPermissionLauncher.launch(
                        permission
                    )
                    binding.permissionButton.setOnClickListener {
                        requestPermissionLauncher.launch(
                            permission
                        )
                    }
                }
            }
        }
    }
}