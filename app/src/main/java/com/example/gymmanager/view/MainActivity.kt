package com.example.gymmanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymmanager.R

import com.example.gymmanager.util.appContext
import com.jakewharton.threetenabp.AndroidThreeTen


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        appContext = applicationContext
        setContentView(R.layout.activity_main)
    }
}