package com.example.gymmanager.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.renderscript.ScriptGroup
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.graphics.scale
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage

import org.threeten.bp.LocalDateTime
import id.zelory.compressor.Compressor
import java.io.*

fun createUriForImageFile(context: Context,name:String): Uri {

    val picturesDirectory = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"/")
    val imageFile = File(picturesDirectory, name)
    return  FileProvider.getUriForFile(context, "${context.packageName}.android.fileprovider", imageFile)
}

fun resizeImage(targetHeight:Int , targetWidth:Int , imageUri : Uri,context: Context) {
   // val bmOptions = BitmapFactory.Options()
    //bmOptions.inJustDecodeBounds = true

    //return bitmap.scale(targetWidth,targetHeight,true)
   // val originalPhotoHeight = bmOptions.outHeight
   // val originalPhotoWidth = bmOptions.outWidth
   // val scaleFactor = 1.coerceAtLeast((originalPhotoHeight / targetHeight).coerceAtMost(originalPhotoWidth / targetWidth))
   // return bitmap.scale(targetWidth,targetHeight,true)
}

fun compressAndScaleImage(context: Context,imageName: String,targetHeight: Int,targetWidth: Int):Bitmap{

    val outputStream = compressImage(imageName,context)
    val bitmapArray = outputStream.toByteArray()
    val compressedBitmap = BitmapFactory.decodeByteArray(bitmapArray,0,bitmapArray.size)
    return Bitmap.createScaledBitmap(compressedBitmap,targetWidth,targetHeight,true)

}

fun compressImage(imageName:String ,context: Context):ByteArrayOutputStream{

    val imageFile = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath + "/" + imageName
    val bitmap = BitmapFactory.decodeFile(imageFile)
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG,10,outputStream)
    return outputStream
}


private fun writeCompressedBitmap(outputStream: ByteArrayOutputStream,imageFile:File){
    val fileOutputStream = FileOutputStream(imageFile)
    fileOutputStream.write(outputStream.toByteArray())
    fileOutputStream.flush()
    fileOutputStream.close()
    outputStream.flush()
    outputStream.close()
}
