package com.codecamp.blogapp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codecamp.blogapp.R
import com.codecamp.blogapp.binding.adapter.MyPostAdapter
import com.codecamp.blogapp.binding.model.MyPost
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.concurrent.thread

class ProfileFragment : Fragment() {
    private lateinit var mConstraintLayout : NestedScrollView
    private lateinit var mArrowBackButton : RelativeLayout;
    private lateinit var mUserImage : ShapeableImageView;
    private lateinit var mMyPostRecyclerView : RecyclerView;
    private lateinit var mMyPostAdapter : MyPostAdapter;
    private lateinit var mMyPostList : ArrayList<MyPost>;
    private lateinit var mContext : Context;
    private lateinit var mUserAuth : FirebaseAuth;
    private lateinit var mPostRef : DatabaseReference;
    private lateinit var name : String
    private lateinit var image : String
    private lateinit var post : String
    private lateinit var date : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUserAuth = FirebaseAuth.getInstance();
        mPostRef = FirebaseDatabase.getInstance().reference.child("Posts");
        mUserAuth.currentUser?.let {
            mPostRef.child(it.uid).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(datasnapshot in snapshot.children){
                        //val mypost : MyPost = datasnapshot.getValue(MyPost::class.java)!!
                        name = datasnapshot.child("name").value.toString()
                        image = datasnapshot.child("image").value.toString()
                        post = datasnapshot.child("post").value.toString()
                        date = datasnapshot.child("date").value.toString()
                        mMyPostList.add(MyPost(name,image,post,date))
                        //mMyPostList.add(mypost)
                    }
                    mMyPostAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       //var view : View
        val view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = requireContext();
        mConstraintLayout = view.findViewById(R.id.constraintLayout);
        mArrowBackButton = view.findViewById(R.id.arrowBackIcon);
        mUserImage = view.findViewById(R.id.userImage);
        mMyPostRecyclerView = view.findViewById(R.id.postRecyclerView);
        val shapeAppearanceModel = mUserImage.shapeAppearanceModel.toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, 20f)
            .build()
        mUserImage.shapeAppearanceModel = shapeAppearanceModel
        mArrowBackButton.setOnClickListener {
            mArrowBackButton.startAnimation(AnimationUtils.loadAnimation(activity,android.R.anim.fade_in))
            //go fragment to fragment
            thread {
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.frameLayout, HomeFragment())
                transaction?.isAddToBackStackAllowed
                transaction?.commit()
            }
        }


        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMyPost()
    }

    private fun initMyPost(){
        mMyPostList = arrayListOf();

        mMyPostAdapter = MyPostAdapter(mMyPostList,mContext);
        mMyPostRecyclerView.setHasFixedSize(true);
        mMyPostRecyclerView.layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        mMyPostRecyclerView.adapter = mMyPostAdapter;
    }
    override fun onStart() {
        super.onStart()
        mConstraintLayout.startAnimation(AnimationUtils.loadAnimation(activity,android.R.anim.slide_in_left));
    }
}