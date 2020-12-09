package com.example.gymmanager.adapter.bindingadapters;

import android.net.Uri;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.gymmanager.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class ImageViewBindingAdapters {

    @BindingAdapter("android:imageUri")
    public static void setSrc(CircularImageView imageView, Uri imageUri){
         if(imageUri != null) {
             imageView.setImageURI(imageUri);
         }
    }
}
