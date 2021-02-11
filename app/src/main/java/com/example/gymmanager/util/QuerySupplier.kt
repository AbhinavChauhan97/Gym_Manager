package com.example.gymmanager.util

import com.example.gymmanager.MyApplication
import com.example.gymmanager.repository.getALlMembersFeeReference
import com.example.gymmanager.repository.getConciseMembersReference
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * @author Abhinav Chouhan
 * @since 31-jan-2021
 * delivers firebase queries to its subscribers subscriber has to tell what queries they want
 */
object QuerySupplier {

    interface QuerySupporter {
        fun newQuery(query: Query)
        fun queryOfInterest(): QueryType
    }

    enum class QueryType {
        CONCISE_MEMBERS, FEES
    }

    private val querySupporters = mutableListOf<QuerySupporter>()

    fun listenForQueries(querySupporter: QuerySupporter) {
        querySupporters.add(querySupporter)
        GlobalScope.launch(Dispatchers.Main) {

        }

    }

    fun newSearchQuery(searchQuery: String, queryType: QueryType) {
        querySupporters.forEach {
            if (queryType == it.queryOfInterest()) {
                val collectionReference:CollectionReference = when (queryType) {

                    QueryType.CONCISE_MEMBERS -> getConciseMembersReference()
                    QueryType.FEES -> getALlMembersFeeReference()
                }
                val fireStoreSearchQuery = if(searchQuery.isBlank()) collectionReference else collectionReference.whereEqualTo("name",searchQuery)
                it.newQuery(fireStoreSearchQuery)
            }
        }
    }

    fun newSortQuery(queryType: QueryType,vararg orderBy: String) {
        querySupporters.forEach {
            if (queryType == it.queryOfInterest()) {
                var fireStoreSortQuery: Query = when (queryType) {

                    QueryType.CONCISE_MEMBERS -> getConciseMembersReference()
                    QueryType.FEES -> getALlMembersFeeReference()
                }
                orderBy.forEach { field ->
                    fireStoreSortQuery = fireStoreSortQuery.orderBy(field)
                }
                it.newQuery(fireStoreSortQuery)
            }
        }
    }
}