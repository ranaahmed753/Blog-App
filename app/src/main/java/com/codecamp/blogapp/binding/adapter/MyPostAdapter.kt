package com.codecamp.blogapp.binding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codecamp.blogapp.R
import com.codecamp.blogapp.binding.model.MyPost
import com.codecamp.blogapp.binding.viewholder.MyPostViewHolder

class MyPostAdapter(var myPostList : ArrayList<MyPost>, var mContext : Context) : RecyclerView.Adapter<MyPostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostViewHolder {
        var view : View = LayoutInflater.from(mContext).inflate(R.layout.mypost,parent,false);
        return MyPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPostViewHolder, position: Int) {
        holder.postUserName.setText(myPostList[position].name)
        holder.postDate.setText(myPostList[position].date)

        holder.mConstraintLayout.startAnimation(AnimationUtils.loadAnimation(mContext,android.R.anim.fade_in))

        if(myPostList[position].image.equals("default")){
            holder.postUserImage.setImageResource(R.drawable.girl)
        }else{
            Glide.with(mContext).load(myPostList[position].image).into(holder.postUserImage);
        }

        holder.postText.setText(myPostList[position].post)
    }

    override fun getItemCount(): Int {
        return myPostList.size
    }
}