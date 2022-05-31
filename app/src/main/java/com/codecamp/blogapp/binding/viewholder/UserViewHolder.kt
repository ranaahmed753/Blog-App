package com.codecamp.blogapp.binding.viewholder

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codecamp.blogapp.R
import com.codecamp.blogapp.binding.model.User
import com.codecamp.blogapp.util.fadeInAnimation
import de.hdodenhof.circleimageview.CircleImageView

class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    var userImage : CircleImageView? = null
    var constraintLayout : ConstraintLayout? = null
    init {
        userImage = itemView.findViewById(R.id.userImage)
        constraintLayout = itemView.findViewById(R.id.constraintLayout)
    }

    fun bind(user : User,holder: UserViewHolder,context: Context){
        if(user.photo?.equals("default")!!){
            holder.userImage?.setImageResource(R.drawable.girl)
        }else{
            Glide.with(context).load(user.photo).into(holder.userImage!!)
        }
    }

    fun onUserClick(widget : CircleImageView,context: Context, onDoSomething : (position : Int, holder : UserViewHolder) -> Unit, position: Int, holder: UserViewHolder){
        widget.setOnClickListener {
            fadeInAnimation(context,widget)
            onDoSomething(position,holder)
        }

    }

    interface setOnUserClick{
        fun onNavigateToDettailsPage(position: Int,holder: UserViewHolder)
    }
}