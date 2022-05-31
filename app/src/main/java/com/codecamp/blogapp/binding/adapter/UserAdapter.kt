package com.codecamp.blogapp.binding.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codecamp.blogapp.view.MainActivity
import com.codecamp.blogapp.view.ProfileActivity
import com.codecamp.blogapp.R
import com.codecamp.blogapp.binding.model.User
import com.codecamp.blogapp.binding.viewholder.UserViewHolder

class UserAdapter(var mUserList : ArrayList<User>, var mContext : Context) : RecyclerView.Adapter<UserViewHolder>(),UserViewHolder.setOnUserClick {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var view : View;
        view = LayoutInflater.from(mContext).inflate(R.layout.child_user,parent,false);
        return UserViewHolder(view);
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.bind(mUserList[position],holder,mContext)
        holder.onUserClick(holder.userImage!!,mContext,::onNavigateToDettailsPage,position,holder)

    }

    override fun getItemCount(): Int {

            return mUserList.size
    }

    override fun onNavigateToDettailsPage(position: Int, holder: UserViewHolder) {
        val intent = Intent(mContext,ProfileActivity::class.java)
        intent.putExtra("name",mUserList[position].name)
        intent.putExtra("image",mUserList[position].photo)
        intent.putExtra("email",mUserList[position].email)
        intent.putExtra("bio",mUserList[position].bio)
        intent.putExtra("ID",mUserList[position].userId)
        mContext.startActivity(intent)
        (mContext as MainActivity).finish()
    }
}


