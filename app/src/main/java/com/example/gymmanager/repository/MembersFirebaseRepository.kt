package com.example.gymmanager.repository

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import com.example.gymmanager.model.DetailedMember
import com.example.gymmanager.util.compressImage
import com.example.gymmanager.view.memberviewholderview.Member
import com.google.android.play.core.tasks.OnCompleteListener
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import kotlin.random.Random

fun getMembers(){


}

fun getMember(id:Int){

        val name = FirebaseFirestore.getInstance()
        .collection("users")
        .document(id.toString())
        .get()
}

fun addMemberToFirestoreDatabase(member: DetailedMember,imageUri: Uri? = null){
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(member.id)
                .set(member).addOnFailureListener { Log.d("log",it.toString()) }
                .addOnSuccessListener {
                        if(imageUri != null) {
                                Log.d("log","uploading file")
                                FirebaseStorage.getInstance().getReference(member.id).putFile(imageUri).
                                        addOnCompleteListener {

                                                if(it.isCanceled ){
                                                        Log.d("log",it.exception.toString())
                                                }
                                                if(it.isSuccessful){
                                                        Log.d("log","successful")
                                                }
                                        }
                        }

                }
}