package com.codecamp.blogapp.viewmodel

import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codecamp.blogapp.model.FirebaseRepository
import com.codecamp.blogapp.util.hide
import com.codecamp.blogapp.util.show
import com.codecamp.blogapp.util.snackbar

class FirebaseViewModel : ViewModel() {
    var firebasewRepository : FirebaseRepository? = null
    var signupMutableLiveData : MutableLiveData<Boolean>? = null
    var loginMutableLiveData : MutableLiveData<Boolean>? = null
    init {
        firebasewRepository = FirebaseRepository()
        signupMutableLiveData = firebasewRepository?.getSignupLivedata()
        loginMutableLiveData = firebasewRepository?.getLoginLiveData()
    }

    //signup block start
    fun signup(name : String, email : String, password : String, loadingBar : ProgressBar, buttonText : TextView,widget : ConstraintLayout){
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            snackbar(widget,"some fields are empty!!")
        }else{
            firebasewRepository?.signup(name,email,password,loadingBar,buttonText)
        }
    }
    fun getSignupLiveData() : MutableLiveData<Boolean>{
        return signupMutableLiveData!!
    }
    //signup block end

    //login block start
    fun login(email : String,password : String,loadingBar: ProgressBar,loginText : TextView,widget: ConstraintLayout){
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            hide(loadingBar)
            show(loginText)
            snackbar(widget,"some fields are empty!!")
        }else{
            firebasewRepository?.login(email,password,loadingBar,loginText)
        }
    }

    fun getLoginLiveData() : MutableLiveData<Boolean>{
        return loginMutableLiveData!!
    }
    //login block end
}