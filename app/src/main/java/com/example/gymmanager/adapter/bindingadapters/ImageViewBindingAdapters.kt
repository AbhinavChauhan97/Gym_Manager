package com.example.gymmanager.adapter.bindingadapters

import androidx.databinding.BindingAdapter
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ImageView
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
import java.io.File

object ImageViewBindingAdapters {
    @JvmStatic
    @BindingAdapter("imageUri")
    fun setSrc(imageView: CircularImageView, imageUri: Uri?) {
        if (imageUri != null) {
            imageView.load(imageUri)
        } else {
            imageView.load(R.drawable.ic_baseline_account_circle_24)
        }
    }

    @JvmStatic
    @BindingAdapter("image")
    fun setSrc(imageView: CircularImageView, imageName: String?) {
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
    }
}

