package com.duyguyilmaz.smartphotocase.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter

@Composable
fun PhotoDetailPage(modifier: Modifier = Modifier,
                    photoUri: String,
                    photoName: String,
                    photoDate: String,
) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberImagePainter(photoUri),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

