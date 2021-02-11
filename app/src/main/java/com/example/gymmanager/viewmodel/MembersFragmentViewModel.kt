package com.example.gymmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.gymmanager.repository.getConciseMembersReference
import com.google.firebase.firestore.DocumentSnapshot

class MembersFragmentViewModel : ViewModel(){

    var currentList:PagedList<DocumentSnapshot>? = null
    var baseQuery = getConciseMembersReference().orderBy("name")
    val pagedListConfig: PagedList.Config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(20)
        .setPageSize(20)
        .setPrefetchDistance(5)
        .build()
}