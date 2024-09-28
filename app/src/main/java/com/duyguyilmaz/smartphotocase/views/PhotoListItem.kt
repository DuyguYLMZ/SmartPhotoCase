package com.duyguyilmaz.smartphotocase.views

import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.duyguyilmaz.smartphotocase.model.Photo

@Composable
fun PhotoListItem(
    photoItem: Photo,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(1f)
            .clickable {
                navController.navigate("PhotoDetail/${photoItem.id}")
                {
                    popUpTo("main") {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val context = LocalContext.current
            val bitmap = MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                Uri.parse(photoItem.uriString)
            )
            Row (modifier = Modifier
                .fillMaxWidth(0.5f) .height(100.dp)){
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = photoItem.name, style = typography.bodyMedium)
                Text(text = photoItem.date, style = typography.bodySmall)
            }
        }
    }
}
