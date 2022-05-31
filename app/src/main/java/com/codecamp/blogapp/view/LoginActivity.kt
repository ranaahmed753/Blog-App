package com.codecamp.blogapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codecamp.blogapp.R
import com.codecamp.blogapp.util.fadeInAnimation
import com.codecamp.blogapp.util.navigate
import com.codecamp.blogapp.viewmodel.FirebaseViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mConstraintLayout : ConstraintLayout;
    private lateinit var mEmailEditText : TextInputEditText;
    private lateinit var mPasswordEditText : TextInputEditText;
    private lateinit var mLoginButton : RelativeLayout;
    private lateinit var mLoginText : TextView;
    private lateinit var mProgressBar : ProgressBar;
    private lateinit var firebaseViewModel : FirebaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        mConstraintLayout = findViewById(R.id.constraintLayout);
        mEmailEditText = findViewById(R.id.emailEditText);
        mPasswordEditText = findViewById(R.id.passwordEditText);
        mLoginButton = findViewById(R.id.loginButtton);
        mLoginText = findViewById(R.id.loginText);
        mProgressBar = findViewById(R.id.progressbar);

        mLoginButton.setOnClickListener {
            fadeInAnimation(this,mLoginButton)
            firebaseViewModel.login(mEmailEditText.text.toString(),mPasswordEditText.text.toString(),mProgressBar,mLoginText,mConstraintLayout)
            firebaseViewModel.getLoginLiveData().observe(this, Observer { isUserLoggedin ->
                if(isUserLoggedin){
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout,HomeFragment())
                        .commit()
                }
            })
        }
    }


    override fun onStart() {
        super.onStart()
        mConstraintLayout.startAnimation(AnimationUtils.loadAnimation(applicationContext,android.R.anim.slide_in_left))
    }
}