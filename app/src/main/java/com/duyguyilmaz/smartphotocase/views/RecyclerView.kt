package com.duyguyilmaz.smartphotocase.views


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.duyguyilmaz.smartphotocase.model.Photo
import com.duyguyilmaz.smartphotocase.model.PhotoViewModel
import java.util.Locale

@SuppressLint("SuspiciousIndentation", "Range", "UnrememberedMutableState")
@Composable
fun RecyclerView(
    viewModel: PhotoViewModel,
    navController: NavController,
    state: MutableState<TextFieldValue>
) {
    var filteredPhotos: List<Photo>
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            val searchedText = state.value.text
            filteredPhotos = if (searchedText.isEmpty()) {
                viewModel.photoItems
            } else {
                val resultList = ArrayList<Photo>()
                for (photo in viewModel.photoItems) {
                    if (photo.name.lowercase(Locale.getDefault())
                            .contains(searchedText.lowercase(Locale.getDefault()))
                    ) {
                        resultList.add(photo)
                    }
                }
                resultList
            }
        items(filteredPhotos) { filteredPhoto ->
            PhotoListItem(filteredPhoto, navController = navController)
        }
    }
}





