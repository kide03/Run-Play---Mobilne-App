import android.Manifest
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication1.viewmodel.AuthViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.myapplication1.LocationForegroundService
import com.example.myapplication1.MapWithForeground
import com.example.myapplication1.Timer

enum class TimerState {
    Idle, Running, Paused
}


@Composable
fun HomePage (modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {

    val timer = remember { Timer() }
    val time by timer.timeState.collectAsState()
    var state by remember { mutableStateOf(TimerState.Idle) }
    var locationService by remember { mutableStateOf<LocationForegroundService?>(null) }

    val context = LocalContext.current

    val locationPermissionRequest =rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission())
    { isGranted: Boolean ->
        if (isGranted) {
            ContextCompat.startForegroundService(
                context,
                Intent(context, LocationForegroundService::class.java)
            )
        } else {
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT)
                .show()
        }
    }
LaunchedEffect(Unit) { locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION) }


Box(modifier = Modifier)
{
    Column(
        modifier = modifier.fillMaxSize()
            .background(Color(0xFF34ADBB)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Home Page", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))

        if (state != TimerState.Idle) {
            Text(text = time, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
        }




        when (state) {
            TimerState.Idle -> {
                Button(onClick = {

                    timer.start()

                    state = TimerState.Running
                }) {
                    Text("Start Run")
                }
            }


            TimerState.Running -> {
                Button(onClick = {
                    timer.pause()
                    state = TimerState.Paused


                }) {
                    Text("Pause")
                }

            }

            TimerState.Paused -> {

                Row {

                    Button(onClick = {
                        timer.start()
                        state = TimerState.Running

                    })
                    {
                        Text("Continue")
                    }
                    Spacer(modifier = Modifier.width(16.dp))

                    Button(onClick = {
                        timer.finish()
                        state = TimerState.Idle

                    })
                    {
                        Text("Finish")
                    }
                }
            }
        }

        MapWithForeground(context)


    }

}
    }


