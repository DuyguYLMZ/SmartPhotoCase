package com.duyguyilmaz.smartphotocase.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.duyguyilmaz.smartphotocase.model.PhotoViewModel
import com.duyguyilmaz.smartphotocase.views.CameraOpen
import com.duyguyilmaz.smartphotocase.views.RecyclerView
import com.duyguyilmaz.smartphotocase.views.SearchView
import com.duyguyilmaz.smartphotocase.views.SortList

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(photoList: PhotoViewModel, navController: NavController, applicationContext: Context) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()) {
                    SearchView(state = textState)
                }
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()) {
                    SortList(photoList)
                }
            }
        },
        floatingActionButton = {
           CameraOpen(photoList,applicationContext)
        }
    )
    { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RecyclerView(photoList, navController, state = textState)
        }
    }

}

