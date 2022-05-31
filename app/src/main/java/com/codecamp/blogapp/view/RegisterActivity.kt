package com.codecamp.blogapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
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
import com.codecamp.blogapp.util.snackbar
import com.codecamp.blogapp.viewmodel.FirebaseViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.collections.HashMap

class RegisterActivity : AppCompatActivity() {
    private lateinit var mConstraintLayout : ConstraintLayout;
    private lateinit var mNameEditText : TextInputEditText;
    private lateinit var mEmailEditText : TextInputEditText;
    private lateinit var mPasswordEditText : TextInputEditText;
    private lateinit var mRegisterButton : RelativeLayout;
    private lateinit var mProgressBar : ProgressBar;
    private lateinit var mRegisterButtonText : TextView;
    private lateinit var firebaseViewModel : FirebaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)

        mConstraintLayout = findViewById(R.id.constraintLayout);
        mNameEditText = findViewById(R.id.nameEditText);
        mEmailEditText = findViewById(R.id.emailEditText);
        mPasswordEditText = findViewById(R.id.passwordEditText);
        mRegisterButton = findViewById(R.id.RegisterButtton);
        mProgressBar = findViewById(R.id.progressbar);
        mRegisterButtonText = findViewById(R.id.registerButtonText);

        mRegisterButton.setOnClickListener {
            fadeInAnimation(this,mRegisterButton)
            firebaseViewModel.signup(mNameEditText.text.toString(),mEmailEditText.text.toString(),mPasswordEditText.text.toString(),mProgressBar,mRegisterButtonText,mConstraintLayout)
            firebaseViewModel.getSignupLiveData().observe(this, Observer { isUserCreated ->
                if(!isUserCreated || isUserCreated.equals(null)){
                    snackbar(mConstraintLayout,"something wrong!!")
                }else{
                    navigate(this,LoginActivity())
                }
            })


        }





    }

    override fun onStart() {
        super.onStart()
        mConstraintLayout.startAnimation(AnimationUtils.loadAnimation(applicationContext,android.R.anim.slide_in_left))
    }
}