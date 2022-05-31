package com.codecamp.blogapp.binding.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codecamp.blogapp.view.MainActivity
import com.codecamp.blogapp.view.ProfileActivity
import com.codecamp.blogapp.R
import com.codecamp.blogapp.binding.model.Post
import com.codecamp.blogapp.binding.viewholder.PostViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PostAdapter(var mPostList : ArrayList<Post>, var mContext : Context) : RecyclerView.Adapter<PostViewHolder>() {
    var isLike : Boolean? = null
    var mPostRef : DatabaseReference? = null
    var mUserAuth : FirebaseAuth? = null
    init {
        isLike = false
        mUserAuth = FirebaseAuth.getInstance();
        mPostRef = FirebaseDatabase.getInstance().getReference().child("Posts");

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        var view : View = LayoutInflater.from(mContext).inflate(R.layout.post,parent,false);
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        holder.postUserName.setText(mPostList[position].name)
        holder.postDate.setText(mPostList[position].date)

        holder.mConstraintLayout.startAnimation(AnimationUtils.loadAnimation(mContext,android.R.anim.fade_in))

        if(mPostList[position].image.equals("default")){
            holder.postUserImage.setImageResource(R.drawable.girl)
        }else{
            Glide.with(mContext).load(mPostList[position].image).into(holder.postUserImage);
        }

        holder.postText.setText(mPostList[position].post)

        holder.postUserImage.setOnClickListener {
            holder.postUserImage.startAnimation(AnimationUtils.loadAnimation(mContext,android.R.anim.fade_in))
            //Toast.makeText(mContext,"visit profile "+position, Toast.LENGTH_SHORT).show()

            val intent = Intent(mContext, ProfileActivity::class.java);
            intent.putExtra("name",mPostList[position].name)
            intent.putExtra("image",mPostList[position].image)
            intent.putExtra("email",mPostList[position].email)
            intent.putExtra("bio",mPostList[position].bio)
            intent.putExtra("totalchild",mPostList[position].totalchild)
            mContext.startActivity(intent);
            (mContext as MainActivity).finish()
        }

        holder.postLike.setOnClickListener {
            holder.postLike.startAnimation(AnimationUtils.loadAnimation(mContext,android.R.anim.fade_in))

        }


        holder.postComment.setOnClickListener {
            holder.postComment.startAnimation(AnimationUtils.loadAnimation(mContext,android.R.anim.fade_in))
            Toast.makeText(mContext,"new post comment on position "+position, Toast.LENGTH_SHORT).show()
        }
        holder.postShare.setOnClickListener {
            holder.postShare.startAnimation(AnimationUtils.loadAnimation(mContext,android.R.anim.fade_in))
            Toast.makeText(mContext,"new post share on position "+position, Toast.LENGTH_SHORT).show()
        }
        holder.menuIcon.setOnClickListener {
            holder.menuIcon.startAnimation(AnimationUtils.loadAnimation(mContext,android.R.anim.fade_in))
            Toast.makeText(mContext,"menu opened "+position, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return mPostList.size
    }

}