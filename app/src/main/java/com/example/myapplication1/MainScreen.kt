package com.example.myapplication1

import MyNavigation
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cloudinary.android.MediaManager
import com.example.myapplication1.viewmodel.AuthViewModel
import com.example.myapplication1.viewmodel.ProfileViewModel


@Composable
fun MainScreen(modifier: Modifier, authViewModel: AuthViewModel, ProfileViewModel: ProfileViewModel)
{

    val navController = rememberNavController()



    val navItemList = listOf(
        NavItem("Leaderboard",Icons.Default.Star ,"leaderboard"),
        NavItem("Run", Icons.Default.Face,"home"),
        NavItem("Profile", Icons.Default.AccountCircle,"profile"),
    )
    var selectedIndex by remember{
        mutableIntStateOf(1)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),

            bottomBar = {
                if(currentRoute != "login" && currentRoute!= "signup" ) {
                NavigationBar {
                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                navController.navigate(navItem.route){
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(imageVector = navItem.icon, contentDescription = "Icon")
                            },
                            label = {
                                Text(text = navItem.label)
                            }
                        )
                    }

                }
            }

        }
    )
    { innerPadding ->
        MyNavigation(modifier = Modifier.padding(innerPadding),authViewModel = authViewModel,navController = navController,
            ProfileViewModel = ProfileViewModel )


    }
}