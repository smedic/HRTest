package com.smedic.hr.tools

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import java.text.DecimalFormat

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */

fun Float.formattedRating(): String {
    val df = DecimalFormat("0.0")
    return "${df.format(this)}/10"
}

fun ImageView.setTint(id: Int) {
    this.setColorFilter(
        ContextCompat.getColor(context, id),
        android.graphics.PorterDuff.Mode.MULTIPLY
    );
}

fun View.visible(animate: Boolean = true, duration: Long = 500) {
    if (animate) {
        animate().alpha(1f).setDuration(duration).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                visibility = View.VISIBLE
            }
        })
    } else {
        visibility = View.VISIBLE
    }
}

fun View.gone(animate: Boolean = true, duration: Long = 500) {
    if (animate) {
        animate().alpha(0f).setDuration(duration).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = View.GONE
            }
        })
    } else {
        visibility = View.GONE
    }
}