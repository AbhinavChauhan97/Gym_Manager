package com.example.gymmanager.repository

import android.content.Context
import android.os.Environment
import com.example.gymmanager.model.DetailedMember

import com.example.gymmanager.util.compressAndResizeImage

import com.example.gymmanager.util.uploadImage
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

suspend fun saveImage(imageName:String):Boolean{
    val byteArray = compressAndResizeImage(imageName)
    val isImageSaved = uploadImage(imageName,byteArray)
    GlobalScope.launch {  cacheImage(imageName,byteArray) }
    return isImageSaved
}



