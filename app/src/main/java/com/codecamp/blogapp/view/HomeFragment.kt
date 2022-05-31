package com.codecamp.blogapp.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codecamp.blogapp.R
import com.codecamp.blogapp.binding.adapter.PostAdapter
import com.codecamp.blogapp.binding.adapter.UserAdapter
import com.codecamp.blogapp.binding.model.Post
import com.codecamp.blogapp.binding.model.User
import com.codecamp.blogapp.util.navigate
import com.codecamp.blogapp.viewmodel.FirebaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class HomeFragment : Fragment() {
    private lateinit var mRecyclerView : RecyclerView;
    private lateinit var mPostRecyclerView : RecyclerView;
    private lateinit var mConstraintLayout : ConstraintLayout
    private lateinit var mPostButton : RelativeLayout;
    private lateinit var mPostList : ArrayList<Post>;
    private lateinit var mUserList : ArrayList<User>;
    private lateinit var mUserAdapter : UserAdapter;
    private lateinit var mPostAdapter : PostAdapter;
    private lateinit var mContext : Context;
    private lateinit var mUploadText : TextView;
    private lateinit var mBackButtonIcon : ImageView;
    private lateinit var mPostBtn : RelativeLayout;
    private lateinit var mPostEditText : EditText;
    private lateinit var mNestedScrollView : NestedScrollView;
    private lateinit var mPostRef : DatabaseReference;
    private lateinit var mUserAuth : FirebaseAuth;
    private lateinit var mUserRef : DatabaseReference;
    private lateinit var name : String
    private lateinit var photo : String
    private lateinit var email : String
    private lateinit var bio : String
    private lateinit var myid : String
    private lateinit var firebaseViewModel : FirebaseViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        mUserAuth = FirebaseAuth.getInstance();
        mPostRef = FirebaseDatabase.getInstance().reference.child("Posts");
        mUserRef = FirebaseDatabase.getInstance().reference.child("Users")
        mUserAuth.currentUser?.let {
            mUserRef.child(it.uid).addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    name = dataSnapshot.child("name").value.toString()
                    photo = dataSnapshot.child("photo").value.toString()
                    email = dataSnapshot.child("email").value.toString()
                    bio = dataSnapshot.child("bio").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

        mContext = requireContext();
        mConstraintLayout = view.findViewById(R.id.constraintLayout);
        mPostButton = view.findViewById(R.id.postButton);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mPostRecyclerView = view.findViewById(R.id.postRecyclerView)
        mNestedScrollView = view.findViewById(R.id.nestedScrollView);
        mNestedScrollView.isSmoothScrollingEnabled = true
        mNestedScrollView.isFillViewport = true
        mRecyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool())
        mPostRecyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool())
        initializer()
        initializePost()
        setPost()
    }

    private fun initializePost()
    {
        mPostList = arrayListOf()
        mPostRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mPostList.clear()
                for(snapshot in dataSnapshot.children){
                    var totalchild : Long = snapshot.childrenCount
                    for(childsnapshot in snapshot.children){
                        //val post : Post = childsnapshot.getValue(Post::class.java)!!
                        val name = childsnapshot.child("name").value.toString()
                        val image = childsnapshot.child("image").value.toString()
                        val post = childsnapshot.child("post").value.toString()
                        val date = childsnapshot.child("date").value.toString()
                        val email = childsnapshot.child("email").value.toString()
                        val bio = childsnapshot.child("bio").value.toString()
                        //mPostList.add(post)
                        mPostList.add(Post(name,image,post,date,email,bio,totalchild))
                    }

                }
                mPostAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        mPostAdapter = PostAdapter(mPostList,mContext)
        mPostRecyclerView.setHasFixedSize(true)
        val lm = LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false)
        //lm.recycleChildrenOnDetach = true
        mPostRecyclerView.layoutManager = lm
        mPostRecyclerView.adapter = mPostAdapter
        mPostAdapter.notifyDataSetChanged()

    }

    private fun initializer(){
        mUserList = arrayListOf()
        mUserRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mUserList.clear()
                myid = dataSnapshot.key!!
               for(snapshot in dataSnapshot.children){
                    //val user : User = snapshot.getValue(User::class.java)!!
                   val name = snapshot.child("name").value.toString()
                   val photo = snapshot.child("photo").value.toString()
                   val email = snapshot.child("email").value.toString()
                   val bio = snapshot.child("bio").value.toString()
                   mUserList.add(User(name,photo,email,bio,snapshot.key))
               }
                mUserAdapter = UserAdapter(mUserList,mContext)
                mRecyclerView.setHasFixedSize(true)
                mRecyclerView.isNestedScrollingEnabled = false
                val lm = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL ,false)
                lm.recycleChildrenOnDetach = true
                mRecyclerView.layoutManager = lm
                mRecyclerView.adapter = mUserAdapter

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })



    }
    @SuppressLint("ResourceAsColor", "NewApi", "SimpleDateFormat")

    private fun setPost(){
        mPostButton.setOnClickListener {
            mPostButton.startAnimation(AnimationUtils.loadAnimation(activity,android.R.anim.fade_in))
            var bottomShetDialog = BottomSheetDialog(mContext,R.style.BottomSheetStyle);
            //val view : View = layoutInflater.inflate(R.layout.post_alert_dialog,null);
            //bottomShetDialog.setContentView(view)
            bottomShetDialog.setContentView(R.layout.post_alert_dialog);
            mBackButtonIcon = bottomShetDialog.findViewById(R.id.backButtonIcon)!!
            mPostBtn = bottomShetDialog.findViewById(R.id.postButton)!!
            mPostEditText = bottomShetDialog.findViewById(R.id.postEditText)!!
            bottomShetDialog.behavior.peekHeight = 400
            bottomShetDialog.setCanceledOnTouchOutside(false)
            bottomShetDialog.show()

            mBackButtonIcon.setOnClickListener {
                mBackButtonIcon.startAnimation(AnimationUtils.loadAnimation(mContext,android.R.anim.fade_in))
                bottomShetDialog.dismiss()
            }
            mPostBtn.setOnClickListener {
                mPostBtn.startAnimation(AnimationUtils.loadAnimation(mContext,android.R.anim.fade_in))

                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())

                val postText : String = mPostEditText.text.toString()
                val mPostMap : HashMap<String,String> = HashMap()
                mPostMap.put("post",postText);
                mPostMap.put("post_type","text");
                mPostMap.put("date",currentDate);
                mPostMap.put("likes","default");
                mPostMap.put("name",name)
                mPostMap.put("image",photo)
                mPostMap.put("email",email);
                mPostMap.put("bio",bio);


                val dialog : Dialog = activity?.let { it1 -> Dialog(it1) }!!
                dialog.setContentView(R.layout.custom_post_dialog);
                dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.show()

                mPostRef.child(mUserAuth.currentUser!!.uid).push().setValue(mPostMap).addOnCompleteListener {
                    if(it.isSuccessful){
                        mPostEditText.text.clear()
                        dialog.cancel()
                        bottomShetDialog.cancel()
                        Toast.makeText(activity,"Post created",Toast.LENGTH_SHORT).show()
                    }else{
                        dialog.cancel()
                    }
                }
            }


        }
    }

    override fun onStart() {
        super.onStart()

        mConstraintLayout.startAnimation(AnimationUtils.loadAnimation(activity,android.R.anim.slide_in_left));
    }

}