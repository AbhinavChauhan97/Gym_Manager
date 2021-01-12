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
import org.threeten.bp.LocalDate
import java.io.File

interface DocumentReferenceProvider {
    val documentReference: DocumentReference
}
fun getConciseMembersReference() = FirebaseFirestore.getInstance().collection("c_members")

fun getConciseMemberDoc(memberId:String) = getConciseMembersReference().document(memberId)

fun getDetailedMembersReference() = FirebaseFirestore.getInstance().collection("d_members")

fun getDetailedMemberDoc(memberId: String) = getDetailedMembersReference().document(memberId)

fun getALlMembersFeeReference() = FirebaseFirestore.getInstance().collection("fee")

fun getMemberImageRef(memberId: String) = FirebaseStorage.getInstance().getReference("$memberId.jpg")




suspend fun getConciseMember(memberId:String):ConciseMember{
    val conciseMemberDoc = getConciseMemberDoc(memberId)
    val conciseMemberSnapshotTask = conciseMemberDoc.get()
    conciseMemberSnapshotTask.await()
    return conciseMemberSnapshotTask.result!!.toObject(ConciseMember::class.java)!!
}
suspend fun submitFee(feeRecord: FeeRecord, months: String):Boolean {
    val conciseMember = getConciseMember(feeRecord.id)
    val memberFee = MemberFee(amount = feeRecord.amount,
        day = feeRecord.day,
        month = feeRecord.month,
        year = feeRecord.year)
    var memberFeeDate = LocalDate.of(conciseMember.year,conciseMember.month,conciseMember.day)
    memberFeeDate = memberFeeDate.plusMonths(months.toLong())
    val conciseMemberDoc = getConciseMemberDoc(feeRecord.id)
    val feeRecordDoc = FirebaseFirestore.getInstance().collection("fee").document()
    val memberFeeDoc = FirebaseFirestore.getInstance().collection("d_members")
        .document(feeRecord.id).collection("fee").document()
    val submitFeeTask = FirebaseFirestore.getInstance().runBatch {
        feeRecordDoc.set(feeRecord)
        memberFeeDoc.set(memberFee)
        conciseMemberDoc.update("day",memberFeeDate.dayOfMonth)
        conciseMemberDoc.update("month",memberFeeDate.monthValue)
        conciseMemberDoc.update("year",memberFeeDate.year)
    }
    submitFeeTask.await()
    return submitFeeTask.isSuccessful
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

suspend fun getMemberInternalFeeDocs(memberId: String): MutableList<DocumentSnapshot>?{
    val internalCollection = getDetailedMemberDoc(memberId).collection("fee")
    val task = internalCollection.get()
    task.await()
    if(task.isSuccessful) {
        Log.d("log","sucessful")
        task.result?.forEach { Log.d("log",it.id) }
       return task.result?.documents
    }
    return null
}

suspend fun getMemberInternalFeeDocRefs(memberId: String): MutableList<DocumentReference>?{
    val internalFeeDocs = getMemberInternalFeeDocs(memberId)
    if(internalFeeDocs != null){
        val internalFeeDocRefs = mutableListOf<DocumentReference>()
        internalFeeDocs.forEach { internalFeeDocRefs.add(it.reference) }
        return internalFeeDocRefs
    }
    return null
}

suspend fun deleteMember(memberId: String):Boolean{
    val conciseDocRef = getConciseMemberDoc(memberId)
    val detailedMemberDocRef = getDetailedMemberDoc(memberId)
    val internalFeeDocRefs = getMemberInternalFeeDocRefs(memberId)
    val feeDocsRefs = getMemberFeeDocsRefs(memberId)
    val memberImageRef = getMemberImageRef(memberId)
    val task = FirebaseFirestore.getInstance()
        .runBatch {
            Log.d("log","in the batch")
            conciseDocRef.delete()
            detailedMemberDocRef.delete()
            memberImageRef.delete()
            feeDocsRefs?.forEach { it.delete() }
            internalFeeDocRefs?.forEach { it.delete()  }
        }
    task.await()
    Log.d("log",task.isSuccessful.toString())
    return task.isSuccessful
}


suspend fun getMemberFeeDocsRefs(memberId: String): List<DocumentReference>? {
    val documentsList = getMemberFeeDocuments(memberId)
    if(documentsList != null) {
        val documentsRefs = mutableListOf<DocumentReference>()
        documentsList.forEach {
           documentsRefs.add(it.reference)
        }
        return documentsRefs
    }
    return null
}

suspend fun getMemberFeeDocuments(memberId: String): MutableList<DocumentSnapshot>? {
    val query = getALlMembersFeeReference().whereEqualTo("id",memberId)
    val documentsTask = query.get()
    val documentsSnapShot = documentsTask.await()
    if(documentsTask.isSuccessful){
        return documentsSnapShot.documents
    }
    return null
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
