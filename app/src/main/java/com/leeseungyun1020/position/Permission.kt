package com.leeseungyun1020.position

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

const val DESC = "DESC"
const val IMAGE_ID = "IMAGE_ID"
const val MULTILE = "MULTILE"
const val PERMISSION = "PERMISSION"


fun startPermissionActivity(
    context: Context,
    permission: String,
    imageID: Int,
    description: String
) {
    if (ContextCompat.checkSelfPermission(
            context,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        val intent = android.content.Intent(context, PermissionActivity::class.java)
        intent.putExtra(DESC, description)
        intent.putExtra(IMAGE_ID, imageID)
        intent.putExtra(MULTILE, false)
        intent.putExtra(PERMISSION, permission)
        context.startActivity(intent)
    }
}

fun startMultiplePermissionActivity(
    context: Context,
    permission: Array<String>,
    imageID: Int,
    description: String
) {
    val intent = android.content.Intent(context, PermissionActivity::class.java)
    intent.putExtra(DESC, description)
    intent.putExtra(IMAGE_ID, imageID)
    intent.putExtra(MULTILE, true)
    intent.putExtra(PERMISSION, permission)
    context.startActivity(intent)
}

fun startLocationPermissionActivity(context: Context) {
    val intent = android.content.Intent(context, PermissionActivity::class.java)
    intent.putExtra(DESC, context.getString(R.string.desc_location_permission))
    intent.putExtra(IMAGE_ID, R.drawable.ic_baseline_location_24)
    intent.putExtra(MULTILE, true)
    intent.putExtra(
        PERMISSION, arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    context.startActivity(intent)
}