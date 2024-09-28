package com.duyguyilmaz.smartphotocase.helper

import android.content.Context
import android.content.SharedPreferences
import com.duyguyilmaz.smartphotocase.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val PREFS_NAME = "PhotoAppPreferences"
private const val PHOTOS_KEY = "photos"

fun savePhotos(context: Context, photoItems: List<Photo>) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val json = Gson().toJson(photoItems)
    editor.putString(PHOTOS_KEY, json)
    editor.apply()
}

fun getPhotos(context: Context): List<Photo> {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val json = sharedPreferences.getString(PHOTOS_KEY, null)
    val type = object : TypeToken<List<Photo>>() {}.type
    return Gson().fromJson(json, type) ?: emptyList()
}
