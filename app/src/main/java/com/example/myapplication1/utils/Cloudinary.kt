package com.example.myapplication1.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.myapplication1.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

// Upload fajla (File)
fun uploadImageToCloudinary(file: File, profileViewModel:ProfileViewModel,onSuccess: (String) -> Unit) {
    val uploadPreset = "unsigned_profile_upload" //  preset
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    MediaManager.get()
        .upload(file.absolutePath)
        .unsigned(uploadPreset)
        .callback(object : UploadCallback {
            override fun onStart(requestId: String?) {}
            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}
            override fun onSuccess(requestId: String?, resultData: Map<*, *>?) {
                val url = resultData?.get("secure_url") as? String
                if (url != null && uid!= null) {
                    profileViewModel.setProfileImageUrl(uid,url)
                    onSuccess(url)
                }

            }
            override fun onError(requestId: String?, error: ErrorInfo?) {}
            override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
        }).dispatch()
}

// Upload bitmap-a (pretvori u File pa uploaduj)
fun uploadImageToCloudinary(context: Context, profileViewModel: ProfileViewModel,bitmap: Bitmap, onSuccess: (String) -> Unit) {
    val file = saveBitmapToCache(context, bitmap)

    uploadImageToCloudinary(file, profileViewModel,onSuccess) // koristi gornju funkciju
}


fun saveBitmapToCache(context: Context, bitmap: Bitmap): File {
    val file = File(context.cacheDir, "profile_${System.currentTimeMillis()}.jpg")
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
    }
    return file
}


fun getFileFromUri(context: Context, uri: Uri): File {
    // Kreiramo privremeni fajl u cache folderu
    val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.jpg")
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val outputStream = FileOutputStream(file)

    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    return file
}