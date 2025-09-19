package com.example.myapplication1.pages

import android.Manifest
import android.os.FileUtils
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication1.viewmodel.ProfileViewModel
import com.example.myapplication1.R
import com.example.myapplication1.utils.getFileFromUri
import com.example.myapplication1.utils.saveBitmapToCache
import com.example.myapplication1.utils.uploadImageToCloudinary


@Composable
fun PickPicture(modifier: Modifier, navController: NavController,profileViewModel: ProfileViewModel) {

    val context = LocalContext.current

    val launcherCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) {bmp->
        if(bmp != null)
        {

           uploadImageToCloudinary(context, profileViewModel ,bmp) { url ->
               profileViewModel.profileImageUrl.value = url
           }
        }
        navController.navigate("profile")

    }

    val launchImage =  rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val file = getFileFromUri(context,uri)
            uploadImageToCloudinary(file,profileViewModel) { url ->
                profileViewModel.profileImageUrl.value = url

                navController.navigate("profile")

            }
        }
    }

    val cameraPermission = Manifest.permission.CAMERA

    val launcherPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {

            launcherCamera.launch()
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }



            Row(modifier = Modifier
                .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center)
            {
                Column(modifier = Modifier.padding(start = 60.dp))
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Camera",
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                               launcherPermission.launch(cameraPermission)


                            }
                    )
                    Text(text = "Camera")
                }

                Spacer(modifier = Modifier.padding(30.dp))

                Column(modifier = Modifier.padding(start = 60.dp))
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                launchImage.launch("image/*")


                            }
                    )
                    Text(text = "Gallery")
                }

            }



    }

