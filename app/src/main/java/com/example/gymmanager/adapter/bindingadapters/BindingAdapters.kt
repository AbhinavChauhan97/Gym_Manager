package com.example.gymmanager.adapter.bindingadapters

import androidx.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.Coil
import coil.load
import coil.util.CoilUtils
import com.bumptech.glide.Glide
import com.example.gymmanager.R

import com.example.gymmanager.repository.downloadImage
import com.example.gymmanager.repository.getCachedImage
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import java.io.File

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUri")
    fun setSrc(imageView : ImageView, imageUri: Uri?) {
        if (imageUri != null) {
            imageView.load(imageUri)
        } else {
           loadPlaceHolder(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("image")
    fun setSrc(imageView: ImageView, imageName: String?) {
        if (imageName != null) {
            GlobalScope.launch {
                val imageFile = getCachedImage(imageName)
                if (!imageFile.exists()) {
                    downloadImage(imageName,imageFile)
                }
                imageView.load(imageFile) {
                    placeholder(R.drawable.ic_baseline_account_circle_24)
                }
            }
        }
        else{
            loadPlaceHolder(imageView)
        }
    }

    private fun loadPlaceHolder(imageView: ImageView){
        imageView.load(R.drawable.ic_baseline_account_circle_24)
    }
}



