package com.example.gymmanager

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.flow.first

class MyApplication : Application(){


    companion object{
         lateinit var context: Context
         lateinit var dataStore:DataStore<Preferences>
         lateinit var membersSortingKey:Preferences.Key<Int>
         lateinit var feesSortingKey:Preferences.Key<Int>
         val SORT_BY_NAME = 0
         val SORT_BY_FEES = 1
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        membersSortingKey = intPreferencesKey("members_sorting")
        feesSortingKey = intPreferencesKey("feesSorting")
        dataStore = createDataStore("settings")
    }


}