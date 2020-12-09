package com.example.gymmanager.view.feeviewholderview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gymmanager.R
import com.example.gymmanager.databinding.FeerecordviewholderviewBinding

class FeeRecordViewHolderView(context: Context,attributeSet: AttributeSet? = null) : ConstraintLayout(context,attributeSet){

    private val binding:FeerecordviewholderviewBinding
    init {
       val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
       binding =  FeerecordviewholderviewBinding.inflate(inflater,this,true)
    }
}