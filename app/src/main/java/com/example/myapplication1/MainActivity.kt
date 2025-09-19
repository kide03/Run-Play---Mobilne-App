package com.example.myapplication1


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels


import androidx.compose.ui.Modifier
import com.cloudinary.android.MediaManager

import com.example.myapplication1.ui.theme.MyApplication1Theme
import com.example.myapplication1.viewmodel.AuthViewModel
import com.example.myapplication1.viewmodel.ProfileViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


            val config: HashMap<String, String> = HashMap()
            config["cloud_name"] = "drz6de4b1"
        try {


            MediaManager.init(this, config)
        }
        catch (e: IllegalStateException){}


        val authViewModel : AuthViewModel by viewModels()
        val ProfileViewModel : ProfileViewModel by viewModels()

        setContent {

            MyApplication1Theme {
                        MainScreen(modifier = Modifier,authViewModel = authViewModel, ProfileViewModel = ProfileViewModel)

                }
            }
        }
    }


