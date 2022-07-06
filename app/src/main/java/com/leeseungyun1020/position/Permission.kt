package com.leeseungyun1020.position

import android.content.Context

val DESC = "DESC"
val IMAGE_ID = "IMAGE_ID"


fun startPermissionActivity(context: Context, imageID: Int, description: String) {
    val intent = android.content.Intent(context, PermissionActivity::class.java)
    intent.putExtra(DESC, description)
    intent.putExtra(IMAGE_ID, imageID)
    context.startActivity(intent)
}