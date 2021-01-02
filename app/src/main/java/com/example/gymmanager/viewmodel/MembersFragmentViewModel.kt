package com.example.gymmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.gymmanager.repository.getConciseMembersReference

class MembersFragmentViewModel : ViewModel(){

    val baseQuery = getConciseMembersReference().orderBy("name")
    val pagedListConfig: PagedList.Config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(20)
        .setPageSize(20)
        .setPrefetchDistance(5)
        .build()





}