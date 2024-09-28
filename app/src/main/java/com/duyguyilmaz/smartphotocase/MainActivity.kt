package com.duyguyilmaz.smartphotocase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duyguyilmaz.smartphotocase.model.PhotoViewModel
import com.duyguyilmaz.smartphotocase.screens.HomeScreen
import com.duyguyilmaz.smartphotocase.screens.PhotoDetailPage
import com.duyguyilmaz.smartphotocase.ui.theme.smartphotocaseTheme

class MainActivity : ComponentActivity() {
    private val photoViewModel = PhotoViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            smartphotocaseTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    photoViewModel.loadPhotos(applicationContext)
                    NavHost(navController = navController,
                        startDestination = "HomeScreen") {
                        composable(route = "HomeScreen") {
                            HomeScreen(photoViewModel,navController,applicationContext)
                        }
                        composable("PhotoDetail/{id}") { backStackEntry ->
                            backStackEntry.arguments?.getString("id")?.let { photoId ->
                                val selectedPhoto = photoViewModel.photoItems.firstOrNull{
                                    photoId.equals(it.id.toString())
                                }
                                if (selectedPhoto != null){
                                    PhotoDetailPage(photoUri=selectedPhoto.uriString, photoName=selectedPhoto.name ?: "",photoDate = selectedPhoto.date ?: "")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        photoViewModel.savePhotos(applicationContext)
    }
}

