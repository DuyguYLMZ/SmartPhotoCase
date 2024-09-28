package com.duyguyilmaz.smartphotocase.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.duyguyilmaz.smartphotocase.model.Photo
import com.duyguyilmaz.smartphotocase.model.PhotoViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects


@SuppressLint("SimpleDateFormat")
@Composable
fun CameraOpen(viewModel: PhotoViewModel, context: Context) {
    val file = context.createImageFile()
    val photoUri = remember {
        FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            context.packageName + ".provider", file
        )
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                val rotation = getImageRotation(file.absolutePath)
                val  bitmap = BitmapFactory.decodeFile(file.absolutePath)?.let {
                    rotateBitmap(it, rotation)
                }
                val savedUri = bitmap?.let {
                    saveImageToExternalStorage(
                        context,
                        it,
                        "photo_${System.currentTimeMillis()}"
                    )
                }

                val id = viewModel.photoItems.maxByOrNull { it.id }?.id?.plus(1) ?: 1
                if (savedUri != null) {
                    val photoName = "image$id"
                    val photoDate =
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                    viewModel.photoItems.add(Photo(id, photoDate, photoName, savedUri.toString()))
                    viewModel.savePhotos(context)
                }
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(photoUri)

        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    ExtendedFloatingActionButton(
        text = { Text(text = "Camera") },
        onClick = {
            val permissionCheckResult =
                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                cameraLauncher.launch(photoUri)
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        },
        icon = { Icon(Icons.Filled.Add, "") }
    )


}

private fun saveImageToExternalStorage(context: Context, bitmap: Bitmap, filename: String): Uri? {
    val directory = File(context.getExternalFilesDir(null), "Photos")
    if (!directory.exists()) {
        directory.mkdirs()
    }

    val file = File(directory, "$filename.jpg")

    return try {
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        Uri.fromFile(file)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }

}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image
}


private fun getImageRotation(filePath: String): Float {
    val exif = ExifInterface(filePath)
    return when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90f
        ExifInterface.ORIENTATION_ROTATE_180 -> 180f
        ExifInterface.ORIENTATION_ROTATE_270 -> 270f
        else -> 0f
    }
}


private fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}