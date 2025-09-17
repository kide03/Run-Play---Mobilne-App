import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication1.AuthViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color

@Composable
fun HomePage (modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {

    Column(
        modifier = modifier.fillMaxSize()
            .background(Color(0xFF34ADBB)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(text =  "Home Page",fontSize =32.sp )


    }


}