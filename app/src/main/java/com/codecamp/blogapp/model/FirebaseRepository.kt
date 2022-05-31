package com.codecamp.blogapp.model

import android.app.Application
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.codecamp.blogapp.util.hide
import com.codecamp.blogapp.util.navigate
import com.codecamp.blogapp.util.show
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseRepository : Application() {
    var userAuth : FirebaseAuth? = null
    var userRef : DatabaseReference? = null
    var signupMutableLivedata : MutableLiveData<Boolean>? = null
    var loginMutableLivedata : MutableLiveData<Boolean>? = null
    var userMap : HashMap<String,String>? = null
    init {
        userAuth = FirebaseAuth.getInstance()
        userRef = FirebaseDatabase.getInstance().getReference().child("Users")
        signupMutableLivedata = MutableLiveData()
        loginMutableLivedata = MutableLiveData()
        userMap = HashMap()
    }
    //signup block start
    fun signup(name : String,email : String,password : String,loadingBar : ProgressBar,buttonText : TextView){
        userAuth?.createUserWithEmailAndPassword(email,password)?.addOnCompleteListener { task ->
            if(task.isSuccessful){
                userMap?.put("name",name);
                userMap?.put("email",email);
                userMap?.put("password",password);
                userMap?.put("photo","default");
                userMap?.put("bio","default");
                userMap?.put("post","default");
                userMap?.put("isLogin","notLoggedIn")
                userRef
                ?.child(userAuth
                ?.currentUser?.uid!!)
                ?.setValue(userMap)
                ?.addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        signupMutableLivedata?.postValue(true)
                        loginMutableLivedata?.postValue(false)
                        hide(loadingBar)
                        show(buttonText)
                    }else{
                        signupMutableLivedata?.postValue(false)
                        loginMutableLivedata?.postValue(false)
                    }
                }
            }
        }

    }

    fun getSignupLivedata() : MutableLiveData<Boolean>{
        return signupMutableLivedata!!
    }
    //signup block end

    //login block start
    fun login(email : String,password : String,loadingBar: ProgressBar,loginText : TextView){
        userAuth?.signInWithEmailAndPassword(email,password)?.addOnCompleteListener { task ->
            if(task.isSuccessful){
                loginMutableLivedata?.value = true
                userRef
                ?.child(userAuth?.currentUser!!.uid)
                ?.child("isLogin")
                ?.setValue("loggedIn")
                hide(loadingBar)
                show(loginText)
            }else{
                loginMutableLivedata?.value = true
            }
        }
    }

    fun getLoginLiveData() : MutableLiveData<Boolean>{
        return loginMutableLivedata!!
    }
    //login block end

}