package com.example.myapplication1


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels


import androidx.compose.ui.Modifier

import com.example.myapplication1.ui.theme.MyApplication1Theme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel : AuthViewModel by viewModels()
        setContent {

            MyApplication1Theme {
                        MainScreen(modifier = Modifier,authViewModel = authViewModel)
                }
            }
        }
    }


