package com.example.gymmanager.view.enabled_disabled_behaviour_accepter_floating_action_button

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gymmanager.R
import com.example.gymmanager.view.ViewEnabledDisabledBehaviour
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EnabledDisabledBehaviourFloatingActionButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FloatingActionButton(context, attributeSet, defStyleAttr) {


    var fabEnabledDisabledBehaviour: ViewEnabledDisabledBehaviour? = null
    override fun setEnabled(enabled: Boolean) {
        if(enabled) {
            fabEnabledDisabledBehaviour?.onEnabled()
        }
        else{
            fabEnabledDisabledBehaviour?.onDisabled()
        }
    }

}