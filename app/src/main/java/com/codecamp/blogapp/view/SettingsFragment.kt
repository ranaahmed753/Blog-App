package com.codecamp.blogapp.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.codecamp.blogapp.R
import com.codecamp.blogapp.R.drawable.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SettingsFragment : Fragment() {
    private lateinit var mConstraintLayout : ConstraintLayout;
    private lateinit var mUpdateProfileButton : ConstraintLayout
    private lateinit var mSettingButton : ConstraintLayout
    private lateinit var mUpdateProfileConstraintLayout : ConstraintLayout;
    private lateinit var mSettingConstraintLayout : ConstraintLayout;
    private lateinit var mUpdateConstraintLayout : ConstraintLayout
    private lateinit var mLogoutConstraintLayout : ConstraintLayout
    private lateinit var mUpdateImageButton : ConstraintLayout;
    private lateinit var mSaveButton : ConstraintLayout;
    private lateinit var mUserRef : DatabaseReference;
    private lateinit var mNameEditText : TextInputEditText
    private lateinit var mBioEditText : TextInputEditText
    private lateinit var mUserAuth : FirebaseAuth;
    private lateinit var name : String
    private lateinit var email : String
    private lateinit var bio : String
    private lateinit var password : String
    private lateinit var mUserName : TextView
    private lateinit var mUsername : TextView
    private lateinit var mUserPassword : TextView
    private lateinit var mUserBio : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUserAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View;
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        mConstraintLayout = view.findViewById(R.id.constraintLayout);
        mUpdateProfileButton = view.findViewById(R.id.updateProfileButton);
        mSettingButton = view.findViewById(R.id.settingButton);
        mUpdateImageButton = view.findViewById(R.id.editImageeButton);
        mSaveButton = view.findViewById(R.id.saveButton);

        mNameEditText = view.findViewById(R.id.nameEditText);
        mBioEditText = view.findViewById(R.id.bioEditText);

        mSettingButton.setBackgroundResource(layout_active)

        mUpdateProfileButton.setBackgroundResource(layout_inactive)


        mUpdateProfileConstraintLayout = view.findViewById(R.id.updateProfileConstraintLayout)
        mSettingConstraintLayout = view.findViewById(R.id.settingConstraintLayout)
        mUpdateConstraintLayout = view.findViewById(R.id.updateConstraintLayout)
        mLogoutConstraintLayout = view.findViewById(R.id.logoutConstraintLayout);

        mUserName = view.findViewById(R.id.userName);
        mUsername = view.findViewById(R.id.username);
        mUserPassword = view.findViewById(R.id.password);
        mUserBio = view.findViewById(R.id.bio)

        mUserAuth.currentUser?.let {
            mUserRef.child(it.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    name = dataSnapshot.child("name").value.toString()
                    email = dataSnapshot.child("email").value.toString()
                    password = dataSnapshot.child("password").value.toString()
                    bio = dataSnapshot.child("bio").value.toString()

                    mUserName.setText(name)
                    mUsername.setText("m.me/"+name.toLowerCase())
                    mUserPassword.setText(password)
                    if(bio.equals("default")){
                        mUserBio.setText("UI/UX Developer")
                    }else{
                        mUserBio.setText(bio)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }


        mSettingConstraintLayout.visibility = View.VISIBLE
        mUpdateProfileConstraintLayout.visibility = View.GONE

        mSaveButton.setOnClickListener {
            mSaveButton.startAnimation(AnimationUtils.loadAnimation(activity,android.R.anim.fade_in));
            if(!TextUtils.isEmpty(mNameEditText.text.toString())){
                mUserAuth.currentUser?.let { it1 -> mUserRef.child(it1.uid).child("name").setValue(mNameEditText.text.toString()).addOnCompleteListener { task->
                    if(task.isSuccessful){
                        mNameEditText.text?.clear()
                    }
                } }
                Toast.makeText(activity,"name updated",Toast.LENGTH_SHORT).show();
            }else{
                mNameEditText.error = "empty name"
            }

            if(!TextUtils.isEmpty(mBioEditText.text.toString())){
                mUserAuth.currentUser?.let { it1 ->
                    mUserRef.child(it1.uid).child("bio").setValue(mBioEditText.text.toString()).addOnCompleteListener { task->
                        if(task.isSuccessful){
                            mBioEditText.text?.clear()
                        }
                    }
                }
                Toast.makeText(activity,"bio updated",Toast.LENGTH_SHORT).show();
            }else{
                mBioEditText.error = "empty bio"
            }

        }

        mUpdateImageButton.setOnClickListener {
            mUpdateImageButton.startAnimation(AnimationUtils.loadAnimation(activity,android.R.anim.fade_in));
            Toast.makeText(activity,"image updated",Toast.LENGTH_SHORT).show();
        }

        mUpdateConstraintLayout.setOnClickListener {
            mUpdateConstraintLayout.startAnimation(AnimationUtils.loadAnimation(activity,android.R.anim.fade_in));
            mSettingButton.setBackgroundResource(layout_inactive)
            mUpdateProfileButton.setBackgroundResource(layout_active)
            mSettingConstraintLayout.visibility = View.GONE
            mUpdateProfileConstraintLayout.visibility = View.VISIBLE
        }

        mLogoutConstraintLayout.setOnClickListener {
            mLogoutConstraintLayout.startAnimation(AnimationUtils.loadAnimation(activity,android.R.anim.fade_in));
            mUserRef.child(mUserAuth.currentUser!!.uid).child("isLogin").setValue("notLoggedIn");
            var intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        mUpdateProfileButton.setOnClickListener {
            mUpdateProfileButton.startAnimation(AnimationUtils.loadAnimation(activity,android.R.anim.fade_in));
            mUpdateProfileButton.setBackgroundResource(layout_active)
            mSettingButton.setBackgroundResource(layout_inactive)
            mSettingConstraintLayout.visibility = View.GONE
            mUpdateProfileConstraintLayout.visibility = View.VISIBLE
        }
        mSettingButton.setOnClickListener {
            mSettingButton.startAnimation(AnimationUtils.loadAnimation(activity,android.R.anim.fade_in));
            mUpdateProfileButton.setBackgroundResource(layout_inactive)
            mSettingButton.setBackgroundResource(layout_active)
            mSettingConstraintLayout.visibility = View.VISIBLE
            mUpdateProfileConstraintLayout.visibility = View.GONE
        }
        updateProfileInitializer()
        settingInitializer()
        return view;
    }
    private fun updateProfileInitializer(){

    }
    private fun settingInitializer(){

    }
    override fun onStart() {
        super.onStart()
        mConstraintLayout.startAnimation(AnimationUtils.loadAnimation(activity,android.R.anim.slide_in_left));
    }
}