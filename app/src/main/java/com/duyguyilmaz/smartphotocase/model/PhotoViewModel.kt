package com.duyguyilmaz.smartphotocase.model

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duyguyilmaz.smartphotocase.helper.getPhotos
import com.duyguyilmaz.smartphotocase.helper.savePhotos


import kotlinx.coroutines.launch

class PhotoViewModel : ViewModel() {
    var photoItems = mutableStateListOf<Photo>()

    fun loadPhotos(context: Context) {
        photoItems.clear()
        photoItems.addAll(getPhotos(context))
    }

    fun savePhotos(context: Context) {
        viewModelScope.launch {
            savePhotos(context, photoItems)
        }
    }
}