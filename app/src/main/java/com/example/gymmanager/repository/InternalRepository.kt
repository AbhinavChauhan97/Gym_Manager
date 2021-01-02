package com.example.gymmanager.repository

import com.example.gymmanager.util.imagesDirectory
import java.io.File

fun cacheImage(imageName:String,byteArray: ByteArray){
    val imagesDirectory = imagesDirectory()
    val imageFile = File(imagesDirectory,imageName)
    imageFile.writeBytes(byteArray)
}

fun getCachedImage(imageName: String): File {
    val imagesDirectory = imagesDirectory()
    return File(imagesDirectory,imageName)
}