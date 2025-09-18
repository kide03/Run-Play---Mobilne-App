package com.example.myapplication1

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun MapWithForeground(context: Context) {
    var locationService by remember { mutableStateOf<LocationForegroundService?>(null) }
    val connection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                val b = binder as LocationForegroundService.LocalBinder
                locationService = b.getService()
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                locationService = null
            }
        }
    }


    DisposableEffect(Unit) {
        val intent = Intent(context, LocationForegroundService::class.java)
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)

        onDispose { context.unbindService(connection) }
    }


    if (locationService == null) {
        Text("Connecting to location service...")
    } else {
        Map(locationService!!)
    }
}
