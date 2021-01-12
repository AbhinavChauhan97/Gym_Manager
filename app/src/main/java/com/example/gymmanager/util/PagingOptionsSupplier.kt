package com.example.gymmanager.util

import android.app.DownloadManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PagingOptionsSupplier<T> constructor(
    private val lifecycleOwner: LifecycleOwner,
    private val modelClass: Class<T>
) {
    var query: Query? = null
    var initialLoadSize = 10
    var prefetchDistance = 5
    var placeHolders = false
    var pageSize = 10
    private var options: FirestorePagingOptions<T>? = null


    fun getPagingOptions(): FirestorePagingOptions<T> {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(placeHolders)
            .setPageSize(pageSize)
            .setPrefetchDistance(prefetchDistance)
            .setInitialLoadSizeHint(initialLoadSize)
            .build()

        options = query?.let {
            FirestorePagingOptions.Builder<T>()
                .setQuery(it, pagedListConfig, modelClass)
                .setLifecycleOwner(lifecycleOwner)
                .build()
        }

        if (options == null) {
            throw IllegalStateException("query is not supplied, query field of this object is null can't supply options without query")
        }
        return options!!
    }
}