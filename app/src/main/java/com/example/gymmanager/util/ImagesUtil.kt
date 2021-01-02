package com.example.gymmanager.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.example.gymmanager.repository.addImageToFirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.io.*

lateinit var imageArray: ByteArray
lateinit var appContext: Context

fun imagesDirectory(): File = appContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!

fun createUriForImageFile(name: String): Uri {
    val picturesDirectory = imagesDirectory()
    val imageFile = File(picturesDirectory, name)
    return FileProvider.getUriForFile(
        appContext,
        "${appContext.packageName}.android.fileprovider",
        imageFile
    )
}


suspend fun compressAndResizeImage(imageFileName: String): ByteArray {
    val imagesDirectory = imagesDirectory()
    val imageFile = File(imagesDirectory, imageFileName)
    val resizedBitmap = resizeBitmapFromFile(imageFile)
    val stream = compressBitmap(resizedBitmap)
    return stream.toByteArray()

}


suspend fun compressBitmap(bitmap: Bitmap): ByteArrayOutputStream {

    return withContext(Dispatchers.Default) {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        outputStream
    }
}

suspend fun resizeBitmapFromFile(file: File): Bitmap {

    return withContext(Dispatchers.IO) {
        val options = BitmapFactory.Options()
        options.inSampleSize = 8
        options.inPreferredConfig = Bitmap.Config.RGB_565
        BitmapFactory.decodeFile(file.absolutePath, options)
    }
}


suspend fun uploadImage(imageName: String,byteArray : ByteArray): Boolean {
    //val byteArray = compressAndResizeImage(imageName)
    return addImageToFirebaseStorage(imageName, byteArray).task.isComplete
}

suspend fun deleteImage(imageName: String) {
    return withContext(Dispatchers.IO) {
        val imageFilePath =
            appContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath + "/" + imageName
        File(imageFilePath).delete()
    }
}
