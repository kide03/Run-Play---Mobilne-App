package com.example.myapplication1.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication1.viewmodel.AuthState
import com.example.myapplication1.viewmodel.AuthViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.example.myapplication1.R
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.layout.ContentScale
import com.example.myapplication1.viewmodel.ProfileViewModel


@Composable
fun ProfilePage(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel,
                profileViewModel: ProfileViewModel
)
{
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    if (uid != null) {
        profileViewModel.loadProfileImageUrl(uid)
    }

    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value)
        {
            is AuthState.Unauthenticated -> navController.navigate("login"){
                popUpTo(0)
            }
            else ->Unit
        }
    }


    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var numOfFruitMonthly by remember { mutableStateOf("0") }
    var numOfFruitTotal by remember { mutableStateOf("0") }

    LaunchedEffect(Unit) {
        val mDatabase = FirebaseDatabase.getInstance().reference
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@LaunchedEffect
        mDatabase.child("users").child(uid).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val data = it.value as? Map<String, Any>
                     name = data?.get("name") as? String ?: ""
                     surname = data?.get("surname") as? String ?: ""
                     email = data?.get("email") as? String ?: ""

                    Log.i("firebase", "User: $name $surname, email: $email")
                } else {
                    Log.w("firebase", "No data found for userId: $uid")
                }
            }
            .addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
    }




Box(
    modifier = modifier.fillMaxSize()
        .background(Color(0xFF34ADBB))

)
{
    Text(text = "Your profile", fontSize = 36.sp, modifier = modifier.align(Alignment.TopCenter))
    }


    val profileImageUrl = profileViewModel.profileImageUrl.value
    Box(modifier = modifier) {

        if(profileImageUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(profileImageUrl),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        else{
            Image(
                painter = painterResource(id = R.drawable.blank_profile_picture),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop)
        }
        Image(
            painter = painterResource(id = R.drawable.camera),
            contentDescription = null,
            modifier = Modifier
                .alpha(0.4f)
                .align(Alignment.BottomEnd)
                .size(30.dp)
                .clip(CircleShape)
                .clickable { navController.navigate("pickPicture") }
        )

    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(modifier = Modifier.height(120.dp))
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(text = "Name: $name")


        }
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(text = "Surname: ")
            Text(text = surname)



        }
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(text = "Email: $email")



        }

        Spacer(modifier = Modifier.height(14.dp))
        Text(text = "This month fruit collected: $numOfFruitMonthly",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp))

        Text(text = "Total fruit collected: $numOfFruitTotal",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp))

    }


    Column( modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally) {

        TextButton(onClick = {
            authViewModel.signout()
        },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF3F51B5)
            ),
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 14.dp)



        ) {
            Text(text = "Sign out", fontSize = 20.sp)
        }

    }

    }


