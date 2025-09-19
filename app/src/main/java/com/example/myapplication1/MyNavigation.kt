import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication1.pages.LeaderboardPage
import com.example.myapplication1.viewmodel.AuthViewModel
import com.example.myapplication1.pages.LogInPage
import com.example.myapplication1.pages.PickPicture
import com.example.myapplication1.pages.SingUpPage
import com.example.myapplication1.pages.ProfilePage
import com.example.myapplication1.pages.LeaderboardPage
import com.example.myapplication1.viewmodel.ProfileViewModel


@Composable
fun MyNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navController: NavHostController,
                 ProfileViewModel: ProfileViewModel
) {


    NavHost(navController = navController, startDestination="login", builder = {
        composable(route= "login"){
            LogInPage(modifier,navController,authViewModel)
        }
        composable(route= "signup"){
            SingUpPage(modifier,navController,authViewModel)
        }
        composable(route= "home"){
            HomePage(modifier,navController,authViewModel)
        }

        composable(route= "profile"){
            ProfilePage(modifier,navController,authViewModel,ProfileViewModel)
        }

        composable(route= "leaderboard"){
            LeaderboardPage(modifier,navController)
        }
        composable(route= "pickPicture"){
            PickPicture(modifier,navController, ProfileViewModel )
        }
    })
}

