package com.example.gymmanager.view.enabled_disabled_behaviour_accepter_floating_action_button

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.gymmanager.R
import com.example.gymmanager.view.ViewEnabledDisabledBehaviour
import com.google.android.material.floatingactionbutton.FloatingActionButton

 class AddingFABEnabledDisabledBehaviour(private val floatingActionButton: FloatingActionButton): ViewEnabledDisabledBehaviour {

    override fun onEnabled(){
        floatingActionButton.apply {
            backgroundTintList = ColorStateList.valueOf(Color.GREEN)
            isClickable = true
            setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_check_24
                )
            )
        }
    }
    override fun onDisabled(){
        floatingActionButton.apply {
            backgroundTintList = ColorStateList.valueOf(Color.RED)
            isClickable = false
            setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_clear_24
                )
            )
        }
    }
}
