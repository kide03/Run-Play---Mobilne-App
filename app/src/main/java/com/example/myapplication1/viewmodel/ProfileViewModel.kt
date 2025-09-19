package com.example.myapplication1.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)


    // Kad se app pokrene, odmah učitaj URL iz memorije
    val profileImageUrl = mutableStateOf(prefs.getString("profile_url", null))

    fun setProfileImageUrl(uid:String, url: String) {
        profileImageUrl.value = url
        prefs.edit().putString("profile_url_$uid", url).apply()
    }

    fun loadProfileImageUrl(uid: String) {
        profileImageUrl.value = prefs.getString("profile_url_$uid", null)
    }
}
