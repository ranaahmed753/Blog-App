package com.codecamp.blogapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.codecamp.blogapp.R
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : AppCompatActivity() {
    private lateinit var mConstraintLayout : ConstraintLayout;
    private lateinit var mBackButton : ImageView;
    private lateinit var mUserName : TextView;
    private lateinit var mUserEmail : TextView;
    private lateinit var mTotalChildCount : TextView
    private lateinit var mUserBio : TextView;
    private lateinit var mUserNameWithSmallLetter : TextView;
    private lateinit var mUserImage : CircleImageView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val name = intent.getStringExtra("name")
        val image = intent.getStringExtra("image")
        val email = intent.getStringExtra("email");
        val bio = intent.getStringExtra("bio");
        val totalchild = intent.getStringExtra("totalchild");
        val userId = intent.getStringExtra("ID");

        mConstraintLayout = findViewById(R.id.constraintLayout);
        mBackButton = findViewById(R.id.backButton);
        mUserName = findViewById(R.id.userName);
        mUserImage = findViewById(R.id.postUserImage);
        mUserNameWithSmallLetter = findViewById(R.id.username);
        mUserEmail = findViewById(R.id.useremail);
        mUserBio = findViewById(R.id.userbio);
        mTotalChildCount = findViewById(R.id.userpost)

        mUserName.text = name
        mUserNameWithSmallLetter.text = "@/"+name?.toLowerCase()
        mUserEmail.text = email
        mUserBio.text = bio
        mTotalChildCount.text = userId
        Glide.with(this).load(image).into(mUserImage);
        mBackButton.setOnClickListener {
            mBackButton.startAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
            startActivity(Intent(this, MainActivity::class.java));
            finish();

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        mConstraintLayout.startAnimation(AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right));
        startActivity(Intent(this, MainActivity::class.java));
        finish();
    }

    override fun onStart() {
        super.onStart()
        mConstraintLayout.startAnimation(AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left));
    }
}