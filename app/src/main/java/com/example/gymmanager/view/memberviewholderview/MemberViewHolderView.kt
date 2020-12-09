package com.example.gymmanager.view.memberviewholderview

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.gymmanager.R
import com.example.gymmanager.databinding.MemberviewholderviewBinding

import org.threeten.bp.LocalDate


class MemberViewHolderView(context: Context,attributeSet: AttributeSet? = null) : ConstraintLayout(context,attributeSet){

    private val binding: MemberviewholderviewBinding
    var member:Member?= null
    set(value) {
        field = value
        binding.member = field
        binding.executePendingBindings()
    }
    init {

        //inflate(context,R.layout.memberviewholderview,parent)
        setBackgroundColor(Color.CYAN)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
       // inflater.inflate(R.layout.memberviewholderview,this,true)
        binding = MemberviewholderviewBinding.inflate(inflater,this,true)
        binding.lifecycleOwner = context as LifecycleOwner
    }





}

