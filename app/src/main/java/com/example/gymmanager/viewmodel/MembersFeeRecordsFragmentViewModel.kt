package com.example.gymmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.gymmanager.repository.getALlMembersFeeReference

class MembersFeeRecordsFragmentViewModel : ViewModel() {

    val baseQuery = getALlMembersFeeReference().orderBy("year")
    val pagedListConfig: PagedList.Config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(20)
        .setPageSize(20)
        .setPrefetchDistance(5)
        .build()
}