package com.codecamp.blogapp.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.codecamp.blogapp.view.HomeFragment
import com.google.android.material.snackbar.Snackbar
import android.R
import androidx.fragment.app.FragmentManager


fun snackbar(widget: ConstraintLayout,message : String){
    Snackbar.make(widget,message,Snackbar.LENGTH_LONG).show()
}
fun show(widget : View){
    widget.visibility = View.VISIBLE
}

fun hide(widget: View){
    widget.visibility = View.INVISIBLE
}

fun navigate(currentContext : Context,targetContext : Context){
    val intent = Intent(currentContext,targetContext::class.java)
    currentContext.startActivity(intent)
}


fun fadeInAnimation(context: Context,widget: View){
    widget.startAnimation(AnimationUtils.loadAnimation(context,android.R.anim.fade_in))
}