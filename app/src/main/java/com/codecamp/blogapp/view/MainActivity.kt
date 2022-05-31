package com.codecamp.blogapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codecamp.blogapp.R
import com.codecamp.blogapp.util.navigate
import com.codecamp.blogapp.viewmodel.FirebaseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var mBottomNavigationView : BottomNavigationView;
    private lateinit var mAuth : FirebaseAuth;
    private lateinit var mUserRef : DatabaseReference;
    private lateinit var firebaseViewModel : FirebaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mBottomNavigationView = findViewById(R.id.bottomNavigation);
        setCurrentFragment(HomeFragment())
        mBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.home -> thread{setCurrentFragment(HomeFragment())}
                R.id.profile -> thread{setCurrentFragment(ProfileFragment())}
                R.id.setting -> thread{setCurrentFragment(SettingsFragment())}
                else -> {
                    setCurrentFragment(HomeFragment())
                }
            }
            true
        }
    }

    private fun setCurrentFragment(currentFragment : Fragment)
    {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout,currentFragment);
            commit();
        }
    }

    override fun onStart() {
        super.onStart()
        if(mAuth.currentUser == null){
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
            finish();
        }
//        firebaseViewModel.getLoginLiveData().observe(this, androidx.lifecycle.Observer { isUserLoggedIn ->
//            if(isUserLoggedIn){
//                navigate(this,LoginActivity())
//            }
//        })

//        mAuth.currentUser?.let {
//            mUserRef.child(it.uid).addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    var mLoginStatus : String = dataSnapshot.child("isLogin").getValue().toString()
//                    if(mLoginStatus.equals("notLoggedIn")){
//                        startActivity(Intent(applicationContext, LoginActivity::class.java))
//                        finish();
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//            })
//        }

    }
}