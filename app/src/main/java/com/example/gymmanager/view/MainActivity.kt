package com.example.gymmanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.createDataStore
import com.example.gymmanager.R


import com.jakewharton.threetenabp.AndroidThreeTen


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidThreeTen.init(this)
    }
}