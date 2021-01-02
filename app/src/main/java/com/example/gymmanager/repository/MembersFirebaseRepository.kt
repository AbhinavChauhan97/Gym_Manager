package com.example.gymmanager.repository

import android.net.Uri
import android.util.Log
import com.example.gymmanager.model.*
import com.google.android.gms.tasks.Task
import com.google.android.play.core.tasks.Tasks
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import java.io.File

interface DocumentReferenceProvider {
    val documentReference: DocumentReference
}

suspend fun downloadMember(id: String): DocumentSnapshot {

    val documentSnapShotTask = FirebaseFirestore.getInstance()
        .collection("d_members")
        .document(id)
        .get()
    return documentSnapShotTask.await()
}

suspend fun downloadImage(imageName: String, downloadDestinationFile: File): Boolean {
    val downloadTask = FirebaseStorage.getInstance()
        .getReference(imageName)
        .getFile(downloadDestinationFile)
    downloadTask.await()
    return downloadTask.isSuccessful
}

fun getConciseMembersReference(): CollectionReference {
    return FirebaseFirestore.getInstance()
        .collection("c_members")
}

suspend fun addToFireStore(vararg documentReferenceProvider: DocumentReferenceProvider): Boolean {
    val memberSetterTask = FirebaseFirestore.getInstance().runBatch {
        documentReferenceProvider.forEach {
            it.documentReference.set(it)
        }
    }
    memberSetterTask.await()
    return memberSetterTask.isSuccessful
}

fun addNewMember(member: DocumentReferenceProvider) {
    member.documentReference.set(member)
}

fun addNewMemberDetails(memberDetails: DocumentReferenceProvider) {
    // FirebaseFirestore.getInstance().collection("d_members").document(memberDetails.id).set(memberDetails)
    memberDetails.documentReference.set(memberDetails)

}

suspend fun addImageToFirebaseStorage(
    imageName: String,
    byteArray: ByteArray
): UploadTask.TaskSnapshot {

    return FirebaseStorage.getInstance()
        .getReference(imageName)
        .putBytes(byteArray)
        .await()
}
