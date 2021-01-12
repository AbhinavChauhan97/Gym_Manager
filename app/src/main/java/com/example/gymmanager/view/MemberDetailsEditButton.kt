package com.example.gymmanager.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.gymmanager.R

class MemberDetailsEditButton(context: Context, attributeSet: AttributeSet? = null) :
    androidx.appcompat.widget.AppCompatImageView(context, attributeSet) {

    private var currentDrawable:String? = null
    private val editDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_edit_24)
    private val invalidDrawable =
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24)?.apply {
            this.setTint(Color.RED)
        }
    private val validDrawable =
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_check_24)?.apply {
            this.setTint(Color.GREEN)
        }
    init {
        isSaveEnabled = true
    }
    fun setValidDrawable(){
        setImageDrawable(validDrawable)
        currentDrawable = "valid"
    }
    fun setInvalidDrawable(){
        setImageDrawable(invalidDrawable)
        currentDrawable = "invalid"
    }
    fun setEditDrawable(){
        setImageDrawable(editDrawable)
        currentDrawable = "edit"
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable("superState",super.onSaveInstanceState())
        bundle.putString("currBtn",currentDrawable)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val bundle = state as Bundle
        currentDrawable = bundle.getString("currBtn")
        when(currentDrawable){
            "edit" -> setImageDrawable(editDrawable)
            "valid" -> setImageDrawable(validDrawable)
            "invalid" -> setImageDrawable(invalidDrawable)
        }
        super.onRestoreInstanceState(bundle.getParcelable("superState"))
    }

}