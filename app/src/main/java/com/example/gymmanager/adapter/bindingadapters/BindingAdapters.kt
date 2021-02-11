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
import com.example.gymmanager.R

import com.example.gymmanager.repository.downloadImage
import com.example.gymmanager.repository.getCachedImage
import com.example.gymmanager.repository.getImageUrl
import com.example.gymmanager.util.imagesDirectory
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import java.io.File

object BindingAdapters {


    /**
     *binding adapter to download and load an image from given uri
     * only if the uri is not null will load a placeholder otherwise
     * @param imageView imageVie to load the image into
     * @param imageUri the uri of the image
     */
    @JvmStatic
    @BindingAdapter("imageUri")
    fun setSrc(imageView : ImageView, imageUri: Uri?) {
        if (imageUri != null) {
            imageView.load(imageUri)
        } else {
           loadPlaceHolder(imageView)
        }
    }

    /**
     * loads the image from its name if the image is in the cache loads it otherwise download is and loads it from firebasestore
     * also caches if after downloading
     * if image is not found in the cache and firestore both a placeholder image is always loads in imageview
     * @param imageView the imageview to load the image
     * @param imageName the name of the image to load
     */

    @JvmStatic
    @BindingAdapter("image")
    fun setSrc(imageView: ImageView, imageName: String?) {
        loadPlaceHolder(imageView)
        if (imageName != null) {
            GlobalScope.launch {
                val imageFile = getCachedImage(imageName)
                if(imageFile.exists()){
                    imageView.load(imageFile)
                }else{
                    val newImageFile = File(imagesDirectory(),imageName)
                    val downloadedImage = downloadImage(imageName,newImageFile)
                    if(downloadedImage){
                        imageView.load(newImageFile)
                    }
                }
            }
        }
    }

    private fun loadPlaceHolder(imageView: ImageView){
        imageView.load(R.drawable.ic_baseline_account_circle_24)
    }
}



