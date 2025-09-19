package com.example.myapplication1.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication1.R

@Composable
fun LeaderboardPage(modifier: Modifier, navController: NavController) {

    Column(
        modifier = modifier.fillMaxSize()
            .background(Color(0xFF34ADBB)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.leaderboard),
            contentDescription = "Leaderboard",
            modifier = Modifier
                .size(150.dp)
            // .clip(CircleShape)

        )
        Text(text = "Leaderboard", fontSize = 38.sp)




    }
}
