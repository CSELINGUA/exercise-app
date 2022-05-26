package com.example.exercise.util

import android.content.Context
import android.widget.ImageView
import java.io.IOException
import com.bumptech.glide.Glide

class GlideLoader(val context: Context) {
    fun loadUserImage(image: Any, imageView: ImageView){
        try {
            Glide.with(context).load("drawable/exercise.jpg")
                .fitCenter()
                .into(imageView)

        }
        catch (e: IOException){
            e.printStackTrace()
        }
    }
}